package com.zinkwork.Atm.model;


import com.zinkwork.Atm.entity.JpaAccount;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class Account {

    private final String accountId;
    private final String accountNumber;
    private final String pin;
    private final BigDecimal balance;
    private final BigDecimal overdraft;


    public static Account fromEntity(JpaAccount account){
        return Account.builder().accountId(account.getAccountId())
                .accountNumber(account.getAccountNumber()).pin(account.getPin())
                .balance(account.getBalance()).overdraft(account.getOverdraft())
                .build();
    }

    public JpaAccount toEntity(){
        JpaAccount jpaAccount = new JpaAccount();
        jpaAccount.setAccountId(accountId);
        jpaAccount.setAccountNumber(accountNumber);
        jpaAccount.setPin(pin);
        jpaAccount.setBalance(balance);
        jpaAccount.setOverdraft(overdraft);
        return jpaAccount;
    }

}
