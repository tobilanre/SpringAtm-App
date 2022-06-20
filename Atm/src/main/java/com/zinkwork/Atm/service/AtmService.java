package com.zinkwork.Atm.service;

import com.zinkwork.Atm.exception.UnsuccessfulTransactionException;
import com.zinkwork.Atm.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@Transactional
public class AtmService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private DenominationService denominationService;


    public Account getAccount(String accountId) {
        return accountService.getAccountById(accountId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Account with id %s does not exists", accountId)));
    }

    public Map<String, BigDecimal> getAccountBalanceById(String accountId, String pin){
        final var acc = accountService.getAccountById(accountId)
                .filter(account -> accountService.accountHasPin(account, pin)).get();
        return Map.of("balance", acc.getBalance(), "maximumWithdrawable" , acc.getBalance().add(acc.getOverdraft()));
    }


    public Map<String, Object> withdrawFunds(BigDecimal amount, String accountId, String pin){

        denominationService.checkAmountAvailability(amount);

        final var account = accountService.getAccountById(accountId)
                .filter(acc -> accountService.accountHasPin(acc, pin)).get();

        if(accountService.accountHasWithdrawable(account, amount)){
            accountService.withDrawFromBalance(account, amount);
        } else if(accountService.accountHasWithdrawableWithOverDraft(account, amount)){
            accountService.withDrawWithOverDraft(account, amount);
        }else {
            throw new UnsuccessfulTransactionException("You can not make this withdrawal due to insufficient funds");
        }
        return Map.of("balance", getAccountBalanceById(accountId, pin),
                "dispensation", denominationService.dispense(amount));
    }



}
