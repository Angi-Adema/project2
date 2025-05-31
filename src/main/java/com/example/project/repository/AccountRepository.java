package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUsername(String username);
} 


