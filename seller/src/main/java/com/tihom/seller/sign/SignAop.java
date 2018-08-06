package com.tihom.seller.sign;

import com.tihom.seller.service.SignService;
import com.tihom.util.RSAUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 验签AOP
 * @author TiHom
 * create at 2018/8/4 0004.
 */

@Component
@Aspect
public class SignAop {

    @Autowired
    private SignService signService;

    //在执行具体的方法之前先进行拦截所以用before
    //拦截com.tihom.seller.controller包下的所有类的所有方法，但是限制参数类型为包含这三个类型authId,sign,text的参数
    @Before(value = "execution(* com.tihom.seller.controller.*.*(..)) && args(authId,sign,text,..)")
    public void verify(String authId,String sign,SignText text){
        String publicKey = signService.getPublicKey(authId);
        Assert.isTrue(RSAUtil.verify(text.toText(),sign,publicKey),"验签失败");
        //成功就执行业务方法
    }
}
