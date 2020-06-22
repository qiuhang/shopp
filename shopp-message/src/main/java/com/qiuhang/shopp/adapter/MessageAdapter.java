package com.qiuhang.shopp.adapter;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@Component
public interface MessageAdapter {
	public void sendMsg(JSONObject body);
}