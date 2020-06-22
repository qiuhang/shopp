package com.qiuhang.shopp.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.qiuhang.shopp.adapter.MessageAdapter;
import com.qiuhang.shopp.constants.Constants;
import com.qiuhang.shopp.email.service.MailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ConsumerDistribute {
	@Autowired
	private MailService mailService;
	@Autowired
	private MessageAdapter messageAdapter;
	
	@JmsListener(destination = "messages_queue")
	public void distribute(String json) {
		log.info("####ConsumerDistribute###distribute() 消息服务平台接受 json参数:" + json);
		if (StringUtils.isEmpty(json)) {
			return;
		}
		JSONObject jsonObecjt = new JSONObject().parseObject(json);
		JSONObject header = jsonObecjt.getJSONObject("header");
		String interfaceType = header.getString("interfaceType");

		if (StringUtils.isEmpty(interfaceType)) {
			return;
		}
		if (interfaceType.equals(Constants.SMS_MAIL)) {
			messageAdapter = mailService;
		}
		if (messageAdapter == null) {
			return;
		}
		JSONObject body = jsonObecjt.getJSONObject("content");
		log.info("发送");
		messageAdapter.sendMsg(body);

	}

}