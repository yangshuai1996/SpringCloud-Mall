package com.estone.it.business;

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
@EnableDiscoveryClient
@EnableFeignClients
public class BusinessSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusinessSpringBootApplication.class,args);
    }
}
