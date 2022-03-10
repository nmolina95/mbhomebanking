package com.mindhub.homebanking.dtos;

public class PosnetDTO {
    private String number;
    private int cvv;
    private double amount;
    private String description;
    private String account;

    public PosnetDTO(){}
    public PosnetDTO(String number, int cvv, double amount, String description, String account){
        setNumber(number);
        setCvv(cvv);
        setAmount(amount);
        setDescription(description);
        setAccount(account);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
