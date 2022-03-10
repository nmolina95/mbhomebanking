package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String name;
    private double maxAmount;

    @ElementCollection
    @Column(name = "payment")
    private List<Integer> payments = new ArrayList<>();

    @ElementCollection
    @Column(name="interests")
    private List<Integer> interests = new ArrayList<>();

    @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    public Loan(){}

    public Loan(String name, double maxAmount, List<Integer> payments, List<Integer> interests) {
        setName(name);
        setMaxAmount(maxAmount);
        setPayments(payments);
        setInterests(interests);
    }

    public long getId() {
        return id;
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

    public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setLoan(this);
        clientLoans.add(clientLoan);
    }

    public List<Client> getClients(){
        return clientLoans.stream().map(clientLoan -> clientLoan.getClient()).collect(toList());
    }

}
