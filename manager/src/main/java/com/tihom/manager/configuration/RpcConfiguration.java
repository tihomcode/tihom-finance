package com.tihom.manager.configuration;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImplExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rpc相关配置交给spring管理
 * @author TiHom
 * create at 2018/8/3 0003.
 */

//@Configuration
public class RpcConfiguration {

    //这里是交给spring管理
//    @Bean
//    public AutoJsonRpcServiceImplExporter rpcServiceImplExporter(){
//        return new AutoJsonRpcServiceImplExporter();
//    }
}
