package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;

import java.time.LocalDateTime;

public class CardDTO {
    private long id;
    private String cardholder;
    private String number;
    private int cvv;
    private CardType type;
    private CardColor color;
    private LocalDateTime fromDate;
    private LocalDateTime thruDate;
    private boolean isActive;

    public CardDTO(){}
    public CardDTO(Card card){
        setId(card.getId());
        setCardholder(card.getCardholder());
        setNumber(card.getNumber());
        setCvv(card.getCvv());
        setType(card.getType());
        setColor(card.getColor());
        setFromDate(card.getFromDate());
        setThruDate(card.getThruDate());
        setActive(card.isActive());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCardholder() {
        return cardholder;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
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

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public boolean isActive() { return isActive; }

    public void setActive(boolean active) { isActive = active; }
}
