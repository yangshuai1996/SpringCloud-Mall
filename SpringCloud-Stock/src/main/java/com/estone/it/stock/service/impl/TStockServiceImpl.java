package com.estone.it.stock.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.estone.it.common.dto.CommodityDTO;
import com.estone.it.common.enums.RspStatusEnum;
import com.estone.it.common.response.ObjectResponse;
import com.estone.it.stock.entity.TStock;
import com.estone.it.stock.mapper.TStockMapper;
import com.estone.it.stock.service.ITStockService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 库存服务实现类
 * </p>
 *
 * * @author lidong
 *
 * @since 2019-09-04
 */
@Service
public class TStockServiceImpl extends ServiceImpl<TStockMapper, TStock> implements ITStockService {

    @Override
    public ObjectResponse decreaseStock(CommodityDTO commodityDTO) {
        int stock = baseMapper.decreaseStock(commodityDTO.getCommodityCode(), commodityDTO.getCount());
        ObjectResponse<Object> response = new ObjectResponse<>();
        if (stock > 0) {
            response.setStatus(RspStatusEnum.SUCCESS.getCode());
            response.setMessage(RspStatusEnum.SUCCESS.getMessage());
            return response;
        }

        response.setStatus(RspStatusEnum.FAIL.getCode());
        response.setMessage(RspStatusEnum.FAIL.getMessage());
        return response;
    }
}
