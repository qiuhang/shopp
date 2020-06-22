package com.qiuhang.shopp.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.qiuhang.shopp.adapter.MessageAdapter;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailService implements MessageAdapter {

	@Value("${msg.subject}")
	private String subject;
	@Value("${msg.text}")
	private String text;
	@Autowired
	private JavaMailSender mailSender; // 自动注入的Bean

	@Override
	public void sendMsg(JSONObject body) {
		String mail = body.getString("mail");
		if (StringUtils.isEmpty(mail)) {
			return;
		}
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		// 发送
		simpleMailMessage.setFrom(mail);
		simpleMailMessage.setTo(mail);
		// 标题
		simpleMailMessage.setSubject(subject);
		// 内容
		simpleMailMessage.setText(text.replace("{}", mail));
		log.info("发送邮件");
		mailSender.send(simpleMailMessage);

	}

}
