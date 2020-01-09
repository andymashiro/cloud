package com.momodog.consumer_one;

import com.momodog.consumer_one.fegin.UserFegin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
@RestController
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class ConsumerOneApplication {
    @Autowired
    RestTemplate restTemplate;

    @Autowired(required = false)
    UserFegin userFegin;

    @LoadBalanced
    @Bean
    public RestTemplate rest() {
        return new RestTemplate();
    }
    public static void main(String[] args) {
        SpringApplication.run(ConsumerOneApplication.class, args);
    }

    @GetMapping(value = "/getUser")
    @ResponseBody
    public Map<String,Object> getUser(@RequestParam Integer id){
        Map<String,Object> data = new HashMap<>();
        data = restTemplate.getForObject("http://providerOne/getUser?id=" + id, Map.class);
        return data;
    }

    @GetMapping(value = "/getFeignUser")
    @ResponseBody
    public Map<String,Object> getFeignUser(@RequestParam Integer id){
        Map<String,Object> data = new HashMap<>();
        data = userFegin.getUser(id);
        return data;
    }
}
