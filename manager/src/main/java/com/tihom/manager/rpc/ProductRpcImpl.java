package com.tihom.manager.rpc;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import com.tihom.api.ProductRpc;
import com.tihom.api.domain.ProductRpcReq;
import com.tihom.entity.Product;
import com.tihom.manager.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * rpc服务实现类
 * @author TiHom
 * create at 2018/8/3 0003.
 */

@AutoJsonRpcServiceImpl
@Service    //让spring管理这个对象
public class ProductRpcImpl implements ProductRpc {

    private static Logger LOG  = (Logger) LoggerFactory.getLogger(ProductRpcImpl.class);

    @Autowired
    private ProductService productService;

    @Override
    public List<Product> query(ProductRpcReq req) {
        LOG.info("查询多个产品,请求:{}",req);
        //这里的意思是按收益率倒序排序,从第0页开始,每页展示100个
        Pageable pageable = new PageRequest(0,100,Sort.Direction.DESC,"rewardRate");
        Page<Product> result = productService.query(req.getIdList(),req.getMinRewardRate(),
                req.getMaxRewardRate(),req.getStatusList(),pageable);
        LOG.info("查询多个产品,结果:{}",result);
        return result.getContent();
    }

    @Override
    public Product findOne(String id) {
        LOG.info("查询产品详情,请求:{}",id);
        Product result = productService.findOne(id);
        LOG.info("查询产品详情,结果:{}",result);
        return result;
    }
}
