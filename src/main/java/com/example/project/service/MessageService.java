package com.example.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.entity.Account;
import com.example.project.entity.Message;
import com.example.project.repository.AccountRepository;
import com.example.project.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }   

    public Message createMessage(Message message) {

        Account account = accountRepository.findById(message.getPostedBy()).orElse(null);

        if (account == null) {
            return null;
        }
        return messageRepository.save(message);
    }
}




