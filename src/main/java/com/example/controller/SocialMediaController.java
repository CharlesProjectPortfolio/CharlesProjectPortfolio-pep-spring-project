package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
//@RequestMapping("localhost:8080")
public class SocialMediaController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;


    @PostMapping("/register")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> verifyAccount(@RequestBody Account account) {
        return accountService.verifyAccount(account);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        return messageService.createMessage(message);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int message_id) {
        return messageService.getMessageById(message_id);
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int message_id) {
        return messageService.deleteMessage(message_id);
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> patchMessage(@PathVariable int message_id, @RequestBody Message message) {
        return messageService.patchMessage(message_id, message);
    }

    @GetMapping("/accounts/{accountId}/messages")
        public ResponseEntity<List<Message>> getAllMessagesByUser(@PathVariable int accountId) {
            return messageService.getAllMessagesByUser(accountId);
        }
}
