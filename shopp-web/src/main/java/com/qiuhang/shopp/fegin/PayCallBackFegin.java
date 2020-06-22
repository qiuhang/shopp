package com.qiuhang.shopp.fegin;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

import com.qiuhang.shopp.api.service.PayCallBackService;
@Component
@FeignClient("pay")

public interface PayCallBackFegin extends PayCallBackService{

}
