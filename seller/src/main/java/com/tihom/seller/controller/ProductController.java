package com.tihom.seller.controller;

import com.tihom.entity.Product;
import com.tihom.seller.service.ProductRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 产品相关
 * @author TiHom
 * create at 2018/8/4 0004.
 */

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRpcService productRpcService;

    @GetMapping("/{id}")
    public Product findOne(@PathVariable String id){
        return productRpcService.findOne(id);
    }
}
