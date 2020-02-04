package com.louis.mango.consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 读取配置文件
 */
@RefreshScope  // 刷新配置的属性值(需要配置中心客户端以post方式主动请求,不推荐; 建议使用Spring Cloud Bus(消息总线))
@RestController
public class SpringConfigController {

    @Value("${hello}")
    private String hello;

    @GetMapping("/hello")
    public String from() {
        return this.hello;
    }
}
