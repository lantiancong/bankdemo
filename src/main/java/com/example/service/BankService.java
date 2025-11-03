package com.example.service;

import com.example.entity.BankCard;
import com.example.entity.TransactionFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询银行卡余额
     */
    public BigDecimal getBalance(Integer cardId) {
        String sql = "SELECT balance FROM bank_card WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{cardId}, BigDecimal.class);
    }

    /**
     * 查询银行卡信息
     */
    public BankCard getBankCard(Integer cardId) {
        String sql = "SELECT * FROM bank_card WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{cardId},
                new BeanPropertyRowMapper<>(BankCard.class));
    }

    /**
     * 事件1：用户A转账给用户B - 正常事务
     * 严格按照PPT中的@Transactional注解使用方式
     */
    @Transactional
    public void transferAccounts(Integer fromCardId, Integer toCardId, BigDecimal amount) {
        System.out.println("=== 开始转账事务 ===");

        // 1. 检查转出方余额
        BigDecimal fromBalance = getBalance(fromCardId);
        if (fromBalance.compareTo(amount) < 0) {
            throw new RuntimeException("余额不足");
        }

        // 2. 扣除转出方金额
        String deductSql = "UPDATE bank_card SET balance = balance - ? WHERE id = ?";
        jdbcTemplate.update(deductSql, amount, fromCardId);
        System.out.println("扣除转出方金额: " + amount);

        // 3. 增加转入方金额
        String addSql = "UPDATE bank_card SET balance = balance + ? WHERE id = ?";
        jdbcTemplate.update(addSql, amount, toCardId);
        System.out.println("增加转入方金额: " + amount);

        // 4. 记录转出流水（支出）
        String outFlowSql = "INSERT INTO transaction_flow(from_card_id, to_card_id, flow_type, amount) VALUES(?, ?, 1, ?)";
        jdbcTemplate.update(outFlowSql, fromCardId, toCardId, amount);

        // 5. 记录转入流水（收入）
        String inFlowSql = "INSERT INTO transaction_flow(from_card_id, to_card_id, flow_type, amount) VALUES(?, ?, 0, ?)";
        jdbcTemplate.update(inFlowSql, fromCardId, toCardId, amount);

        System.out.println("=== 转账事务完成 ===");
    }

    /**
     * 事件2：用户B转账给用户A - 模拟异常事务回滚
     * 严格按照PPT中的异常回滚示例
     */
    @Transactional
    public void transferAccountsWithException(Integer fromCardId, Integer toCardId, BigDecimal amount) {
        System.out.println("=== 开始转账事务（模拟异常） ===");

        // 1. 检查转出方余额
        BigDecimal fromBalance = getBalance(fromCardId);
        if (fromBalance.compareTo(amount) < 0) {
            throw new RuntimeException("余额不足");
        }

        // 2. 扣除转出方金额
        String deductSql = "UPDATE bank_card SET balance = balance - ? WHERE id = ?";
        jdbcTemplate.update(deductSql, amount, fromCardId);
        System.out.println("扣除转出方金额: " + amount);

        // 3. 增加转入方金额
        String addSql = "UPDATE bank_card SET balance = balance + ? WHERE id = ?";
        jdbcTemplate.update(addSql, amount, toCardId);
        System.out.println("增加转入方金额: " + amount);

        // 4. 模拟错误 - 严格按照PPT中的异常示例
        System.out.println("模拟异常发生...");
        int a = 0 / 0; // 这将抛出ArithmeticException

        // 5. 记录流水（不会执行到这里）
        String flowSql = "INSERT INTO transaction_flow(from_card_id, to_card_id, flow_type, amount) VALUES(?, ?, 1, ?)";
        jdbcTemplate.update(flowSql, fromCardId, toCardId, amount);
    }

    /**
     * 演示事务超时设置 - 严格按照PPT中的timeout属性示例
     */
    @Transactional(timeout = 1)
    public void transferWithTimeout(Integer fromCardId, Integer toCardId, BigDecimal amount) throws InterruptedException {
        System.out.println("=== 测试事务超时 ===");

        // 模拟超时 - 严格按照PPT中的Thread.sleep示例
        Thread.sleep(3000); // 睡眠3秒，超过设置的1秒超时时间

        String deductSql = "UPDATE bank_card SET balance = balance - ? WHERE id = ?";
        jdbcTemplate.update(deductSql, amount, fromCardId);
    }

    /**
     * 查询交易流水
     */
    public List<TransactionFlow> getTransactionFlows() {
        String sql = "SELECT * FROM transaction_flow ORDER BY create_time DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TransactionFlow.class));
    }

    /**
     * 查询所有银行卡信息
     */
    public List<BankCard> getAllBankCards() {
        String sql = "SELECT * FROM bank_card";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BankCard.class));
    }
}