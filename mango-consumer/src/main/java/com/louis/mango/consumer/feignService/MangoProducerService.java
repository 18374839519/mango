package com.louis.mango.consumer.feignService;

import com.louis.mango.consumer.hystrix.MangoProducerHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 对其他模块提供接口
 * fallback: 绑定失败回调处理类
 */
@FeignClient(name = "mango-producer", fallback = MangoProducerHystrix.class)  // mango-producer 是要调用的服务名
public interface MangoProducerService {

    @RequestMapping("/hello")
    String hello();
}
