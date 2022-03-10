package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import static java.util.stream.Collectors.toSet;

public class AccountDTO {
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private AccountType accountType;
    private boolean isActive;
    Set<TransactionDTO> transactions = new HashSet<>();

    public AccountDTO(){}
    public AccountDTO(Account account) {
        setId(account.getId());
        setNumber(account.getNumber());
        setCreationDate(account.getCreationDate());
        setBalance(account.getBalance());
        setAccountType(account.getAccountType());
        setActive(account.isActive());
        setTransactions(account.getTransactions().stream().map(TransactionDTO::new).collect(toSet()));
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public double getBalance() { return balance; }

    public Set<TransactionDTO> getTransactions() { return transactions; }

    public void setId(long id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setTransactions(Set<TransactionDTO> transactions) { this.transactions = transactions; }

    public AccountType getAccountType() { return accountType; }

    public void setAccountType(AccountType accountType) { this.accountType = accountType; }

    public boolean isActive() { return isActive; }

    public void setActive(boolean active) { isActive = active; }
}
