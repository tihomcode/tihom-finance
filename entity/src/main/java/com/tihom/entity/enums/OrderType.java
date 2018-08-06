package com.tihom.entity.enums;

/**
 * 订单类型
 * @author TiHom
 * create at 2018/8/1 0001.
 */
public enum OrderType {
    APPLY("申购"),
    REDEEM("赎回");

    private String desc;

    OrderType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
