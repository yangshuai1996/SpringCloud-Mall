package com.estone.it.business.controller;

import com.estone.it.common.dto.BusinessDTO;
import com.estone.it.common.response.ObjectResponse;
import lombok.extern.slf4j.Slf4j;
import com.estone.it.business.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: heshouyou
 * @Description Dubbo业务执行入口
 * @Date Created in 2019/1/14 17:15
 */
@RestController
@RequestMapping("/business")
@Slf4j
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    /**
     * 模拟用户购买商品下单业务逻辑流程
     *
     * @Param:
     * @Return:
     */
    @PostMapping("/buy")
    ObjectResponse handleBusiness(@RequestBody BusinessDTO businessDTO) {
        log.info("请求参数：{}", businessDTO.toString());
        return businessService.handleBusiness(businessDTO);
    }

    /**
     * 模拟用户购买商品下单业务逻辑流程
     *
     * @Param:
     * @Return:
     */
    @PostMapping("/buy2")
    ObjectResponse handleBusiness2(@RequestBody BusinessDTO businessDTO) {
        log.info("请求参数：{}", businessDTO.toString());
        return businessService.handleBusiness2(businessDTO);
    }
}
