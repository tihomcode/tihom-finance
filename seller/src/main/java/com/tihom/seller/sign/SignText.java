package com.tihom.seller.sign;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.tihom.util.JsonUtil;

/**
 * 签名明文
 * @author TiHom
 * create at 2018/8/4 0004.
 */

@JsonInclude(JsonInclude.Include.NON_NULL) //json序列化时包含非空的字段
@JsonPropertyOrder(alphabetic = true)   //字典排序为true
public interface SignText {

    default String toText(){
        return JsonUtil.toJson(this);
    }
}
