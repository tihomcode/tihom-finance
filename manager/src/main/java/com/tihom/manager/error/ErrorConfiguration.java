package com.tihom.manager.error;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 错误处理相关配置
 * @author TiHom
 * create at 2018/8/2 0002.
 */

@Configuration
public class ErrorConfiguration {

    //ErrorMvcAutoConfiguration中的处理方式
    @Bean
    public MyErrorController basicErrorController(ErrorAttributes errorAttributes,ServerProperties serverProperties,
                ObjectProvider<List<ErrorViewResolver>> errorViewResolversProvider) {
        return new MyErrorController(errorAttributes, serverProperties.getError(),
                errorViewResolversProvider.getIfAvailable());
    }
}
