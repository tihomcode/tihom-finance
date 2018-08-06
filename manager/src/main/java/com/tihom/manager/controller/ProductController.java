package com.tihom.manager.controller;

import com.tihom.entity.Product;
import com.tihom.manager.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * 产品
 * @author TiHom
 * create at 2018/8/1 0001.
 */

@RestController
@RequestMapping("/products")
@Api(tags = "product",description = "产品相关")
public class ProductController {

    private static Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    /**
     * 创建产品
     * @param product 产品对象
     * @return 产品对象
     */
    @ApiOperation(value = "创建产品",notes = "根据对应业务规则添加相应的产品")
    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        LOG.info("创建产品,参数:{}",product);

        Product result = productService.addProduct(product);

        LOG.info("创建产品,结果:{}",result);
        return result;
    }

    /**
     * 查询单个产品
     * @param id 产品编号
     * @return 产品
     */
    @GetMapping("/{id}")
    public Product findOne(@PathVariable("id") String id){
        LOG.info("查看单个产品,id={}",id);

        Product product = productService.findOne(id);

        LOG.info("查看单个产品,结果={}",product);
        return product;
    }

    /**
     * 分页查询产品
     * @param ids 编号数组
     * @param minRewardRate 最小收益率
     * @param maxRewardRate 最大收益率
     * @param status 状态数组
     * @param pageNum 页数
     * @param pageSize 每页展示多少
     * @return 可分页的产品对象
     */
    @GetMapping
    public Page<Product> query(String ids,
                               BigDecimal minRewardRate,BigDecimal maxRewardRate,
                               String status,
                               @RequestParam(defaultValue = "0") int pageNum,
                               @RequestParam(defaultValue = "10") int pageSize) {//可以再加上排序参数等其他查询参数
        LOG.info("查询产品,ids={},minRewardRate={},maxRewardRate={},status,pageNum={},pageSize={}");
        List<String> idList = null,statusList = null;

        //因为前端传递过来的时候是数组型的,所以需要将其转换为List集合
        if(!StringUtils.isEmpty(ids)){
            idList = Arrays.asList(ids.split(","));
        }
        if(!StringUtils.isEmpty(status)){
            statusList = Arrays.asList(status.split(","));
        }
        Pageable pageable = new PageRequest(pageNum,pageSize);
        Page<Product> page = productService.query(idList,minRewardRate,maxRewardRate,statusList,pageable);

        LOG.info("查询产品,结果={}",page);
        return page;
    }
}
