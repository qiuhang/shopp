package com.qiuhang.shopp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class WeiXinApp {
	public static void main(String[] args) {
		SpringApplication.run(WeiXinApp.class, args);
	}

}
