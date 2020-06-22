package com.qiuhang.shopp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.qiuhang.shopp.base.ResponseBase;
import com.qiuhang.shopp.constants.Constants;
import com.qiuhang.shopp.entity.UserEntity;
import com.qiuhang.shopp.fegin.MemberServiceFegin;
@Controller
public class RegisterController {
	
	@Autowired
	private MemberServiceFegin memberServiceFegin;
	
	@GetMapping("register")
	public String registerGet() {
		return "register";
	}
	
	@PostMapping("register")
	public String registerPost(UserEntity userEntity,HttpServletRequest request) {
		ResponseBase base=memberServiceFegin.regUser(userEntity);
		if (!base.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
			request.setAttribute("error", base.getRtnMsg());
			return "register";
		}
		return "login";
	}

}
