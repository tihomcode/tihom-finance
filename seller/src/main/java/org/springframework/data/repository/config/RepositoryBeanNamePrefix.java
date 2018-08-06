package org.springframework.data.repository.config;


import java.lang.annotation.*;

/**
 * repository bean 名称的前缀
 * @author TiHom
 * create at 2018/8/6 0006.
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RepositoryBeanNamePrefix {
    String value();
}
