package com.tihom.manager.service;

import com.tihom.entity.Product;
import com.tihom.entity.enums.ProductStatus;
import com.tihom.manager.error.ErrorEnum;
import com.tihom.manager.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 产品服务类
 * @author TiHom
 * create at 2018/8/1 0001.
 */

@Service
public class ProductService {

    private static Logger LOG = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository repository;

    public Product addProduct(Product product){
        LOG.debug("创建产品,参数:{}",product);
        //数据校验
        checkProduct(product);
        //设置默认值
        setDefault(product);
        Product result = repository.save(product);
        LOG.debug("创建产品,结果:{}",result);
        return result;
    }

    /**
     * 设置默认值
     * @param product
     */
    private void setDefault(Product product) {
        //创建时间
        if (product.getCreateAt() == null) {
            product.setCreateAt(new Date());
        }
        //更新时间
        if (product.getUpdateAt() == null) {
            product.setUpdateAt(new Date());
        }
        //投资步长默认为0
        if (product.getStepAmount() == null) {
            product.setStepAmount(BigDecimal.ZERO);
        }
        //锁定期默认为0天
        if (product.getLockTerm() == null) {
            product.setLockTerm(0);
        }
        //产品状态默认为审核中
        if (product.getStatus() == null) {
            product.setStatus(ProductStatus.AUDITING.name());
        }
    }

    /**
     * 产品数据校验
     * 非空、收益率为0-30以内、投资步长需为整数（业务需求决定）
     * @param product
     */
    private void checkProduct(Product product) {
        Assert.notNull(product.getId(),ErrorEnum.ID_NOT_NULL.getCode());
        Assert.isTrue(BigDecimal.ZERO.compareTo(product.getRewardRate())<0 &&
                BigDecimal.valueOf(30).compareTo(product.getRewardRate())>=0,"收益率范围错误");  //这里暂时我不修改错误的显示方式,方便对比
        Assert.isTrue(BigDecimal.valueOf(product.getStepAmount().longValue())
                .compareTo(product.getStepAmount()) == 0, "投资步长需为整数");
        //其他非空校验
    }

    /**
     * 查询单个产品
     * @param id 产品编号
     * @return 返回对应产品或者null
     */
    public Product findOne(String id){
        Assert.notNull(id,"需要产品编号参数");
        LOG.debug("查询单个产品,id={}",id);

        Product product = repository.findOne(id);

        LOG.debug("查询单个产品,结果={}",product);
        return product;
    }

    /**
     * 分页查询产品
     * @param idList 编号
     * @param minRewardRate 最小收益率
     * @param maxRewardRate 最大收益率
     * @param statusList 状态
     * @param pageable 分页的参数
     * @return 分页的产品对象
     */
    public Page<Product> query(List<String> idList,
                               BigDecimal minRewardRate, BigDecimal maxRewardRate,
                               List<String> statusList,
                               Pageable pageable){
        LOG.debug("查询产品,idList={},minRewardRate={},maxRewardRate={},statusList={},pageable={}",
                idList, minRewardRate, maxRewardRate, statusList, pageable);

        Specification<Product> specification = new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Expression<String> idCol = root.get("id");  //获取编号列
                Expression<BigDecimal> rewardRateCol = root.get("rewardRate");  //获取收益率列
                Expression<String> statusCol = root.get("status");  //获取状态列
                List<Predicate> predicates = new ArrayList<>();     //断言列表
                if(idList!=null && idList.size()>0){
                    predicates.add(idCol.in(idList));
                }
                //这里的用法是BigDecimal.ZERO小于minRewardRate返回-1，如果BigDecimal为大于minRewardRate返回1
                if(minRewardRate!=null && BigDecimal.ZERO.compareTo(minRewardRate)<0){
                    predicates.add(criteriaBuilder.ge(rewardRateCol,minRewardRate));
                }
                if(maxRewardRate!=null &&
                         BigDecimal.ZERO.compareTo(maxRewardRate)<0){
                    predicates.add(criteriaBuilder.le(rewardRateCol,maxRewardRate));
                }
                if(statusList!=null && statusList.size()>0){
                    predicates.add(statusCol.in(statusList));
                }

                criteriaQuery.where(predicates.toArray(new Predicate[0]));
                return null;
            }
        };
        Page<Product> page = repository.findAll(specification,pageable);

        LOG.debug("分页查询产品,结果={}",page);
        return page;
    }
}
