package com.tihom.entity.enums;

/**
 * 产品状态
 * @author TiHom
 * create at 2018/8/1 0001.
 */
public enum ProductStatus {
    AUDITING("审核中"),

    IN_SELL("销售中"),

    LOCKED("暂停销售"),

    FINISHED("已结束");

    private String desc;

    ProductStatus(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
