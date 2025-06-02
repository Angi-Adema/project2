package com.example.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.entity.Account;
import com.example.project.exception.DuplicateUsernameException;
import com.example.project.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account account) {
        if (accountRepository.findByUsername(account.getUsername()) != null) {
            throw new DuplicateUsernameException("Username already exists.");
            //return null;
        }
        return accountRepository.save(account);
    }

    public Account login(String username, String password) {
        return accountRepository.findByUsernameAndPassword(username, password);
    }
}


