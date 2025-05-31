package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

}


