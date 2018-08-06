package com.tihom.swagger;

import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.annotation.*;

/**
 * 开启swagger文档自动生成功能
 * @author TiHom
 * create at 2018/8/3 0003.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(SwaggerConfiguration.class)
@EnableSwagger2
public @interface EnableMySwagger {
}
