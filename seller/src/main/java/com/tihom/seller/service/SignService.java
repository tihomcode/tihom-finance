package com.tihom.seller.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 签名服务
 * @author TiHom
 * create at 2018/8/4 0004.
 */

@Service
public class SignService {

    //一般实际开发这里的公钥应该放在数据库中或者缓存中
    private static Map<String,String> PUBLIC_KEYS = new HashMap<>();
    static {
        PUBLIC_KEYS.put("1000","MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCsYmTLuYEvuTduISqEdqlXSRvj\n" +
                "GCHK1Puicr5W75xI025i5AsJ3D4LQU5T36yDCQJ/A/wAU2GN5wkYXwADfU/goKxs\n" +
                "EiSx1dW+ufxZbl+b2QJph9Fc/rMS4cI7znOOcsMEi4p1/IJCRQAL7gCOC1DWKXzj\n" +
                "VNd830n9rTw5yt9sTQIDAQAB");
    }

    /**
     * 根据授权编号获取公钥
     * @param authId
     * @return
     */
    public String getPublicKey(String authId){
        return PUBLIC_KEYS.get(authId);
    }
}
