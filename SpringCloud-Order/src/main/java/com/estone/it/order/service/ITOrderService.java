package com.estone.it.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.estone.it.common.dto.OrderDTO;
import com.estone.it.common.response.ObjectResponse;
import com.estone.it.order.entity.TOrder;

/**
 * <p>
 * 创建订单
 * </p>
 *
 * * @author lidong
 *
 * @since 2019-09-04
 */
public interface ITOrderService extends IService<TOrder> {

    /**
     * 创建订单
     */
    ObjectResponse<OrderDTO> createOrder(OrderDTO orderDTO);
}
