package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private AccountType accountType;
    private boolean isActive;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    Set<Transaction> transactions = new HashSet<>();

    public Account(){}
    public Account(String number, LocalDateTime creationDate, double balance, AccountType accountType) {
        setNumber(number);
        setCreationDate(creationDate);
        setBalance(balance);
        setAccountType(accountType);
        setActive(true);
    }
    public Account(String number, LocalDateTime creationDate, double balance, AccountType accountType, Client client){
        setNumber(number);
        setCreationDate(creationDate);
        setBalance(balance);
        setAccountType(accountType);
        setClient(client);
        setActive(true);
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

    public double getBalance() {
        return balance;
    }

    public Client getClient() {
        return client;
    }

    public Set<Transaction> getTransactions(){ return this.transactions; }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setClient(Client client){
        this.client = client;
    }

    public AccountType getAccountType() { return accountType; }

    public void setAccountType(AccountType accountType) { this.accountType = accountType; }

    public boolean isActive() { return isActive; }

    public void setActive(boolean active) { isActive = active; }

    public void addTransaction(Transaction transaction){
        transaction.setAccount(this);
        transactions.add(transaction);
    }
}
