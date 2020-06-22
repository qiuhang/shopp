package com.qiuhang.shopp.api.service;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;

import com.qiuhang.shopp.base.ResponseBase;


@RequestMapping("/member")
public interface TestApiService {
	@RequestMapping("/test")
	public Map<String, Object> test(Integer id,String name);
	
	@RequestMapping("/success")
	public ResponseBase testResponseBase();
	
	@RequestMapping("/testRedis")
	public ResponseBase testRedis(String key,String value);
}
