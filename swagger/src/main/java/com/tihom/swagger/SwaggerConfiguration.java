package com.tihom.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author TiHom
 * create at 2018/8/2 0002.
 */

@Configuration
@ComponentScan(basePackages = "com.tihom.swagger")  //扫描
@EnableSwagger2 //方法三开启
//@EnableSwagger2 方法二关闭
public class SwaggerConfiguration {

    @Autowired
    private SwaggerInfo swaggerInfo;

    @Bean
    public Docket controllerApi(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName(swaggerInfo.getGroupName())
                .apiInfo(apiInfo());
        ApiSelectorBuilder builder = docket.select();
        if(!StringUtils.isEmpty(swaggerInfo.getBasePackage())){
            //这样就只扫描swaggerInfo.getBasePackage()包路径下的文件
            builder = builder.apis(RequestHandlerSelectors.basePackage(swaggerInfo.getBasePackage()));
        }
        if(!StringUtils.isEmpty(swaggerInfo.getAntPath())){
            //控制ui界面的url显示 .paths(PathSelectors.ant("/products/*"))
            builder = builder.paths(PathSelectors.ant(swaggerInfo.getAntPath()));
        }
        return builder.build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerInfo.getTitle())
                .description(swaggerInfo.getDescription())
                .termsOfServiceUrl("http://springfox.io")
                .contact("tihom")
                .license(swaggerInfo.getLicense())
                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                .version("2.0")
                .build();
    }
}
