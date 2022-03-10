package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Set;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    CardService cardService;
    @Autowired
    ClientService clientService;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createNewCard(
            @RequestParam CardColor color, @RequestParam CardType type, Authentication authentication){
        Client client = clientService.findClientByEmail(authentication.getName());
        Set<Card> cards = client.getCards();
        List<Card> cardsAmount = cards.stream().filter(card -> card.getType() == type).collect(toList());

        if(color == null || type == null){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if(cardsAmount.size() == 3){
            return new ResponseEntity<>("Can't have more than 3 cards of the same type", HttpStatus.FORBIDDEN);
        }

        String newCardNumber;
        do {
            newCardNumber = CardUtils.getCardNumber();
        }
        while(cardService.findCardByNumber(newCardNumber) != null);

        cardService.saveCard(new Card(client.getFirstName() + client.getLastName(), newCardNumber, CardUtils.getCVV(),
                LocalDateTime.now(), LocalDateTime.now().plus(Period.ofYears(5)), type, color, client));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("clients/current/cards/{id}")
    public ResponseEntity<Object> cancelCard(@PathVariable long id, Authentication authentication){
        Card card = cardService.findById(id);
        Client client = clientService.findClientByEmail(authentication.getName());
        Boolean hasCard = client.getCards().contains(card);

        if(card == null){
            return new ResponseEntity<>("Card not found", HttpStatus.FORBIDDEN);
        }
        if(!hasCard){
            return new ResponseEntity<>("Card doesn't belong to authenticated client", HttpStatus.FORBIDDEN);
        }

        card.setActive(false);
        cardService.saveCard(card);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
