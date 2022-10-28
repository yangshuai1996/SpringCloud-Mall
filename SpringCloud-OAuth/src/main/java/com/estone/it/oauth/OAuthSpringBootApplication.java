package com.estone.it.oauth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.estone.it.mapper"})
public class OAuthSpringBootApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(OAuthSpringBootApplication.class,args);
    }
}
