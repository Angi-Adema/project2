package com.example.project.service;

import java.util.List;
import java.util.Optional;

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

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    public boolean deleteMessageById(Integer messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateMessageTextById(Integer messageId, String newText) {
        Optional<Message> optional = messageRepository.findById(messageId);

        if (optional.isPresent()) {
            Message message = optional.get();
            message.setMessageText(newText);
            messageRepository.save(message);
            return true;
        }
        return false;
    }
}




