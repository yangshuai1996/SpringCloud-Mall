package com.estone.it.stock.controller;

import com.estone.it.common.dto.CommodityDTO;
import com.estone.it.common.response.ObjectResponse;
import lombok.extern.slf4j.Slf4j;
import com.estone.it.stock.service.ITStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * * @author lidong
 *
 * @since 2019-09-04
 */
@RestController
@RequestMapping("/stock")
@Slf4j
public class TStockController {

    @Autowired
    private ITStockService stockService;

    /**
     * 扣减库存
     */
    @PostMapping("dec_stock")
    ObjectResponse decreaseStock(@RequestBody CommodityDTO commodityDTO) {
        log.info("请求库存微服务：{}", commodityDTO.toString());
        return stockService.decreaseStock(commodityDTO);
    }
}

