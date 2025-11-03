package com.example.main;

import com.example.service.BankService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Scanner;

public class TransactionMain {
    public static void main(String[] args) {
        // 加载Spring配置文件 - 严格按照PPT中的ApplicationContext使用方式
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        // 获取BankService bean
        BankService bankService =
                (BankService) applicationContext.getBean("bankService");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Spring事务管理演示系统 ===");
            System.out.println("1. 查看所有银行卡信息");
            System.out.println("2. 正常转账事务（用户A→用户B）");
            System.out.println("3. 异常转账事务（用户B→用户A，模拟回滚）");
            System.out.println("4. 查看交易流水");
            System.out.println("5. 测试事务超时");
            System.out.println("6. 退出系统");
            System.out.print("请选择操作: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        displayBankCards(bankService);
                        break;
                    case "2":
                        performNormalTransfer(bankService);
                        break;
                    case "3":
                        performExceptionTransfer(bankService);
                        break;
                    case "4":
                        displayTransactionFlows(bankService);
                        break;
                    case "5":
                        testTransactionTimeout(bankService);
                        break;
                    case "6":
                        System.out.println("感谢使用，再见！");
                        return;
                    default:
                        System.out.println("无效选择，请重新输入！");
                }
            } catch (Exception e) {
                System.out.println("操作发生异常: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void displayBankCards(BankService bankService) {
        System.out.println("\n=== 银行卡信息 ===");
        bankService.getAllBankCards().forEach(card -> {
            System.out.println("卡ID: " + card.getId() +
                    ", 卡号: " + card.getCardNumber() +
                    ", 余额: " + card.getBalance() +
                    ", 持卡人: " + getCardHolderName(card.getId()));
        });
    }

    private static String getCardHolderName(Integer cardId) {
        switch (cardId) {
            case 1: return "兰天从";
            case 2: return "张三";
            case 3: return "李四";
            default: return "未知";
        }
    }

    private static void performNormalTransfer(BankService bankService) {
        System.out.println("\n=== 执行正常转账事务 ===");

        // 用户A(卡ID:1) 转账给 用户B(卡ID:2) 1000元
        Integer fromCardId = 1; // 兰天从的卡
        Integer toCardId = 2;   // 张三的卡
        BigDecimal amount = new BigDecimal("1000.00");

        System.out.println("转账前余额:");
        System.out.println("转出方(卡" + fromCardId + ")余额: " + bankService.getBalance(fromCardId));
        System.out.println("转入方(卡" + toCardId + ")余额: " + bankService.getBalance(toCardId));

        try {
            bankService.transferAccounts(fromCardId, toCardId, amount);
            System.out.println("转账成功！");
        } catch (Exception e) {
            System.out.println("转账失败: " + e.getMessage());
        }

        System.out.println("转账后余额:");
        System.out.println("转出方(卡" + fromCardId + ")余额: " + bankService.getBalance(fromCardId));
        System.out.println("转入方(卡" + toCardId + ")余额: " + bankService.getBalance(toCardId));
    }

    private static void performExceptionTransfer(BankService bankService) {
        System.out.println("\n=== 执行异常转账事务（演示回滚） ===");

        // 用户B(卡ID:2) 转账给 用户A(卡ID:1) 500元，但会模拟异常
        Integer fromCardId = 2; // 张三的卡
        Integer toCardId = 1;   // 兰天从的卡
        BigDecimal amount = new BigDecimal("500.00");

        System.out.println("转账前余额:");
        System.out.println("转出方(卡" + fromCardId + ")余额: " + bankService.getBalance(fromCardId));
        System.out.println("转入方(卡" + toCardId + ")余额: " + bankService.getBalance(toCardId));

        try {
            bankService.transferAccountsWithException(fromCardId, toCardId, amount);
            System.out.println("转账成功！");
        } catch (Exception e) {
            System.out.println("转账失败（预期中）: " + e.getMessage());
            System.out.println("由于发生异常，事务已回滚！");
        }

        System.out.println("转账后余额（应该与转账前相同）:");
        System.out.println("转出方(卡" + fromCardId + ")余额: " + bankService.getBalance(fromCardId));
        System.out.println("转入方(卡" + toCardId + ")余额: " + bankService.getBalance(toCardId));
    }

    private static void displayTransactionFlows(BankService bankService) {
        System.out.println("\n=== 交易流水记录 ===");
        bankService.getTransactionFlows().forEach(flow -> {
            String type = flow.getFlowType() == 0 ? "收入" : "支出";
            System.out.println("流水ID: " + flow.getId() +
                    ", 转出卡: " + flow.getFromCardId() +
                    ", 转入卡: " + flow.getToCardId() +
                    ", 类型: " + type +
                    ", 金额: " + flow.getAmount() +
                    ", 时间: " + flow.getCreateTime());
        });
    }

    private static void testTransactionTimeout(BankService bankService) {
        System.out.println("\n=== 测试事务超时 ===");

        try {
            bankService.transferWithTimeout(1, 2, new BigDecimal("100.00"));
            System.out.println("事务执行成功");
        } catch (Exception e) {
            System.out.println("事务超时（预期中）: " + e.getMessage());
        }
    }
}