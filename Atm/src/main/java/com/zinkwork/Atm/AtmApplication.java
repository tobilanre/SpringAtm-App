package com.zinkwork.Atm;

import com.zinkwork.Atm.entity.JpaAccount;
import com.zinkwork.Atm.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class AtmApplication implements CommandLineRunner {

	@Autowired
	AccountRepository accountRepository;

	public static void main(String[] args) {
		SpringApplication.run(AtmApplication.class, args);
	}

	@Override
	public void run(String... arg0) {

		JpaAccount account1 = new JpaAccount();
		account1.setAccountId("1");
		account1.setAccountNumber("123456789");
		account1.setPin("1234");
		account1.setBalance(new BigDecimal(800));
		account1.setOverdraft(new BigDecimal(200));

		JpaAccount account2 = new JpaAccount();
		account2.setAccountId("2");
		account2.setAccountNumber("987654321");
		account2.setPin("4321");
		account2.setBalance(new BigDecimal(1230));
		account2.setOverdraft(new BigDecimal(150));

		accountRepository.saveAllAndFlush(List.of(account1, account2));
	}

}
