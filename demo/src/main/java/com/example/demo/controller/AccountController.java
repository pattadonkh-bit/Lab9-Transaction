package com.example.demo.controller;

import com.example.demo.service.DepositService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deposit")
public class AccountController {

    private final DepositService depositService;

    public AccountController(DepositService depositService) {
        this.depositService = depositService;
    }

    @PostMapping
    public String deposit(
            @RequestParam Long accountId,
            @RequestParam double amount) {

        depositService.deposit(accountId, amount);

        return "Deposit successful";
    }
}

