package com.qiuhang.shopp.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseBase {
	private Integer rtnCode;
	private String rtnMsg;
	private Object data;
	
	public ResponseBase(Integer rtnCode, String rtnMsg, Object data) {
		super();
		this.rtnCode = rtnCode;
		this.rtnMsg = rtnMsg;
		this.data = data;
	}
	
	
	public ResponseBase() {
	}



}
