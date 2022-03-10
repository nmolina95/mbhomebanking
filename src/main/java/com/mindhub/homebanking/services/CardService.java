package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Card;

public interface CardService {
    public Card findCardByNumber(String number);
    public Card saveCard(Card card);
    public Card findById(long id);
}
