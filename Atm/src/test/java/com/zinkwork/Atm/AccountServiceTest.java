package com.zinkwork.Atm;

import com.zinkwork.Atm.entity.JpaAccount;
import com.zinkwork.Atm.model.Account;
import com.zinkwork.Atm.repository.AccountRepository;
import com.zinkwork.Atm.service.AccountService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void shouldGetAccountById(){
        final var account = accountService.getAccountById("0001");
        assertTrue(account.isPresent());
    }

    @Test
    public void shouldGetAccountByNumber(){
        final var account = accountService.getAccountByAccountNumber("002");
        assertTrue(account.isPresent());
    }

    @Test
    public void shouldConfirmAccountPin(){
        Account acc = Account.builder().accountId("001")
                .accountNumber("20304030").pin("1234")
                .balance(new BigDecimal(100)).overdraft(new BigDecimal(100))
                .build();
        assertTrue(accountService.accountHasPin(acc, "1234"));
    }

    @Test
    public void shouldAssertForWithdrawable(){
        Account acc = Account.builder().accountId("001")
                .accountNumber("20304030").pin("1234")
                .balance(new BigDecimal(100)).overdraft(new BigDecimal(100))
                .build();
        assertTrue(accountService.accountHasWithdrawable(acc, new BigDecimal(30)));
    }


    @Test
    public void shouldAssertForWithdrawableWihOverDraft(){
        Account acc = Account.builder().accountId("001")
                .accountNumber("20304030").pin("1234")
                .balance(new BigDecimal(100)).overdraft(new BigDecimal(100))
                .build();
        assertTrue(accountService.accountHasWithdrawableWithOverDraft(acc, new BigDecimal(150)));
    }

    @BeforeEach
    public void addTestAccount(){
        JpaAccount newAccount = new JpaAccount();
        newAccount.setAccountId("0001");
        newAccount.setAccountNumber("002");
        newAccount.setPin("12345");
        newAccount.setBalance(new BigDecimal(200));
        newAccount.setOverdraft(new BigDecimal(200));
        accountRepository.saveAndFlush(newAccount);

    }


}
