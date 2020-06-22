package com.qiuhang.shopp.fegin;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

import com.qiuhang.shopp.api.service.OrderService;

@Component
@FeignClient("order")
public interface OrderServiceFegin extends OrderService {

}
