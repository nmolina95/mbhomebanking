package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {
    private long id;
    private double amount;
    private String description;
    private LocalDateTime date;
    private TransactionType type;
    private double balance;
    private boolean isActive;

    public TransactionDTO(){}
    public TransactionDTO(Transaction transaction){
        setId(transaction.getId());
        setAmount(transaction.getAmount());
        setDescription(transaction.getDescription());
        setDate(transaction.getDate());
        setType(transaction.getType());
        setBalance(transaction.getBalance());
        setActive(transaction.isActive());
    }

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getBalance() { return balance; }

    public void setBalance(double balance) { this.balance = balance; }

    public boolean isActive() { return isActive; }

    public void setActive(boolean active) { isActive = active; }
}
