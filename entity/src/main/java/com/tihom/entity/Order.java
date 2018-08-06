package com.tihom.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单类
 * @author TiHom
 * create at 2018/8/1 0001.
 */

@Entity(name = "t_order")
public class Order {
    @Id
    private String orderId; //订单编号
    private String chanId;  //渠道编号
    private String chanUserId;  //渠道用户编号

    /**
     * @see com.tihom.entity.enums.OrderType
     */
    private String orderType;   //类型，APPLY：申购，REDEEM：赎回
    private String productId;  //产品编号
    private BigDecimal amount; //金额
    private String outerOrderId;  //外部订单编号
    /**
     * @see com.tihom.entity.enums.OrderStatus
     */
    private String orderStatus; //状态，INIT：初始化，PROCESS：处理中，SUCCESS：成功，FAIL：失败
    private String memo;  //备注

    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private Date createAt;  //创建时间

    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private Date updateAt;  //更新时间

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", chanId='" + chanId + '\'' +
                ", chanUserId='" + chanUserId + '\'' +
                ", orderType='" + orderType + '\'' +
                ", productId='" + productId + '\'' +
                ", amount=" + amount +
                ", outerOrderId='" + outerOrderId + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", memo='" + memo + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getChanId() {
        return chanId;
    }

    public void setChanId(String chanId) {
        this.chanId = chanId;
    }

    public String getChanUserId() {
        return chanUserId;
    }

    public void setChanUserId(String chanUserId) {
        this.chanUserId = chanUserId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getOuterOrderId() {
        return outerOrderId;
    }

    public void setOuterOrderId(String outerOrderId) {
        this.outerOrderId = outerOrderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}
