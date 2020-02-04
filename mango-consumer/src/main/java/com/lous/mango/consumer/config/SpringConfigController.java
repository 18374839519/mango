package com.lous.mango.consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 读取配置文件
 */
@RestController
public class SpringConfigController {

    @Value("${hello}")
    private String hello;

    @GetMapping("/hello")
    public String from() {
        return this.hello;
    }
}
