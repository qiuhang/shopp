package com.qiuhang.shopp.api.service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qiuhang.shopp.base.ResponseBase;
import com.qiuhang.shopp.entity.PaymentInfo;

@RequestMapping("/pay")
public interface PayService {
	@PostMapping("/creatToken")
	public ResponseBase creatToken(@RequestBody PaymentInfo paymentInfo);
	
	@PostMapping("/findPayToken")
	public ResponseBase findPayToken(@RequestParam("payToken") String payToken);
	
	@PostMapping("txTest")
	public ResponseBase txTest(@RequestParam("outTradeNo") String outTradeNo,
			@RequestParam("tradeNo") String tradeNo);

}
