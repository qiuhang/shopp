package com.qiuhang.shopp.txmanager;

import com.codingapi.tx.config.service.TxManagerTxUrlService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * create by lorne on 2017/11/18
 */
@Service
public class TxManagerTxUrlServiceImpl implements TxManagerTxUrlService {

	//@Value("${tm.manager.url}")
	private String url="http://127.0.0.1:7970/";

	@Override
	public String getTxUrl() {
		System.out.println("load tm.manager.url ");
		return url;
	}
}
