package com.qiuhang.shopp.service;

import com.codingapi.tx.annotation.TxTransaction;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.qiuhang.shopp.Utils.TokenUtil;
import com.qiuhang.shopp.api.service.PayService;
import com.qiuhang.shopp.base.BaseApiService;
import com.qiuhang.shopp.base.ResponseBase;
import com.qiuhang.shopp.config.AlipayConfig;
import com.qiuhang.shopp.constants.Constants;
import com.qiuhang.shopp.dao.PaymentInfoDao;
import com.qiuhang.shopp.entity.PaymentInfo;
import com.qiuhang.shopp.fegin.OrderServiceFegin;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PayServiceImpl  extends BaseApiService implements PayService {
	@Autowired
	private PaymentInfoDao paymentInfoDao;

	@Autowired
	private OrderServiceFegin orderServiceFegin;

	@Override
	public ResponseBase creatToken(@RequestBody PaymentInfo paymentInfo) {
		
		log.info(paymentInfo.toString());
		//1.创建支付请求信息
		Integer a=paymentInfoDao.savePaymentType(paymentInfo);
		if (a==null) {
			return setErrorResult("插入失败");
		}
		//2.生成对应token
		String token = TokenUtil.getPayToken();
		//3.存放在redis中
		
		baseRedis.setString(token, paymentInfo.getId()+"", Constants.PAY_TOKEN_COOKIE_TIME);
		log.info("保存到redis，id="+paymentInfo.getId());
		//4.返回token
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("payToken", token);
		
		return setSuccessResult(jsonObject);
	}

	@Override
	public ResponseBase findPayToken(@RequestParam String payToken) {
		//1.参数验证
		if (StringUtils.isEmpty(payToken)) {
			return setErrorResult("token为空");
		}
		//2.判断token有效期
		//3.使用token 查找redis 查到对应的id
		String payId=baseRedis.getString(payToken);
		if (StringUtils.isEmpty(payId)) {
			return setErrorResult("token超时"+payId);
		}
		
		//4.使用支付id，进行下单
		Long id=Long.parseLong(payId);
		
		//5.查询支付信息
		PaymentInfo paymentInfo=paymentInfoDao.getPaymentInfo(id);
		if (paymentInfo==null) {
			return setErrorResult("未查到支付信息");
		}
		
		//6.对接支付宝接口
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		
		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
		
		//商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = paymentInfo.getOrderId();
		//付款金额，必填
		String total_amount = paymentInfo.getPrice()+"";
		//订单名称，必填
		String subject = "冲会员";
		//商品描述，可空
		String body = "";
		
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
				+ "\"total_amount\":\""+ total_amount +"\"," 
				+ "\"subject\":\""+ subject +"\"," 
				+ "\"body\":\""+ body +"\"," 
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		
		//若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
		//alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
		//		+ "\"total_amount\":\""+ total_amount +"\"," 
		//		+ "\"subject\":\""+ subject +"\"," 
		//		+ "\"body\":\""+ body +"\"," 
		//		+ "\"timeout_express\":\"10m\"," 
		//		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		//请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节
		
		//请求
		String result=null;
		try {
			result = alipayClient.pageExecute(alipayRequest).getBody();
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//输出
		log.info(result);
		
		JSONObject data=new JSONObject();
		data.put("payHtml", result);
		
		return setSuccessResult(data);
	}
	
	@TxTransaction
	@Transactional 
	public ResponseBase txTest(@RequestParam("outTradeNo") String outTradeNo,
			@RequestParam("tradeNo") String tradeNo) { 
		ResponseBase base=null;
		try {
			PaymentInfo paymentInfo = paymentInfoDao.getByOrderIdPayInfo(outTradeNo);
			paymentInfo.setPlatformorderId(tradeNo);
			paymentInfo.setState(1);
			log.info("#####开始更新支付数据########");
			paymentInfoDao.updatePayInfo(paymentInfo);
			log.info("#####开始更新工单数据########");
			base=orderServiceFegin.updateOrder(1L, tradeNo, outTradeNo);
			log.info("完成更新");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return base;
	}

}
