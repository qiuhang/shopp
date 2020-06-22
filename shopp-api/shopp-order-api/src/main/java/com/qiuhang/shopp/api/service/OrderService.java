package com.qiuhang.shopp.api.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qiuhang.shopp.base.ResponseBase;

@RequestMapping("/order")
public interface OrderService {
	
	@RequestMapping("/updateOrder")
	public ResponseBase updateOrder(@RequestParam("payState") Long payState,
			@RequestParam("payId") String payId,@RequestParam("orderNumber") String orderNumber);
}
