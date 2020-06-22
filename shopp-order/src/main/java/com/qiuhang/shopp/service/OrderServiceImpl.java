package com.qiuhang.shopp.service;

import com.codingapi.tx.annotation.TxTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.qiuhang.shopp.api.service.OrderService;
import com.qiuhang.shopp.base.BaseApiService;
import com.qiuhang.shopp.base.ResponseBase;
import com.qiuhang.shopp.dao.OrderDao;

@RestController
public class OrderServiceImpl  extends BaseApiService implements OrderService{


	@Autowired
	private OrderDao orderDao;
	
	@Override
	@TxTransaction
	@Transactional
	public ResponseBase updateOrder(Long payState, String payId, String orderNumber) {
		int updateOrder = orderDao.updateOrder(payState, payId, orderNumber);
		if (updateOrder <= 0) {
			return setErrorResult("系统错误!");
		}
		return setSuccessResult();
	}

}
