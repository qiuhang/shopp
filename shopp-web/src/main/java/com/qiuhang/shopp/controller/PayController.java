package com.qiuhang.shopp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.fabric.xmlrpc.base.Data;
import com.qiuhang.shopp.base.ResponseBase;
import com.qiuhang.shopp.constants.Constants;
import com.qiuhang.shopp.fegin.PayCallBackFegin;
import com.qiuhang.shopp.fegin.PayServiceFegin;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class PayController {
	@Autowired
	private PayServiceFegin payServiceFegin;
	
	@Autowired
	private PayCallBackFegin payCallBackFegin;
	
	@RequestMapping("/aliPay")
	public void aliPay(@RequestParam("payToken") String payToken,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw=response.getWriter();
		if (StringUtils.isEmpty(payToken)) {
			return;
		}
		ResponseBase responseBase=payServiceFegin.findPayToken(payToken);
		if (!responseBase.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
			return;
		}
		LinkedHashMap data=(LinkedHashMap) responseBase.getData();
		String payHtml=(String) data.get("payHtml");
		
		log.info("##########html:"+payHtml);
		pw.write(payHtml);
		pw.close();
	}
	
//	@RequestMapping("/callBack/synCallBack")
//	public void synCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		response.setContentType("text/html;charset=utf-8");
//		Map<String, String[]> requestParams = request.getParameterMap();
//		Map<String, String> params = new HashMap<String, String>();
//		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
//			String name = (String) iter.next();
//			String[] values = (String[]) requestParams.get(name);
//			String valueStr = "";
//			for (int i = 0; i < values.length; i++) {
//				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
//			}
//			// 乱码解决，这段代码在出现乱码时使用
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
//			params.put(name, valueStr);
//		}
//		PrintWriter writer = response.getWriter();
//		ResponseBase synCallBack = payCallBackFegin.synCallBack(params);
//		if (!synCallBack.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
//			return;
//		}
//
//		LinkedHashMap data = (LinkedHashMap) synCallBack.getData();
//		String htmlFrom = "<form name='punchout_form'"
//				+ " method='post' action='http://127.0.0.1/callBack/synSuccessPage' >"
//				+ "<input type='hidden' name='outTradeNo' value='" + data.get("out_trade_no") + "'>"
//				+ "<input type='hidden' name='tradeNo' value='" + data.get("trade_no") + "'>"
//				+ "<input type='hidden' name='totalAmount' value='" + data.get("total_amount") + "'>"
//				+ "<input type='submit' value='立即支付' style='display:none'>"
//				+ "</form><script>document.forms[0].submit();" + "</script>";
//		writer.println(htmlFrom);
//		writer.close();
//	}
//
//	// 同步回调,解决隐藏参数
//	@RequestMapping(value = "/callBack/synSuccessPage", method = RequestMethod.POST)
//	public String synSuccessPage(HttpServletRequest request, String outTradeNo, String tradeNo, String totalAmount) {
//		request.setAttribute("outTradeNo", outTradeNo);
//		request.setAttribute("tradeNo", tradeNo);
//		request.setAttribute("totalAmount", totalAmount);
//		return Constants.PAY_SUCCESS;
//	}
//
//	// 异步回调
//	@ResponseBody
//	@RequestMapping("/callBack/asynCallBack")
//	public String asynCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		Map<String, String[]> requestParams = request.getParameterMap();
//		Map<String, String> params = new HashMap<String, String>();
//		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
//			String name = (String) iter.next();
//			String[] values = (String[]) requestParams.get(name);
//			String valueStr = "";
//			for (int i = 0; i < values.length; i++) {
//				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
//			}
//			// 乱码解决，这段代码在出现乱码时使用
//			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
//			params.put(name, valueStr);
//		}
//		return payCallBackFegin.asynCallBack(params);
//	}
	
	public String txTest(@RequestParam("outTradeNo") String outTradeNo,
			@RequestParam("tradeNo") String tradeNo) {
		payServiceFegin.txTest(outTradeNo, tradeNo);
		return "/";
	}

}
