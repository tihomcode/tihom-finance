package com.tihom.seller.configuration;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcClientProxyCreator;
import com.tihom.api.ProductRpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * rpc相关配置
 * @author TiHom
 * create at 2018/8/3 0003.
 */

//@Configuration
//@ComponentScan(basePackageClasses = {ProductRpc.class})  //记住别漏写这个
public class RpcConfiguration {

    private static Logger LOG = LoggerFactory.getLogger(RpcConfiguration.class);

//    @Bean
//    public AutoJsonRpcClientProxyCreator rpcClientProxyCreator(@Value("${rpc.manager.url}") String url){
//        AutoJsonRpcClientProxyCreator clientProxyCreator = new AutoJsonRpcClientProxyCreator();
//        try {
//            //配置基础url
//            clientProxyCreator.setBaseUrl(new URL(url));
//        } catch (MalformedURLException e) {
//            LOG.error("创建rpc服务地址错误");
//        }
//        //让它扫描api下rpc服务的包
//        clientProxyCreator.setScanPackage(ProductRpc.class.getPackage().getName());
//        return clientProxyCreator;
//    }
}
