package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Account;
import com.example.demo.model.DepositTransaction;
import com.example.demo.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // สร้างบัญชี
    public Account createAccount(Account account) {
        if (account.getBalance() == null) {
            account.setBalance(0.0);
        }

        return accountRepository.save(account);
    }

    // ดูบัญชีทั้งหมด
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // ค้นหาบัญชีตาม ID
    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ไม่พบบัญชี ID: " + id));
    }

    // ฝากเงิน
    @Transactional
    public Account deposit(Long accountId, Double amount) {

        if (amount == null || amount <= 0) {
            throw new RuntimeException("จำนวนเงินฝากต้องมากกว่า 0");
        }

        // ค้นหาบัญชี
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException(
                        "ไม่พบบัญชี ID: " + accountId));

        // เพิ่มเงินในบัญชี
        account.setBalance(account.getBalance() + amount);

        // สร้างรายการฝากเงิน
        DepositTransaction transaction = new DepositTransaction();
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAccount(account);

        // เพิ่ม transaction เข้า Account
        if (account.getTransactions() != null) {
            account.getTransactions().add(transaction);
        }

        // save account
        return accountRepository.save(account);
    }
}
