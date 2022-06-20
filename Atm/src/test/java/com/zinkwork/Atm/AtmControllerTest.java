package com.zinkwork.Atm;

import com.zinkwork.Atm.controller.dto.WithdrawalRequest;
import com.zinkwork.Atm.entity.JpaAccount;
import com.zinkwork.Atm.repository.AccountRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AtmControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void testGetAccountWithId() throws Exception {
        this.mockMvc.perform(get("/atm/0001")).andDo(print()).andExpect(status().isOk());
    }


    @Test
    public void testGetAccountBalanceWithPin() throws Exception {
        this.mockMvc.perform(get("/atm/balance/0001/2493")).andDo(print()).andExpect(status().isOk());
    }


    @Test
    public void testWithdrawal() throws Exception {
        WithdrawalRequest request = new WithdrawalRequest();
        request.setAccountId("0001");
        request.setPin("2493");
        request.setAmount(new BigDecimal(10));
        this.mockMvc.perform(post("/atm/withdraw/", request)).andDo(print()).andExpect(status().isOk());
    }

    @BeforeEach
    public void addTestAccount(){

        JpaAccount newAccount = new JpaAccount();
        newAccount.setAccountId("0001");
        newAccount.setAccountNumber("002");
        newAccount.setPin("2493");
        newAccount.setBalance(new BigDecimal(200));
        newAccount.setOverdraft(new BigDecimal(200));
        accountRepository.saveAndFlush(newAccount);

    }

}
