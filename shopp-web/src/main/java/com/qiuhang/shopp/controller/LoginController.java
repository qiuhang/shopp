package com.qiuhang.shopp.controller;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.netflix.client.http.HttpResponse;
import com.qiuhang.shopp.Utils.CookieUtil;
import com.qiuhang.shopp.base.ResponseBase;
import com.qiuhang.shopp.constants.Constants;
import com.qiuhang.shopp.entity.UserEntity;
import com.qiuhang.shopp.fegin.MemberServiceFegin;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {
	
	@Autowired
	private MemberServiceFegin memberServiceFegin;
	
	@GetMapping("login")
	public String loginGet() {
		return "login";
	}
	
	@PostMapping("login")
	public String loginPost(UserEntity userEntity,HttpServletRequest request,HttpServletResponse response) {
		ResponseBase base=memberServiceFegin.login(userEntity);
		if (!base.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
			request.setAttribute("error", base.getRtnMsg());
			return "login";
		}
		LinkedHashMap linkedHashMap = (LinkedHashMap) base.getData();
		String memberToken = (String) linkedHashMap.get("memberToken");
		log.info("memberToken:"+memberToken);
		CookieUtil.addCookie(response, Constants.MEMBER_TOKEN_KEY, memberToken, 
				Constants.MEMBER_TOKEN_COOKIE_TIME);
		return "redirect:/";
	}

}
