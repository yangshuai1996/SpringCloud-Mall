package com.estone.it.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @创建人 yangshuai
 * @创建时间 2022/10/28
 * @描述
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.estone.it.order.mapper"})
public class OrderSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderSpringBootApplication.class,args);
    }
}
