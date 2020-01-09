package com.momodog.provider_two;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@RestController
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class ProviderTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderTwoApplication.class, args);
    }
    @GetMapping(value = "/getUser")
    @ResponseBody
    @HystrixCommand(fallbackMethod ="hystrix_GET" )  //去找备选响应，进行服务降级
    public Map<String, Object> getUser(@RequestParam Integer id) {
        if (id==10){
            throw  new  RuntimeException("该"+id+"没有对应信息");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("userName", "admin");
        data.put("from", "provider_two");
        return data;
    }
    //备选响应，服务降级
    public Map<String, Object> hystrix_GET(@RequestParam Integer id){
        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("userName", "服务熔断2");
        data.put("from", "服务熔断2");
        return data;
    }
}
