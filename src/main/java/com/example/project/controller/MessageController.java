package com.example.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.entity.Message;
import com.example.project.service.MessageService;

// Handle messages and getting messages by id.

@RestController
public class MessageController {
    
    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // POST request to create a message. (http://localhost:8080/messages)
    @PostMapping("messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message) {

        if (message.getMessageText() == null || message.getMessageText().isBlank()) {
            return ResponseEntity.badRequest().body("Message content cannot be blank");
        }
        if (message.getMessageText().length() >= 255) {
            return ResponseEntity.badRequest().body("Message must be less than 255 characters");
        }
        if (message.getPostedBy() == null) {
            return ResponseEntity.badRequest().body("PostedBy (accountId) must be specified");
        }

        Message created = messageService.createMessage(message);

        if (created == null) {
            return ResponseEntity.badRequest().body("Invalid user ID. Message not created.");
        } else {
            return ResponseEntity.ok(created);
        }
    }
}
