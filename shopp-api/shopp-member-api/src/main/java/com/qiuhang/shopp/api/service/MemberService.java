package com.qiuhang.shopp.api.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qiuhang.shopp.base.ResponseBase;
import com.qiuhang.shopp.entity.UserEntity;

@RequestMapping("/member")
public interface MemberService {
	@RequestMapping("/findUserById")
	ResponseBase findUserById(Long id);
	
	@RequestMapping("regUser")
	ResponseBase regUser(UserEntity userEntity);
	
	@RequestMapping("login")
	ResponseBase login(UserEntity userEntity);
	
	@RequestMapping("findByTokenUser")
	ResponseBase findByTokenUser(@RequestParam("token") String token);

}
