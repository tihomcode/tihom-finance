package com.tihom.manager;

import com.tihom.swagger.EnableMySwagger;
import com.tihom.swagger.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author TiHom
 * create at 2018/8/1 0001.
 */

@SpringBootApplication
@EntityScan(basePackages = {"com.tihom.entity"})
//@Import(SwaggerConfiguration.class)  方法一
//@EnableMySwagger 方法二
//@EnableSwagger2 方法一
public class ManagerApp {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApp.class);
    }
}
