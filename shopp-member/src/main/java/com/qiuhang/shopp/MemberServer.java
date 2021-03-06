package com.qiuhang.shopp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MemberServer {
	public static void main(String[] args) {
		SpringApplication.run(MemberServer.class, args);
	}
}
