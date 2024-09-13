package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public ResponseEntity<Account> createAccount(Account account) {
        if (account.getUsername().length() <= 0 || account.getPassword().length() < 4) {
            return ResponseEntity.status(400).build();
        } 
       Optional<Account> testAccount = accountRepository.findByUsername(account.getUsername());
        if (testAccount.isPresent() && testAccount.get().getUsername().equals(account.getUsername())) {
            return ResponseEntity.status(409).build();
        } else {
            accountRepository.save(account);
            return ResponseEntity.status(200).body(accountRepository.findByUsername(account.getUsername()).get());
        }  
    }

    public ResponseEntity<Account> verifyAccount(Account account) {
        Optional<Account> account2 = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (account2.isPresent()) {
            if (account2.get().getUsername().equals(account.getUsername()) && account2.get().getPassword().equals(account.getPassword())) {
                return ResponseEntity.status(200).body(accountRepository.findByUsername(account.getUsername()).get());
            } else {
                return ResponseEntity.status(200).build();
            }
        } else {
            return ResponseEntity.status(401).body(account);
        }
    }
}
