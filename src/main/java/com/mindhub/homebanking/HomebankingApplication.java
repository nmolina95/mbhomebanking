package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
									  TransactionRepository transactionRepository, LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return (args) -> {
			Client melba = clientRepository.save(new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("melba")));
			Client nacho = clientRepository.save(new Client("Ignacio", "Molina", "ignaciomolina@hotmail.com", passwordEncoder.encode("nacho")));
			Client admin = clientRepository.save(new Client("admin", "admin", "admin@admin.com", passwordEncoder.encode("admin")));
			Account cuenta1 = accountRepository.save(new Account("VIN-61900418", LocalDateTime.now() ,5000, AccountType.PESO, melba));
			Account cuenta2 = accountRepository.save(new Account("VIN-85352440", LocalDateTime.now().plus(Period.ofDays(1)) ,7500, AccountType.PESO, melba));
			Account cuenta3 = accountRepository.save(new Account("VIN-11233798", LocalDateTime.now() ,950031, AccountType.PESO, nacho));
			Account cuenta4 = accountRepository.save(new Account("VIN-72142833", LocalDateTime.now().plus(Period.ofDays(1)) ,15000, AccountType.DOLAR, nacho));
			Transaction gastoMelba1 = transactionRepository.save(new Transaction(5540, "Gasto en boludeces", LocalDateTime.now(), TransactionType.DEBIT, cuenta1));
			Transaction gastoMelba2 = transactionRepository.save(new Transaction(12341, "Pago por adelantado", LocalDateTime.now(), TransactionType.CREDIT, cuenta1));
			Transaction gastoMelba3 = transactionRepository.save(new Transaction(871.50, "Rappi", LocalDateTime.now(), TransactionType.DEBIT, cuenta1));
			Transaction gastoMelba4 = transactionRepository.save(new Transaction(22251, "Restaurant Kansas", LocalDateTime.now().minus(Period.ofMonths(1)), TransactionType.DEBIT, cuenta1));
			Transaction gastoMelba5 = transactionRepository.save(new Transaction(16780, "Gucci", LocalDateTime.now().minus(Period.ofMonths(5)), TransactionType.DEBIT, cuenta1));
			Transaction gastoMelba6 = transactionRepository.save(new Transaction(15210, "Nike", LocalDateTime.now().minus(Period.ofMonths(3)), TransactionType.DEBIT, cuenta2));
			Transaction gasto4 = transactionRepository.save(new Transaction(1500, "Shell", LocalDateTime.now(), TransactionType.DEBIT, cuenta4));
			Transaction gasto5 = transactionRepository.save(new Transaction(365.50, "Transferencia de Juan Pérez", LocalDateTime.now(), TransactionType.CREDIT, cuenta4));
			Transaction gasto6 = transactionRepository.save(new Transaction(2570.30, "Transferencia de Armando Bloques", LocalDateTime.now(), TransactionType.CREDIT, cuenta4));
			Transaction gastoNacho1 = transactionRepository.save(new Transaction(75000, "Pago de FMI", LocalDateTime.now().minus(Period.ofMonths(12)), TransactionType.CREDIT, cuenta3));
			Transaction gastoNacho2 = transactionRepository.save(new Transaction(5540, "Marotes Shopping", LocalDateTime.now().minus(Period.ofMonths(11)), TransactionType.DEBIT, cuenta3));
			Transaction gastoNacho3 = transactionRepository.save(new Transaction(25018, "Restaurante Kansas", LocalDateTime.now().minus(Period.ofMonths(10)), TransactionType.DEBIT, cuenta3));
			Transaction gastoNacho4 = transactionRepository.save(new Transaction(13400, "Las Marías - Lo mejor para tu mate", LocalDateTime.now().minus(Period.ofMonths(9)), TransactionType.DEBIT, cuenta3));
			Transaction gastoNacho5 = transactionRepository.save(new Transaction(21003, "Ralph Lauren", LocalDateTime.now().minus(Period.ofMonths(8)), TransactionType.DEBIT, cuenta3));
			Transaction gastoNacho6 = transactionRepository.save(new Transaction(11200, "TuTicket*ACDC", LocalDateTime.now().minus(Period.ofMonths(7)), TransactionType.DEBIT, cuenta3));
			Transaction gastoNacho7 = transactionRepository.save(new Transaction(55400, "Guitarras Fender", LocalDateTime.now().minus(Period.ofMonths(6)), TransactionType.DEBIT, cuenta3));
			Transaction gastoNacho8 = transactionRepository.save(new Transaction(69000, "Acreditación de sueldo", LocalDateTime.now().minus(Period.ofMonths(5)), TransactionType.CREDIT, cuenta3));
			Transaction gastoNacho9 = transactionRepository.save(new Transaction(15073, "Blockbuster", LocalDateTime.now().minus(Period.ofMonths(5)), TransactionType.DEBIT, cuenta3));
			Transaction gastoNacho10 = transactionRepository.save(new Transaction(32451, "Supermercado", LocalDateTime.now().minus(Period.ofMonths(4)), TransactionType.DEBIT, cuenta3));
			Transaction gastoNacho11 = transactionRepository.save(new Transaction(5540, "Gasto en boludeces", LocalDateTime.now().minus(Period.ofMonths(3)), TransactionType.DEBIT, cuenta3));
			Transaction gastoNacho12 = transactionRepository.save(new Transaction(3650.50, "Ypf opessa azurduy", LocalDateTime.now().minus(Period.ofMonths(2)), TransactionType.DEBIT, cuenta3));
			Transaction gastoNacho13 = transactionRepository.save(new Transaction(20000, "Servicio pago a proveedores", LocalDateTime.now().minus(Period.ofMonths(1)), TransactionType.DEBIT, cuenta3));
			Transaction gastoNacho14 = transactionRepository.save(new Transaction(5200, "Extracción cajero", LocalDateTime.now(), TransactionType.DEBIT, cuenta3));
			Loan prestamo1 = loanRepository.save(new Loan("Hipotecario", 500000, List.of(12, 24, 36, 48, 60), List.of(30,40,50,60)));
			Loan prestamo2 = loanRepository.save(new Loan("Personal", 100000, List.of(6, 12, 24), List.of(30,40,50,60)));
			Loan prestamo3 = loanRepository.save(new Loan("Automotriz", 300000, List.of(6, 12, 24, 36), List.of(30,40,50,60)));
			ClientLoan prestamoMelba1 = clientLoanRepository.save(new ClientLoan(400000, 60, melba, prestamo1, 40));
			ClientLoan prestamoMelba2 = clientLoanRepository.save(new ClientLoan(50000, 12, melba, prestamo2, 40));
			ClientLoan prestamoNacho1 = clientLoanRepository.save(new ClientLoan(120000, 24, nacho, prestamo2, 40));
			ClientLoan prestamoNacho2 = clientLoanRepository.save(new ClientLoan(240000, 36, nacho, prestamo3, 40));
			Card tarjetaMelba1 = cardRepository.save(new Card(melba.getFirstName() + " " + melba.getLastName(), "4571 0492 9123 2874", 299, LocalDateTime.now(), LocalDateTime.now().plus(Period.ofYears(5)), CardType.DEBIT, CardColor.GOLD, melba));
			Card tarjetaMelba2 = cardRepository.save(new Card(melba.getFirstName() + " " + melba.getLastName(), "8223 9385 3103 5381", 732, LocalDateTime.now(), LocalDateTime.now().plus(Period.ofYears(5)), CardType.DEBIT, CardColor.TITANIUM, melba));
			Card tarjetaNacho1 = cardRepository.save(new Card(nacho.getFirstName() + " " + nacho.getLastName(), "9312 0234 7162 3491", 911, LocalDateTime.now(), LocalDateTime.now().plus(Period.ofYears(5)), CardType.CREDIT, CardColor.TITANIUM, nacho));
			Card tarjetaNacho2 = cardRepository.save(new Card(nacho.getFirstName() + " " + nacho.getLastName(), "4381 9204 5639 8320", 106, LocalDateTime.now(), LocalDateTime.now().plus(Period.ofYears(5)), CardType.DEBIT, CardColor.GOLD, nacho));
			Card tarjetaNacho3 = cardRepository.save(new Card(nacho.getFirstName() + " " + nacho.getLastName(), "2819 4392 8391 0033", 842, LocalDateTime.now(), LocalDateTime.now().plus(Period.ofYears(5)), CardType.CREDIT, CardColor.SILVER, nacho));
		};
	}

}
