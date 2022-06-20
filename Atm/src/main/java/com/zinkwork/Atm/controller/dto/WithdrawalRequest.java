package com.zinkwork.Atm.controller.dto;

import java.math.BigDecimal;

public class WithdrawalRequest {

    private String accountId;
    private BigDecimal amount;
    private String pin;


    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
