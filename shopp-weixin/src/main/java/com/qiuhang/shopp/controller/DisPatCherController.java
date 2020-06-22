package com.qiuhang.shopp.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.StaticApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.qiuhang.shopp.Utils.CheckUtil;
import com.qiuhang.shopp.Utils.HttpClientUtil;
import com.qiuhang.shopp.Utils.XmlUtils;
import com.qiuhang.shopp.base.TextMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class DisPatCherController {
	private static final String REQUESTURL="http://api.qingyunke.com/api.php?key=free&appid=0&msg=";
	//服务器验证接口地址
	@GetMapping("/dispatcher")
	public String dispatcher(String signature,String timestamp,String nonce,String echostr) {
		Boolean bool = CheckUtil.checkSignature(signature, timestamp, nonce);
		if (!bool) {
			return null;
		}
		return echostr;
	}
	
	//服务器验证接口地址
		@PostMapping("/dispatcher")
		public String dispatcher(HttpServletRequest request,HttpServletResponse response) throws Exception {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			//1.将xml转成map
			Map<String,String> map=XmlUtils.parseXml(request);
			log.info("####收到微信推送消息#####"+map.toString());
			//2.判断消息类型
			String msgType = map.get("MsgType");
			PrintWriter writer = response.getWriter();
			//3.如果是文本类型，返回结果给微信
			switch (msgType) {
			case "text":
				String ToUserName=map.get("ToUserName");
				String FromUserName=map.get("FromUserName");
				String Content=map.get("Content");
				String resultJson = HttpClientUtil.doGet(REQUESTURL+Content);
				JSONObject jsonObject=JSONObject.parseObject(resultJson);
				String result=jsonObject.getString("result");
				
				String msg ="服务器开小差了";
				if (result.equals("0")) {
					String resultContent=jsonObject.getString("content");
					log.info("####返回信息####："+resultContent);
					msg = setText(resultContent, ToUserName, msgType, FromUserName);
				}
//				if (Content.equals("蚂蚁课堂")) {
//					msg = setText("hello", ToUserName, msgType, FromUserName);
//				}
				writer.write(msg);
				log.info("####发送微信推送消息#####");
				break;

			default:
				break;
			}
			return null;
		}
		
		public String setText(String Content,String FromUserName,String MsgType,String ToUserName) {
			TextMessage textMessage=new TextMessage();
			textMessage.setContent(Content);
			textMessage.setFromUserName(FromUserName);
			textMessage.setMsgType(MsgType);
			textMessage.setToUserName(ToUserName);
			textMessage.setCreateTime(new Date().getTime());
			return XmlUtils.messageToXml(textMessage);
			
		}

}
