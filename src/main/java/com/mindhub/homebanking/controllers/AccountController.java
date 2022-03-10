package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import com.mindhub.homebanking.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    AccountService accountService;
    @Autowired
    ClientService clientService;
    @Autowired
    TransactionService transactionService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        List<AccountDTO> accounts = accountService.getAccounts();
        return accounts;
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable long id){
        AccountDTO account = new AccountDTO(accountService.findById(id));
        return account;
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication,
                                                @RequestParam AccountType accountType){
        Client client = clientService.findClientByEmail(authentication.getName());
        List<Account> accounts = new ArrayList<>();

        client.getAccounts().forEach(account -> {
            if(account.isActive() == true){
                accounts.add(account);
            }
        });

        if(accounts.size() == 3){
            return new ResponseEntity<>("Client can't have more than three accounts", HttpStatus.FORBIDDEN);
        }
        if(accountType == null){
            return new ResponseEntity<>("Account type is not valid", HttpStatus.FORBIDDEN);
        }

        String newAccountNumber;
        do { newAccountNumber = AccountUtils.getAccountNumber();}
        while(accountService.findByNumber(newAccountNumber) != null);

        accountService.saveAccount(new Account(newAccountNumber, LocalDateTime.now(), 0, accountType, client));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/clients/current/accounts/delete/{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable long id, Authentication authentication){
        Client client = clientService.findClientByEmail(authentication.getName());
        Account account = accountService.findById(id);
        Set<Transaction> transactions = account.getTransactions();
        Boolean hasAccount = client.getAccounts().contains(account);

        if(account == null){
            return new ResponseEntity<>("Account doesn't exist", HttpStatus.FORBIDDEN);
        }

        if(!hasAccount){
            return new ResponseEntity<>("Account doesn't belong to authenticated client", HttpStatus.FORBIDDEN);
        }

        transactions.forEach(transaction -> {
            transaction.setActive(false);
            transactionService.saveTransaction(transaction);
        });
        account.setActive(false);
        accountService.saveAccount(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
