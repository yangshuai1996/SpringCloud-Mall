package com.estone.it.stock.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.estone.it.common.dto.CommodityDTO;
import com.estone.it.common.response.ObjectResponse;
import com.estone.it.stock.entity.TStock;

/**
 * 仓库服务
 *
 * * @author lidong
 *
 * @since 2019-09-04
 */
public interface ITStockService extends IService<TStock> {

    /**
     * 扣减库存
     */
    ObjectResponse decreaseStock(CommodityDTO commodityDTO);
}
