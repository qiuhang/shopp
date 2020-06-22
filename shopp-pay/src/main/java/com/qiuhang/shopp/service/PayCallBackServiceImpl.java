package com.qiuhang.shopp.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.qiuhang.shopp.api.service.PayCallBackService;
import com.qiuhang.shopp.base.BaseApiService;
import com.qiuhang.shopp.base.ResponseBase;
import com.qiuhang.shopp.config.AlipayConfig;
import com.qiuhang.shopp.constants.Constants;
import com.qiuhang.shopp.dao.PaymentInfoDao;
import com.qiuhang.shopp.entity.PaymentInfo;
import com.qiuhang.shopp.fegin.OrderServiceFegin;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PayCallBackServiceImpl extends BaseApiService implements PayCallBackService {
	@Autowired
	private PaymentInfoDao paymentInfoDao;
	
	@Autowired
	private OrderServiceFegin orderServiceFegin;

	// 同步回调
	public ResponseBase synCallBack(@RequestParam Map<String, String> params) {
		// 获取支付宝GET过来反馈信息
		try {
			log.info("####同步回调开始####params:",params);
			boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key,
					AlipayConfig.charset, AlipayConfig.sign_type); // 调用SDK验证签名
			// ——请在这里编写您的程序（以下代码仅作参考）——
			if (!signVerified) {
				return setErrorResult("验签失败!");
			}
			// 商户订单号
			String out_trade_no = params.get("out_trade_no");
			// 支付宝交易号
			String trade_no = params.get("trade_no");
			// 付款金额
			String total_amount = params.get("total_amount");
			JSONObject data = new JSONObject();
			data.put("out_trade_no", out_trade_no);
			data.put("trade_no", trade_no);
			data.put("total_amount", total_amount);
			return setSuccessResult(data);
		} catch (Exception e) {
			log.info("######PayCallBackServiceImpl##ERROR:#####", e);
			return setErrorResult("系统错误!");
		}finally{
			log.info("####同步回调结束####params:",params);
		}

	}

	// 异步回调
	public String asynCallBack(@RequestParam Map<String, String> params) {
		// 获取支付宝GET过来反馈信息
		try {
			log.info("####异步回调开始####params:",params);
			boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key,
					AlipayConfig.charset, AlipayConfig.sign_type); // 调用SDK验证签名
			// ——请在这里编写您的程序（以下代码仅作参考）——
			if (!signVerified) {
				log.info("signVerified为空");
				return Constants.PAY_FAIL;
			}
			// 商户订单号
			String outTradeNo = params.get("out_trade_no");
			log.info("查询支付信息");
			PaymentInfo paymentInfo = paymentInfoDao.getByOrderIdPayInfo(outTradeNo);
			if(paymentInfo==null){
				log.info("paymentInfo为空");
				return Constants.PAY_FAIL;
			}
			Integer state = paymentInfo.getState();
			if (state.equals("1")) {
				log.info("state=1");
				return Constants.PAY_SUCCESS;
			}
			// 支付宝交易号
			String trade_no = params.get("trade_no");
			//支付金额
			String totalAmount=params.get("total_Amount");
			//判断支付金额是否和要求一致
			// 交易状态
			String trade_status = params.get("trade_status");
			if (trade_status.equals("TRADE_SUCCESS")) {
				paymentInfo.setPayMessage(params.toString());
				paymentInfo.setPlatformorderId(trade_no);
				paymentInfo.setState(1);
				paymentInfoDao.updatePayInfo(paymentInfo);
			}
			log.info("更新工单信息");
			ResponseBase base=orderServiceFegin.updateOrder(1L, trade_no, outTradeNo);
			if (!base.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
				log.info("base.getRtnCode()!=Constants.HTTP_RES_CODE_200");
				return Constants.PAY_FAIL;
			}
			log.info("PAY_SUCCESS");
			return Constants.PAY_SUCCESS;
		} catch (Exception e) {
			log.info("######PayCallBackServiceImpl##ERROR:#####", e);
			return Constants.PAY_FAIL;
		}finally{
			log.info("####异步回调结束####params:",params);
		}

	}

}