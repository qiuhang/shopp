package com.qiuhang.shopp.api.service.impl;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ctc.wstx.util.StringUtil;
import com.qiuhang.shopp.Utils.MD5Util;
import com.qiuhang.shopp.Utils.TokenUtil;
import com.qiuhang.shopp.api.service.MemberService;
import com.qiuhang.shopp.base.BaseApiService;
import com.qiuhang.shopp.base.BaseRedis;
import com.qiuhang.shopp.base.ResponseBase;
import com.qiuhang.shopp.constants.Constants;
import com.qiuhang.shopp.dao.MemberDao;
import com.qiuhang.shopp.entity.UserEntity;
import com.qiuhang.shopp.mq.RegisterMailboxProducer;
import com.sun.org.apache.regexp.internal.recompile;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MemberServiceImpl extends BaseApiService implements MemberService{
	
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private RegisterMailboxProducer mailboxProducer;
	
	@Value("${messages.queue}")
	private String messagequeue;

	@Override
	public ResponseBase findUserById(Long id) {
		UserEntity userEntity=memberDao.findByID(id);
		if (userEntity!=null) {
			return setSuccessResult(userEntity);
		}
		return setErrorResult("未查到用户信息",null);
	}


	@Override
	public ResponseBase regUser(@RequestBody UserEntity userEntity) {
		
		userEntity.setPassword(MD5Util.MD5(userEntity.getPassword()));
		Integer id=memberDao.insertUser(userEntity);
		if (id==null) {
			return setErrorResult("注册失败",null);
		}
		String email=userEntity.getEmail();
		String json=emailJson(email);
		log.info("推送消息");
		sendMessage(json);
		
		return setSuccessResult(id);
	}
	
	private String emailJson(String email) {
		JSONObject rootobject = new JSONObject();
		JSONObject headerobject = new JSONObject();
		headerobject.put("interfaceType", Constants.SMS_MAIL);
		JSONObject content = new JSONObject();
		content.put("mail", email);
		rootobject.put("header", headerobject);
		rootobject.put("content", content);
		return rootobject.toJSONString();
	}
	
	private void sendMessage(String json) {
		ActiveMQQueue queue=new ActiveMQQueue(messagequeue);
		mailboxProducer.sendMsg(queue, json);
	}


	@Override
	public ResponseBase login(@RequestBody UserEntity userEntity) {
		//1.验证参数
		String userName = userEntity.getUsername();
		if (StringUtils.isEmpty(userName)) {
			return setErrorResult("用户名为空",null);
		}
		String password = userEntity.getPassword();
		if (StringUtils.isEmpty(password)) {
			return setErrorResult("密码为空",null);
		}
		//2.数据库查找账号密码是否一致
		String newPwd = MD5Util.MD5(password);
		UserEntity userEntity2 =  memberDao.login(userName, newPwd);
		if (userEntity2 == null) {
			return setErrorResult("用户名或密码错误",null);
		}
		//3.如果正确，生成token
		String token=TokenUtil.getToken();
		
		//4.缓存
		Integer id=userEntity2.getId();
		log.info("用户信息已缓存，token："+token+",id:"+id);
		baseRedis.setString(token, id+"", null);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("memberToken", token);
		
		
		return setSuccessResult(jsonObject);
	}


	@Override
	public ResponseBase findByTokenUser(@RequestParam("token") String token) {
		//验证参数
		if (StringUtils.isEmpty(token)) {
			return setErrorResult("token为空",null);
		}
		//从redis中读取id
		String id=baseRedis.getString(token);
		if (id==null) {
			return setErrorResult("token已失效",null);
		}
		Long userId=Long.parseLong(id);
		UserEntity userEntity=memberDao.findByID(userId);
		if (userEntity==null) {
			return setErrorResult("用户不存在",null);
			
		}
		//查询用户信息
		return setSuccessResult(userEntity);
	}

}
