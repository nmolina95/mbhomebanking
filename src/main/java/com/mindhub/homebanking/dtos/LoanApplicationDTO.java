package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {
    private long id;
    private double amount;
    private Integer payments;
    private String destinyAccount;
    private Integer interest;

    public LoanApplicationDTO(){}
    public LoanApplicationDTO(long id, double amount, Integer payments, String destinyAccount, Integer interest) {
        setId(id);
        setAmount(amount);
        setPayments(payments);
        setDestinyAccount(destinyAccount);
        setInterest(interest);
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

    public String getDestinyAccount() {
        return destinyAccount;
    }

    public void setDestinyAccount(String destinyAccount) {
        this.destinyAccount = destinyAccount;
    }

    public Integer getInterest() { return interest; }

    public void setInterest(Integer interest) { this.interest = interest; }
}
