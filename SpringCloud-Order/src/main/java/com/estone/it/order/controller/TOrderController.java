package com.estone.it.order.controller;
import com.estone.it.common.dto.OrderDTO;
import com.estone.it.order.service.ITOrderService;
import com.estone.it.common.response.ObjectResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单服务
 * </p>
 *
 * * @author lidong
 *
 * @since 2019-09-04
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class TOrderController {

    @Autowired
    private ITOrderService orderService;

    @PostMapping("/create_order")
    ObjectResponse<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        log.info("请求订单微服务：{}", orderDTO.toString());
        return orderService.createOrder(orderDTO);
    }
}

