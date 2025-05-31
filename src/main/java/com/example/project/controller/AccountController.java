package com.example.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.entity.Account;
import com.example.project.service.AccountService;

// Handle register, login and looking up accounts by id.

@RestController
public class AccountController {
    
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // POST request to register a new user. (http://localhost:8080/register)
    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody Account account) {
        // Validate the account details
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            return ResponseEntity.badRequest().body("Username cannot be blank");
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            return ResponseEntity.badRequest().body("Password must be at least 4 characters long");
        }

        // Register the account using the service
        Account registered = accountService.registerAccount(account);
        
        if (registered != null) {
            return ResponseEntity.ok(registered);
        } else {
            return ResponseEntity.status(409).body("Username already exists");
        }

    }
}
