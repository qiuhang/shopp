package com.qiuhang.shopp.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qiuhang.shopp.constants.Constants;

@Component
public class BaseApiService {
	@Autowired
	protected BaseRedis baseRedis;
	
	public ResponseBase setResult(Integer rtnCode,String rtnMsg,Object data) {
		return new ResponseBase(rtnCode,rtnMsg,data);
	}
	//返回成功，无参
	public ResponseBase setSuccessResult() {
		return new ResponseBase(Constants.HTTP_RES_CODE_200,Constants.HTTP_RES_CODE_200_VALUE,null);
	}
	
	//返回成功，有参
	public ResponseBase setSuccessResult(Object data) {
		return new ResponseBase(Constants.HTTP_RES_CODE_200,Constants.HTTP_RES_CODE_200_VALUE,data);
	}
	//返回失败，无参
	public ResponseBase setErrorResult() {
		return new ResponseBase(Constants.HTTP_RES_CODE_500,Constants.HTTP_RES_CODE_500_VALUE,null);
	}
	//返回失败，有参
	public ResponseBase setErrorResult(Object data) {
		return new ResponseBase(Constants.HTTP_RES_CODE_500,Constants.HTTP_RES_CODE_500_VALUE,data);
	}
	
	//返回失败，有参
	public ResponseBase setErrorResult(String rtnMsg,Object data) {
			return new ResponseBase(Constants.HTTP_RES_CODE_500,rtnMsg,data);
	}

}
