package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    TransactionService transactionService;
    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createDebitTransaction(@RequestParam double amount, @RequestParam String description,
                                                         @RequestParam String sender, @RequestParam String receiver, Authentication authentication){
        Client client = clientService.findClientByEmail(authentication.getName());
        Account senderAccount = accountService.findByNumber(sender);
        Account receiverAccount = accountService.findByNumber(receiver);
        boolean hasAccount = client.getAccounts().contains(senderAccount);
        boolean hasFunds = senderAccount.getBalance() >= amount;

        if(amount == 0 || description.isEmpty() || sender.isEmpty() || receiver.isEmpty()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if(amount < 0){
            return new ResponseEntity<>("Amount can't be negative", HttpStatus.FORBIDDEN);
        }
        if(senderAccount == receiverAccount){
            return new ResponseEntity<>("Sender and receiver accounts are the same", HttpStatus.FORBIDDEN);
        }
        if(senderAccount == null){
            return new ResponseEntity<>("Sender account doesn't exists", HttpStatus.FORBIDDEN);
        }
        if(!receiverAccount.isActive()){
            return new ResponseEntity<>("Receiver account is not active", HttpStatus.FORBIDDEN);
        }
        if(!senderAccount.isActive()){
            return new ResponseEntity<>("Sender account is not active", HttpStatus.FORBIDDEN);
        }
        if(!hasAccount){
            return new ResponseEntity<>("Sender account doesn't belong to logged in user", HttpStatus.FORBIDDEN);
        }
        if(receiverAccount == null){
            return new ResponseEntity<>("Receiver account doesn't exists", HttpStatus.FORBIDDEN);
        }
        if(!hasFunds){
            return new ResponseEntity<>("Insufficient funds", HttpStatus.FORBIDDEN);
        }

        senderAccount.setBalance(senderAccount.getBalance() - amount);
        receiverAccount.setBalance(receiverAccount.getBalance() + amount);
        transactionService.saveTransaction(new Transaction(amount, description + " - Transfer to " + receiver, LocalDateTime.now(), TransactionType.DEBIT, senderAccount));
        transactionService.saveTransaction(new Transaction(amount, description + " - Transfer from " + sender, LocalDateTime.now(), TransactionType.CREDIT, receiverAccount));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
