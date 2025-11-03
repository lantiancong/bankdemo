package com.example.entity;

import java.math.BigDecimal;
import java.util.Date;

public class BankCard {
    private Integer id;
    private String cardNumber;
    private String bankInfo;
    private Integer userId;
    private Date createTime;
    private BigDecimal balance;

    // 构造方法
    public BankCard() {}

    public BankCard(Integer id, String cardNumber, String bankInfo, Integer userId, Date createTime, BigDecimal balance) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.bankInfo = bankInfo;
        this.userId = userId;
        this.createTime = createTime;
        this.balance = balance;
    }

    // Getter和Setter方法
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getBankInfo() { return bankInfo; }
    public void setBankInfo(String bankInfo) { this.bankInfo = bankInfo; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    @Override
    public String toString() {
        return "BankCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", bankInfo='" + bankInfo + '\'' +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", balance=" + balance +
                '}';
    }
}