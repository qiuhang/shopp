package com.qiuhang.shopp.controller;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.qiuhang.shopp.Utils.CookieUtil;
import com.qiuhang.shopp.base.ResponseBase;
import com.qiuhang.shopp.constants.Constants;
import com.qiuhang.shopp.fegin.MemberServiceFegin;

@Controller
public class IndexController {
	@Autowired
	private MemberServiceFegin memberServiceFegin;
	
	@GetMapping("/")
	public String index(HttpServletRequest request) {
		String token=CookieUtil.getUid(request, Constants.MEMBER_TOKEN_KEY);
		ResponseBase base = memberServiceFegin.findByTokenUser(token);
		if (!base.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
			request.setAttribute("error", base.getRtnMsg());
		}
		LinkedHashMap linkedHashMap = (LinkedHashMap) base.getData();
		String username = (String) linkedHashMap.get("username");
		request.setAttribute("username", username);
		return "index";
	}

}
