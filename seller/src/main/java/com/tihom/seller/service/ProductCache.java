package com.tihom.seller.service;

import com.hazelcast.core.HazelcastInstance;
import com.tihom.api.ProductRpc;
import com.tihom.api.domain.ProductRpcReq;
import com.tihom.entity.Product;
import com.tihom.entity.enums.ProductStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 产品缓存
 * @author TiHom
 * create at 2018/8/4 0004.
 */

@Component
public class ProductCache {
    static final String CACHE_NAME = "tihom_product";

    private static Logger LOG  = LoggerFactory.getLogger(ProductCache.class);

    @Autowired
    private ProductRpc productRpc;

    @Autowired
    private HazelcastInstance hazelcastInstance;

    public List<Product> readAllCache(){
        //获取缓存中的map
        Map map = hazelcastInstance.getMap(CACHE_NAME);
        List<Product> products = null;
        //如果map中有数据,则从缓存中读取数据
        if(map.size()>0){
            products = new ArrayList<>();
            products.addAll(map.values());
        } else {
            products = findAll();
        }
        return products;
    }

    public List<Product> findAll(){
        ProductRpcReq req = new ProductRpcReq();
        List<String> status = new ArrayList<>();
        status.add(ProductStatus.IN_SELL.name());

        req.setStatusList(status);
        LOG.info("rpc查询全部产品,请求:{}",req);
        List<Product> result = productRpc.query(req);
        LOG.info("rpc查询全部产品,结果:{}",result);
        return result;
    }
    /**
     * 读取缓存(如果缓存中有了就直接从缓存拿,没有才去调用下面的方法)
     * @param id
     * @return
     */
    @Cacheable(cacheNames = CACHE_NAME)
    public Product readCache(String id){
        LOG.info("rpc查询单个产品,请求:{}",id);
        Product result = productRpc.findOne(id);
        LOG.info("rpc查询单个产品,结果:{}",result);
        return result;
    }

    /**
     * 更新缓存
     * @param product
     * @return
     */
    @CachePut(cacheNames = CACHE_NAME,key = "#product.id")  //把product.id作为key,Product作为value
    public Product putCache(Product product){
        return product;
    }

    /**
     * 清除缓存
     * @param id
     */
    @CacheEvict(cacheNames = CACHE_NAME) //通过id去缓存数据中查询,如果有就清除掉
    public void removeCache(String id){

    }
}
