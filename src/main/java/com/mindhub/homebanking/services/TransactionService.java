package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Transaction;

public interface TransactionService {
    public Transaction saveTransaction(Transaction transaction);
}
