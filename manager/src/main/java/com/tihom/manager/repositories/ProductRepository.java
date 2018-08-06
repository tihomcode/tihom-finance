package com.tihom.manager.repositories;

import com.tihom.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 产品管理
 * @author TiHom
 * create at 2018/8/1 0001.
 */
public interface ProductRepository extends JpaRepository<Product,String>,JpaSpecificationExecutor<Product> {
}
