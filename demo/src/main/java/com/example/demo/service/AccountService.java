package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Account;
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
                .orElseThrow(() ->
                        new RuntimeException("ไม่พบบัญชี ID: " + id));
    }
}