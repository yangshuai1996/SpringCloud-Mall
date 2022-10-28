package com.estone.it.business.service.impl;

import com.estone.it.business.feign.OrderFeignClient;
import com.estone.it.business.feign.StockFeignClient;
import com.estone.it.business.service.BusinessService;
import com.estone.it.common.dto.BusinessDTO;
import com.estone.it.common.dto.CommodityDTO;
import com.estone.it.common.dto.OrderDTO;
import com.estone.it.common.enums.RspStatusEnum;
import com.estone.it.common.exception.DefaultException;
import com.estone.it.common.response.ObjectResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: lidong
 * @Description Dubbo业务发起方逻辑
 * @Date Created in 2019/9/5 18:36
 */
@Service
@Slf4j
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private StockFeignClient stockFeignClient;

    @Autowired
    private OrderFeignClient orderFeignClient;
    /**
     * 处理业务逻辑 正常的业务逻辑
     *
     * @Param:
     * @Return:
     */
    @Override
    public ObjectResponse handleBusiness(BusinessDTO businessDTO) {
        ObjectResponse<Object> objectResponse = new ObjectResponse<>();
        //1、扣减库存
        CommodityDTO commodityDTO = new CommodityDTO();
        commodityDTO.setCommodityCode(businessDTO.getCommodityCode());
        commodityDTO.setCount(businessDTO.getCount());
        ObjectResponse stockResponse = stockFeignClient.decreaseStock(commodityDTO);
        //2、创建订单
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(businessDTO.getUserId());
        orderDTO.setCommodityCode(businessDTO.getCommodityCode());
        orderDTO.setOrderCount(businessDTO.getCount());
        orderDTO.setOrderAmount(businessDTO.getAmount());
        ObjectResponse<OrderDTO> response = orderFeignClient.createOrder(orderDTO);

        if (stockResponse.getStatus() != 200 || response.getStatus() != 200) {
            throw new DefaultException(RspStatusEnum.FAIL);
        }

        objectResponse.setStatus(RspStatusEnum.SUCCESS.getCode());
        objectResponse.setMessage(RspStatusEnum.SUCCESS.getMessage());
        objectResponse.setData(response.getData());
        return objectResponse;
    }

    /**
     * 出处理业务服务，出现异常回顾
     *
     * @param businessDTO
     * @return
     */
    @Override
    public ObjectResponse handleBusiness2(BusinessDTO businessDTO) {
        ObjectResponse<Object> objectResponse = new ObjectResponse<>();
        //1、扣减库存
        CommodityDTO commodityDTO = new CommodityDTO();
        commodityDTO.setCommodityCode(businessDTO.getCommodityCode());
        commodityDTO.setCount(businessDTO.getCount());
        ObjectResponse stockResponse = stockFeignClient.decreaseStock(commodityDTO);
        //2、创建订单
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(businessDTO.getUserId());
        orderDTO.setCommodityCode(businessDTO.getCommodityCode());
        orderDTO.setOrderCount(businessDTO.getCount());
        orderDTO.setOrderAmount(businessDTO.getAmount());
        ObjectResponse<OrderDTO> response = orderFeignClient.createOrder(orderDTO);

        if (stockResponse.getStatus() != 200 || response.getStatus() != 200) {
            throw new DefaultException(RspStatusEnum.FAIL);
        }

        objectResponse.setStatus(RspStatusEnum.SUCCESS.getCode());
        objectResponse.setMessage(RspStatusEnum.SUCCESS.getMessage());
        objectResponse.setData(response.getData());
        return objectResponse;
    }
}
