package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.PosnetDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class PosnetController {

    @Autowired
    CardService cardService;
    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;

    @Transactional
    @PostMapping("/posnet")
    public ResponseEntity<Object> postnet(Authentication authentication, @RequestBody PosnetDTO posnetDTO){
        Client client = clientService.findClientByEmail(authentication.getName());
        Card card = cardService.findCardByNumber(posnetDTO.getNumber());
        Account account = accountService.findByNumber(posnetDTO.getAccount());
        Boolean hasCard = client.getCards().contains(card);
        Boolean hasAccount = client.getAccounts().contains(account);

        if(client == null){
            return new ResponseEntity<>("Client not found", HttpStatus.FORBIDDEN);
        }
        if(!hasCard){
            return new ResponseEntity<>("Card doesn't belong to current client", HttpStatus.FORBIDDEN);
        }
        if(!hasAccount){
            return new ResponseEntity<>("Accoun't doesn't belong to current client", HttpStatus.FORBIDDEN);
        }
        if(!account.isActive()){
            return new ResponseEntity<>("Account is not active", HttpStatus.FORBIDDEN);
        }
        if(account.getBalance() < posnetDTO.getAmount()){
            return new ResponseEntity<>("Insufficient funds", HttpStatus.FORBIDDEN);
        }
        if(!card.isActive()){
            return new ResponseEntity<>("Card is not active", HttpStatus.FORBIDDEN);
        }
        if(card.getCvv() != posnetDTO.getCvv()){
            return new ResponseEntity<>("Card CVV is incorrect", HttpStatus.FORBIDDEN);
        }
        if(LocalDateTime.now().isAfter(card.getThruDate())){
            return new ResponseEntity<>("Card has expired", HttpStatus.FORBIDDEN);
        }

        transactionService.saveTransaction(new Transaction(posnetDTO.getAmount(), posnetDTO.getDescription(), LocalDateTime.now(), TransactionType.DEBIT, account));
        account.setBalance(account.getBalance() - posnetDTO.getAmount());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
