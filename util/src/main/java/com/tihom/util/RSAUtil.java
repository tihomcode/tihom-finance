package com.tihom.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA加密工具类
 * @author TiHom
 * create at 2018/8/4 0004.
 */
public class RSAUtil {

    private static Logger LOG = LoggerFactory.getLogger(RSAUtil.class);

    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";   //签名算法
    private static final String KEY_ALGORITHM = "RSA";        //加密算法RSA

    /**
     * 公钥验签
     *
     * @param text      原字符串(明文)
     * @param sign      签名结果
     * @param publicKey 公钥
     * @return 验签结果
     */
    public static boolean verify(String text, String sign, String publicKey) {
        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            PublicKey key = KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(Base64.decodeBase64(publicKey)));
            signature.initVerify(key);
            signature.update(text.getBytes());
            return signature.verify(Base64.decodeBase64(sign));
        } catch (Exception e) {
            LOG.error("验签失败:text={},sign={}", text, sign, e);
        }
        return false;
    }

    /**
     * 签名字符串
     *
     * @param text       需要签名的字符串(明文)
     * @param privateKey 私钥(BASE64编码)
     * @return 签名结果(BASE64编码)
     */
    public static String sign(String text, String privateKey) {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(privateK);
            signature.update(text.getBytes());
            byte[] result = signature.sign();
            return Base64.encodeBase64String(result);
        } catch (Exception e) {
            LOG.error("签名失败,text={}", text, e);
        }
        return null;
    }

}
