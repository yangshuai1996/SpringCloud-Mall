package com.estone.it.stock;

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
@MapperScan(basePackages = {"com.estone.it.stock.mapper"})
public class StockSpringBootApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(StockSpringBootApplication.class,args);
    }
}
