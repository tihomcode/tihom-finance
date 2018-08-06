package com.tihom.seller.params;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tihom.seller.sign.SignText;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author TiHom
 * create at 2018/8/4 0004.
 */
public class OrderParam implements SignText {

    //渠道id
    private String chanId;
    //
    private String chanUserId;

    private String productId;

    private BigDecimal amount;

    private String outerOrderId;

    private String memo;

    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private Date createAt;

    @Override
    public String toString() {
        return "OrderParam{" +
                "chanId='" + chanId + '\'' +
                ", chanUserId='" + chanUserId + '\'' +
                ", productId='" + productId + '\'' +
                ", amount=" + amount +
                ", outerOrderId='" + outerOrderId + '\'' +
                ", memo='" + memo + '\'' +
                ", createAt=" + createAt +
                '}';
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
}