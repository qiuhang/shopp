package com.qiuhang.shopp.api.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

import com.qiuhang.shopp.api.service.TestApiService;
import com.qiuhang.shopp.base.BaseApiService;
import com.qiuhang.shopp.base.ResponseBase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestApiServiceImpl extends BaseApiService implements TestApiService {

	@Override
	public Map<String, Object> test(Integer id, String name) {
		Map<String,Object> result=new HashMap();
		result.put("rtmCode", "200");
		result.put("rtnMsg", "success");
		result.put("data", "id:"+id+",name:"+name);
		return result;
	
	}

	@Override
	public ResponseBase testResponseBase() {
		
		return setSuccessResult();
	}

	@Override
	public ResponseBase testRedis(String key, String value) {
		
		baseRedis.setString(key, value, null);
		return setSuccessResult();
	}

}
