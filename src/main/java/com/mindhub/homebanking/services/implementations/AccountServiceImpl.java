package com.mindhub.homebanking.services.implementations;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(Collectors.toList());
    }

    @Override
    public Account saveAccount(Account account){
        return accountRepository.save(account);
    }

    @Override
    public Account findById(long id){
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account findByNumber(String number){
        return accountRepository.findByNumber(number);
    }

}
