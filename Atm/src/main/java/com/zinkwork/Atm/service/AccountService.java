package com.zinkwork.Atm.service;

import com.zinkwork.Atm.exception.UnsuccessfulTransactionException;
import com.zinkwork.Atm.model.Account;
import com.zinkwork.Atm.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;


    public Optional<Account> getAccountById(String accountId){
        return accountRepository.findById(accountId).map(Account::fromEntity);
    }


    public Optional<Account> getAccountByAccountNumber(String accountNumber){
        return accountRepository.findJpaAccountByAccountNumber(accountNumber)
                .map(Account::fromEntity);
    }

    public boolean accountHasPin(Account account, String pin){
        return Optional.ofNullable(account)
                .filter(acc -> acc.getPin().equals(pin))
                .map(o -> true).orElseThrow(() -> new UnsuccessfulTransactionException("Wrong pin"));
    }

    public void withDrawFromBalance(Account account, BigDecimal amount) {
        Account acc = Account.builder().accountId(account.getAccountId())
                .accountNumber(account.getAccountNumber()).pin(account.getPin())
                .balance(account.getBalance().subtract(amount)).overdraft(account.getOverdraft())
                .build();
        accountRepository.save(acc.toEntity());
    }

    public void withDrawWithOverDraft(Account account, BigDecimal amount) {
        final var balanceOutStanding = amount.subtract(account.getBalance());
        Account acc = Account.builder().accountId(account.getAccountId())
                .accountNumber(account.getAccountNumber()).pin(account.getPin())
                .balance(BigDecimal.ZERO).overdraft(account.getOverdraft().subtract(balanceOutStanding))
                .build();
        accountRepository.save(acc.toEntity());
    }

    public boolean accountHasWithdrawable(Account account, BigDecimal amount){
        return account.getBalance().compareTo(amount) >= 0;
    }

    public boolean accountHasWithdrawableWithOverDraft(Account account, BigDecimal amount){
        return account.getBalance().add(account.getOverdraft()).compareTo(amount) >= 0;
    }

}
