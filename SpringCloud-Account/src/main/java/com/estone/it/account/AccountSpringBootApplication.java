package com.estone.it.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @创建人 yangshuai
 * @创建时间 2022/10/28
 * @描述
 */

@SpringBootApplication
@MapperScan(basePackages = {"com.estone.it.account.mapper"})
public class AccountSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountSpringBootApplication.class,args);
    }
}
