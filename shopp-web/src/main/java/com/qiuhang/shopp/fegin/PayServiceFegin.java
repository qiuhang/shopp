package com.qiuhang.shopp.fegin;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

import com.qiuhang.shopp.api.service.PayService;
@Component
@FeignClient("pay")
public interface PayServiceFegin extends PayService{

}
