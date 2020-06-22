package com.qiuhang.shopp.constants;

import java.util.UUID;

public interface Constants {
	// 响应code
	String HTTP_RES_CODE_NAME = "code";
	// 响应msg
	String HTTP_RES_CODE_MSG = "msg";
	// 响应data
	String HTTP_RES_CODE_DATA = "data";
	// 响应请求成功
	String HTTP_RES_CODE_200_VALUE = "success";
	// 系统错误
	String HTTP_RES_CODE_500_VALUE = "error";
	// 响应请求成功code
	Integer HTTP_RES_CODE_200 = 200;
	// 系统错误
	Integer HTTP_RES_CODE_500 = 500;
	
	String SMS_MAIL = "email";
	//登陆token
	String MEMBER_TOKEN = "MEMBER_TOKEN";
	String MEMBER_TOKEN_KEY = "member_token";
	int MEMBER_TOKEN_COOKIE_TIME = 24*60*60;
	Long PAY_TOKEN_COOKIE_TIME = 15*60l;
	String PAY_TOKEN = "pay_token";
	String PAY_SUCCESS = "success";
	String PAY_FAIL = "fail";
}
