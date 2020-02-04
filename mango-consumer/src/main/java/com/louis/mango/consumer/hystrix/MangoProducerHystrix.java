package com.louis.mango.consumer.hystrix;

import com.louis.mango.consumer.feignService.MangoProducerService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 返回接口调用失败信息
 */
@Component
public class MangoProducerHystrix implements MangoProducerService {

    @Override
    @RequestMapping("/hello")
    public String hello() {
        return "Sorry, hello service call failed.";
    }
}
