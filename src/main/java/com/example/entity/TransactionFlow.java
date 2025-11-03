package com.example.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionFlow {
    private Integer id;
    private Integer fromCardId;
    private Integer toCardId;
    private Integer flowType; // 0-收入, 1-支出
    private BigDecimal amount;
    private Date createTime;

    // 构造方法
    public TransactionFlow() {}

    public TransactionFlow(Integer id, Integer fromCardId, Integer toCardId, Integer flowType, BigDecimal amount, Date createTime) {
        this.id = id;
        this.fromCardId = fromCardId;
        this.toCardId = toCardId;
        this.flowType = flowType;
        this.amount = amount;
        this.createTime = createTime;
    }

    // Getter和Setter方法
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getFromCardId() { return fromCardId; }
    public void setFromCardId(Integer fromCardId) { this.fromCardId = fromCardId; }

    public Integer getToCardId() { return toCardId; }
    public void setToCardId(Integer toCardId) { this.toCardId = toCardId; }

    public Integer getFlowType() { return flowType; }
    public void setFlowType(Integer flowType) { this.flowType = flowType; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    @Override
    public String toString() {
        return "TransactionFlow{" +
                "id=" + id +
                ", fromCardId=" + fromCardId +
                ", toCardId=" + toCardId +
                ", flowType=" + flowType +
                ", amount=" + amount +
                ", createTime=" + createTime +
                '}';
    }
}