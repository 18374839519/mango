package com.lous.mango.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@EnableTurbine  // 开启turbine支持
@EnableHystrixDashboard  // 开启熔断监控支持(如果使用的是2.x等比较新的版本，则需要在Hystrix的消费端(mango-consumer)配置监控路径)
@EnableDiscoveryClient
@SpringBootApplication
public class MangoHystrixApplication {

	public static void main(String[] args) {
		SpringApplication.run(MangoHystrixApplication.class, args);
	}

}
