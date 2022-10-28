package com.estone.it.business.feign;

import com.estone.it.common.dto.CommodityDTO;
import com.estone.it.common.response.ObjectResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @创建人 yangshuai
 * @创建时间 2022/10/28
 * @描述
 */
@FeignClient(name = "springcloud-stock")
public interface StockFeignClient {
    @PostMapping("/stock/dec_stock")
    ObjectResponse decreaseStock(CommodityDTO commodityDTO);
}
