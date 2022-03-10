package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    LoanService loanService;
    @Autowired
    AccountService accountService;
    @Autowired
    ClientService clientService;
    @Autowired
    ClientLoanService clientLoanService;
    @Autowired
    TransactionService transactionService;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans(){
        List<LoanDTO> loans = loanService.getLoans();
        return loans;
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> newLoan(@RequestBody LoanApplicationDTO loanApplicationDTO,
                                          Authentication authentication){
        Loan loan = loanService.findById(loanApplicationDTO.getId());
        Account account = accountService.findByNumber(loanApplicationDTO.getDestinyAccount());
        Client client = clientService.findClientByEmail(authentication.getName());
        Boolean belongsToClient = client.getAccounts().contains(account);
        Integer interest = loanApplicationDTO.getInterest();
        Boolean hasInterest = loan.getInterests().contains(interest);
        double amount = loanApplicationDTO.getAmount();
        Integer payments = loanApplicationDTO.getPayments();

        if(amount == 0 || payments == null){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if(loan == null){
            return new ResponseEntity<>("Loan doesn't exist", HttpStatus.FORBIDDEN);
        }
        if(amount > loan.getMaxAmount()){
            return new ResponseEntity<>("Requested amount exceeds loan maximum", HttpStatus.FORBIDDEN);
        }
        if(!loan.getPayments().contains(payments)){
            return new ResponseEntity<>("The required payment installments are not available", HttpStatus.FORBIDDEN);
        }
        if(account == null){
            return new ResponseEntity<>("Destiny account doesn't exist", HttpStatus.FORBIDDEN);
        }
        if(!belongsToClient){
            return new ResponseEntity<>("Destiny account doesn't belong to authenticated client", HttpStatus.FORBIDDEN);
        }
        if(!hasInterest){
            return new ResponseEntity<>("Interest selected is not available", HttpStatus.FORBIDDEN);
        }

        clientLoanService.saveClientLoan(new ClientLoan(amount * 1.2, payments, client, loan, interest));
        transactionService.saveTransaction(new Transaction(amount, "Loan approved", LocalDateTime.now(), TransactionType.CREDIT, account));
        account.setBalance(account.getBalance() + amount);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
