package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Account;
import com.example.demo.model.DepositTransaction;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.DepositTransactionRepository;

@Service
public class DepositService {

    private final AccountRepository accountRepository;
    private final DepositTransactionRepository depositTransactionRepository;

    public DepositService(AccountRepository accountRepository,DepositTransactionRepository depositTransactionRepository) {
        this.accountRepository = accountRepository;
        this.depositTransactionRepository = depositTransactionRepository;
    }

    @Transactional
    public void deposit(Long accountId, double amount) {

        // ตรวจสอบจำนวนเงิน
        if (amount <= 0) {
            throw new RuntimeException("จำนวนเงินฝากต้องมากกว่า 0");
        }

        // ค้นหาบัญชี
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // เพิ่มเงินในบัญชี
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        // สร้างรายการฝากเงิน
        DepositTransaction transaction = new DepositTransaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());

        // บันทึก Deposit Transaction
        depositTransactionRepository.save(transaction);
    }
}