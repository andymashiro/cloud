package com.momodog.consumer_one.fegin;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "providerOne", fallbackFactory = FallbackUserClint.class)//fegin指定服务与降级方法
public interface UserFegin {
    @GetMapping(value = "/getUser")
    Map<String, Object> getUser(@RequestParam Integer id);
}
