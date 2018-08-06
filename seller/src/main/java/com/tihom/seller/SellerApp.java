package com.tihom.seller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 销售端启动类
 * @author TiHom
 * create at 2018/8/3 0003.
 */

@SpringBootApplication
@EnableCaching
@EntityScan("com.tihom.entity")
@EnableScheduling
public class SellerApp {
    public static void main(String[] args) {
        SpringApplication.run(SellerApp.class);
    }
}
