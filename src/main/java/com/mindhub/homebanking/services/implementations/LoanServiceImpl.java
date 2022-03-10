package com.mindhub.homebanking.services.implementations;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

   @Autowired
   LoanRepository loanRepository;

   public List<LoanDTO> getLoans(){
       return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
   }

   public Loan findById(long id){
       return loanRepository.findById(id).orElse(null);
   }
}
