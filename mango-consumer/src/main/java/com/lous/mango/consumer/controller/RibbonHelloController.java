package com.lous.mango.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Configuration
public class RibbonHelloController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/ribbon/call")
    public String call() {
        // 调用服务，mango-producer为注册的服务名称,LoadBalancerInterceptor 会拦截调用并根据服务名称找到对应的服务
        String callServiceResult = restTemplate.getForObject("http://mango-producer/hello", String.class);
        System.out.println(callServiceResult);
        return callServiceResult;
    }
}
