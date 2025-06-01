package com.example.project.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // GET request to get all messages. (http://localhost:8080/messages)
    @GetMapping("messages")
    public ResponseEntity<?> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    // GET request to get a message by ID. (http://localhost:8080/messages/{message_id})
    @GetMapping("messages/{messageId}")
    public ResponseEntity<?> getMessageById(@PathVariable Integer messageId) {
        Message message = messageService.getMessageById(messageId);
        if (message == null) {
            return ResponseEntity.ok("");
        }
        return ResponseEntity.ok(message);
    }

    // DELETE a message by ID. (http://localhost:8080/messages/{message_id})
    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<?> deleteMessageById(@PathVariable Integer messageId) {
        boolean deleted = messageService.deleteMessageById(messageId);

        if (deleted) {
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.ok("");
        }
    }

    // PATCH request to update a message by ID. (http://localhost:8080/messages/{message_id})
    @PatchMapping("messages/{messageId}")
    public ResponseEntity<?> updateMessageText(@PathVariable Integer messageId, @RequestBody Map<String, String> updates) {
        
        String newText = updates.get("messageText");
        
        if (newText == null || newText.isBlank() || newText.length() >= 255) {
            return ResponseEntity.badRequest().build();
        }

        boolean updated = messageService.updateMessageTextById(messageId, newText);

        if (!updated) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(1);
        }
    }
}
