package com.mghio;

import io.prometheus.client.spring.boot.EnableSpringBootMetricsCollector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * 支付服务
 */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
@EnableSpringBootMetricsCollector
public class PayProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayProviderApplication.class, args);
    }

}