package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

public class ClientLoanDTO {

    private long id;
    private double amount;
    private Integer payments;
    private String name;
    private long loan_id;
    private Integer interest;

    public ClientLoanDTO(ClientLoan clientLoan){
        setId(clientLoan.getId());
        setAmount(clientLoan.getAmount());
        setPayments(clientLoan.getPayments());
        setName(clientLoan.getLoan().getName());
        setLoan_id(clientLoan.getLoan().getId());
        setInterest(clientLoan.getInterest());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(long loan_id) {
        this.loan_id = loan_id;
    }

    public Integer getInterest() { return interest; }

    public void setInterest(Integer interest) { this.interest = interest; }
}
