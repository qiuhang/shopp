package com.qiuhang.shopp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@RequestMapping("/index")
	public String index() {
		return "ok";
	}
}
