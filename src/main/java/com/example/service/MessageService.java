package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;

    public ResponseEntity<Message> createMessage(Message message) {
        if (message.getMessageText().length() <= 0 || message.getMessageText().length() > 255) {
            return ResponseEntity.status(400).build();
        }
        if (accountRepository.findById(message.getPostedBy()).isPresent()) {
            messageRepository.save(message);
            return ResponseEntity.status(200).body(messageRepository.findByMessageText(message.getMessageText()).get());
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.status(200).body(messageRepository.findAll());
    }

    public ResponseEntity<Message> getMessageById(int message_id) {
        if (messageRepository.findById(message_id).isPresent()) {
            return ResponseEntity.status(200).body(messageRepository.findById(message_id).get());
        } else {
            return ResponseEntity.status(200).build();
        }
    }

    public ResponseEntity<Integer> deleteMessage(int message_id) {
        if (messageRepository.findById(message_id).isPresent()) {
            messageRepository.deleteById(message_id);
            return ResponseEntity.status(200).body(1);
        } else {
            return ResponseEntity.status(200).build();
        }
    }

    public ResponseEntity<Integer> patchMessage(int message_id, Message message) {
        if (message.getMessageText().length() <= 0 ||
            message.getMessageText().length() > 255 ||
            !messageRepository.findById(message_id).isPresent()) {
                return ResponseEntity.status(400).build();
            } else {
                Message patchedMessage = messageRepository.findById(message_id).get();
                patchedMessage.setMessageText(message.getMessageText());
                messageRepository.save(patchedMessage);
                return ResponseEntity.status(200).body(1);
            }
    }

    public ResponseEntity<List<Message>> getAllMessagesByUser(int accountId) {
        return ResponseEntity.status(200).body(messageRepository.findAllByPostedBy(accountId).get());
    }
}
