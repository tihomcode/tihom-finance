package com.tihom.manager.service;

import com.tihom.api.events.ProductStatusEvent;
import com.tihom.entity.enums.ProductStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 管理产品状态
 * @author TiHom
 * create at 2018/8/4 0004.
 */

@Component
public class ProductStatusManager {

    private static Logger LOG = LoggerFactory.getLogger(ProductStatusManager.class);

    private static final String MQ_DESTINATION = "VirtualTopic.PRODUCT_STATUS";

    @Autowired
    private JmsTemplate jmsTemplate;

    public void changeStatus(String id, ProductStatus status){
        ProductStatusEvent event = new ProductStatusEvent(id,status);
        LOG.info("send message:{}",event);
        //目的地和事件对象
        jmsTemplate.convertAndSend(MQ_DESTINATION,event);
    }

//    @PostConstruct
//    public void init(){
//        changeStatus("T001",ProductStatus.IN_SELL);
//    }

}
