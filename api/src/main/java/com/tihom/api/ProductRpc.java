package com.tihom.api;

import com.googlecode.jsonrpc4j.JsonRpcService;
import com.tihom.api.domain.ProductRpcReq;
import com.tihom.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 产品相关的rpc服务
 * @author TiHom
 * create at 2018/8/3 0003.
 */

@JsonRpcService("rpc/products") //这里不能以/开始例如 /products这是错误的
public interface ProductRpc {

    /**
     * 查询多个产品
     * @param req
     * @return
     */
    List<Product> query(ProductRpcReq req);

    /**
     * 查询单个产品
     * @param id
     * @return
     */
    Product findOne(String id);
}
