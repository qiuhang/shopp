package com.qiuhang.shopp.Utils;

import java.util.UUID;

import com.qiuhang.shopp.constants.Constants;

public class TokenUtil {
	 public static String getToken(){
		 return Constants.MEMBER_TOKEN+UUID.randomUUID();
	 }
	 
	 public static String getPayToken(){
		 return Constants.PAY_TOKEN+UUID.randomUUID();
	 }

}
