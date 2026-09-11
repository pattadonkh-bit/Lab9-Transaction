package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.model.DepositTransaction;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.DepositTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepositService {

    private final AccountRepository accountRepository;
    private final DepositTransactionRepository depositTransactionRepository;

    public DepositService(AccountRepository accountRepository,
                          DepositTransactionRepository depositTransactionRepository) {
        this.accountRepository = accountRepository;
        this.depositTransactionRepository = depositTransactionRepository;
    }

    @Transactional
    public void deposit(Long accountId, double amount) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // เพิ่มเงินในบัญชี
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        // บันทึก Deposit Transaction
        DepositTransaction transaction = new DepositTransaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);

        depositTransactionRepository.save(transaction);
    }
}
