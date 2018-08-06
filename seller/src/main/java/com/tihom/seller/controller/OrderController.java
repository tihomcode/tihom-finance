package com.tihom.seller.controller;

import com.tihom.entity.Order;
import com.tihom.seller.params.OrderParam;
import com.tihom.seller.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单相关
 * @author TiHom
 * create at 2018/8/4 0004.
 */

@RestController
@RequestMapping("/order")
public class OrderController {

    private static Logger LOG = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    /**
     * 下单
     * @param param
     * @return
     */
    @PostMapping("/apply")
    public Order apply(@RequestHeader String authId,@RequestHeader String sign, @RequestBody OrderParam param){
        LOG.info("申购请求:{}",param);
        Order order = new Order();
        //深拷贝
        BeanUtils.copyProperties(param,order);
        order = orderService.apply(order);
        LOG.info("申购结果:{}",order);
        return order;
    }

}
