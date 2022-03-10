package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import java.util.HashSet;
import java.util.Set;
import static java.util.stream.Collectors.toSet;

public class ClientDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    Set<AccountDTO> accounts = new HashSet<>();
    Set<ClientLoanDTO> loans = new HashSet<>();
    Set<CardDTO> cards = new HashSet<>();

    public ClientDTO(Client client){
        setId(client.getId());
        setFirstName(client.getFirstName());
        setLastName(client.getLastName());
        setEmail(client.getEmail());
        setAccounts(client.getAccounts().stream().map(AccountDTO::new).collect(toSet()));
        setLoans(client.getClientLoans().stream().map(ClientLoanDTO::new).collect(toSet()));
        setCards(client.getCards().stream().map(CardDTO::new).collect(toSet()));
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() { return loans; }

    public void setId(long id){
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccounts(Set<AccountDTO> accounts) {
        this.accounts = accounts;
    }

    public void setLoans(Set<ClientLoanDTO> loans) { this.loans = loans; }

    public Set<CardDTO> getCards() { return cards; }

    public void setCards(Set<CardDTO> cards) { this.cards = cards; }
}
