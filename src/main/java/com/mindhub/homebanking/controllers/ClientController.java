package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AccountService accountService;
    @Autowired
    ClientService clientService;

    @GetMapping("/clients")
    public List<ClientDTO> getClients(){
        List<ClientDTO> clients = clientService.getClients();
        return clients;
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable long id){
        ClientDTO clientDTO = clientService.getClient(id);
        return clientDTO;
    }

    @GetMapping("/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication){
        ClientDTO clientDTO = clientService.findByEmail(authentication.getName());
        return clientDTO;
    }

    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password){
        Client client = clientService.findClientByEmail(email);

        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if(client != null){
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }

        Client newClient = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.saveClient(newClient);

        String newAccountNumber;
        do {newAccountNumber = "VIN-" + getRandomInt(10000000, 99999999);}
        while(accountService.findByNumber(newAccountNumber) != null);

        accountService.saveAccount(new Account(newAccountNumber, LocalDateTime.now(), 0, AccountType.PESO, client));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public int getRandomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
