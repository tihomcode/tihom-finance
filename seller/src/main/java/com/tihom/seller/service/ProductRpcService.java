package com.tihom.seller.service;

import com.tihom.api.ProductRpc;
import com.tihom.api.events.ProductStatusEvent;
import com.tihom.entity.Product;
import com.tihom.entity.enums.ProductStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品相关服务
 * @author TiHom
 * create at 2018/8/3 0003.
 */

@Service
//实现ApplicationListener监听器接口,监听ContextRefreshedEvent事件,容器初始化完成后会触发事件
public class ProductRpcService implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger LOG = LoggerFactory.getLogger(ProductRpcService.class);

    private static final String MQ_DESTINATION = "Consumer.cache.VirtualTopic.PRODUCT_STATUS";

    @Autowired
    private ProductRpc productRpc;
    @Autowired
    private ProductCache productCache;

    /**
     * 查询全部产品
     * @return
     */
    public List<Product> findAll(){
        return productCache.readAllCache();
    }

    public Product findOne(String id){
        Product product = productCache.readCache(id);
        if(product==null){
            productCache.removeCache(id);
        }
        return product;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        List<Product> products = findAll();
//        products.forEach(product -> {
//            productCache.putCache(product);
//        });
    }

    @JmsListener(destination = MQ_DESTINATION)     //接受状态改变的事件
    void updateCache(ProductStatusEvent event){
        //首先要清除缓存,因为要重新读取数据,如果不清除缓存会读取缓存中的数据
        LOG.info("receive event:{}",event);
        productCache.removeCache(event.getId());
        if(ProductStatus.IN_SELL.equals(event.getStatus())){
            //如果是销售中的状态,读取缓存
            productCache.readCache(event.getId());
        }

    }

//    @PostConstruct
//    public void init(){
//        findOne("T001");
//    }

}
