package com.momodog.consumer_one.fegin;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DengWei
 * @date 2019/12/31 16:51
 */
@Component
public class FallbackUserClint implements FallbackFactory<UserFegin> {


    @Override
    public UserFegin create(Throwable throwable) {
        return new UserFegin() {
            @Override
            public Map<String, Object> getUser(Integer id) {
                Map<String, Object> data = new HashMap<>();
                data.put("id", id);
                data.put("userName", "服务降级");
                data.put("from", "服务降级");
                return data;
        }
        };
    }
}
