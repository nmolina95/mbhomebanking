package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;

import java.util.ArrayList;
import java.util.List;

public class LoanDTO {
    private long id;
    private String name;
    private double maxAmount;
    private List<Integer> payments = new ArrayList<>();
    private List<Integer> interests = new ArrayList<>();

    public LoanDTO(){}
    public LoanDTO(Loan loan) {
        setId(loan.getId());
        setName(loan.getName());
        setMaxAmount(loan.getMaxAmount());
        setPayments(loan.getPayments());
        setInterests(loan.getInterests());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public List<Integer> getInterests() { return interests; }

    public void setInterests(List<Integer> interests) { this.interests = interests; }
}
