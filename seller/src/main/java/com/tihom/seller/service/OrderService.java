package com.tihom.seller.service;

import com.tihom.entity.Order;
import com.tihom.entity.Product;
import com.tihom.entity.enums.OrderStatus;
import com.tihom.entity.enums.OrderType;
import com.tihom.seller.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * 订单服务
 * @author TiHom
 * create at 2018/8/4 0004.
 */

@Service
public class OrderService {

    private static Logger LOG = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRpcService productRpcService;

    /**
     * 申购订单
     * @param order
     * @return
     */
    public Order apply(Order order){
        //数据校验
        checkOrder(order);
        //完善订单数据
        completeOrder(order);
        //saveAndFlush会立即刷新到数据库中(高并发使用好)
        order = orderRepository.saveAndFlush(order);
        return order;
    }

    /**
     * 完善订单数据
     * @param order
     */
    private void completeOrder(Order order) {
        order.setOrderId(UUID.randomUUID().toString().replaceAll("-",""));
        order.setOrderType(OrderType.APPLY.name());
        order.setOrderStatus(OrderStatus.SUCCESS.name());
        order.setUpdateAt(new Date());
    }

    /**
     * 校验数据
     * @param order
     */
    private void checkOrder(Order order) {
        //必填字段
        Assert.notNull(order.getOuterOrderId(),"需要外部订单编号");
        Assert.notNull(order.getChanId(),"需要渠道编号");
        Assert.notNull(order.getChanUserId(),"需要用户编号");
        Assert.notNull(order.getProductId(),"需要产品编号");
        Assert.notNull(order.getAmount(),"需要购买金额");
        Assert.notNull(order.getCreateAt(),"需要订单时间");
        //产品是否存在及金额是否符合要求
        Product product = productRpcService.findOne(order.getProductId());
        Assert.notNull(product,"产品不存在");
        //金额要满足如果有起投金额时，要大于等于起投金额，如果有投资步长时，超过起投金额的部分要是投资步长的整数倍
//        Assert.isTrue(order.getAmount().compareTo(product.getThresholdAmount())>0,"购买金额要大于起投金额");
//        BigDecimal[] bigDecimals = (order.getAmount().subtract(product.getThresholdAmount())).divideAndRemainder(product.getStepAmount());
//        Assert.isTrue(bigDecimals[1].equals(BigDecimal.ZERO),"超过起投金额的部分要是投资步长的整数倍");
    }
}
