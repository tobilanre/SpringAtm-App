package com.zinkwork.Atm;

import com.zinkwork.Atm.exception.UnsuccessfulTransactionException;
import com.zinkwork.Atm.service.DenominationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DenominationServiceTest {

    @Autowired
    DenominationService denominationService;

    @Test
    public void shouldNotWithdrawMoreThanAvailableAmount(){
        assertThrows(UnsuccessfulTransactionException.class, ()->{
            denominationService.checkAmountAvailability(new BigDecimal(1000000));
        });
    }

    @Test
    public void shouldDispenseMinimumNotes(){

        final var dispensation = denominationService.dispense(new BigDecimal(100));

        assertEquals(dispensation.size(), 1);

        assertTrue(dispensation.containsKey(new BigDecimal(50)));

        assertTrue(dispensation.containsValue(2));
    }

}
