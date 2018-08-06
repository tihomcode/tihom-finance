package com.tihom.seller.repositories;

import com.tihom.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author TiHom
 * create at 2018/8/4 0004.
 */
public interface OrderRepository extends JpaRepository<Order,String>,JpaSpecificationExecutor<Order> {
}
