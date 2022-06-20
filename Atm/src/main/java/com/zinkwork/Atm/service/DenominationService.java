package com.zinkwork.Atm.service;

import com.zinkwork.Atm.exception.UnsuccessfulTransactionException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class DenominationService {

    private final Map<BigDecimal, Integer> denominations;

    public DenominationService() {
        SortedMap<BigDecimal, Integer> denoms = new TreeMap<>(Comparator.reverseOrder());
        denoms.put(new BigDecimal(50), 10);
        denoms.put(new BigDecimal(20), 30);
        denoms.put(new BigDecimal(10), 30);
        denoms.put(new BigDecimal(5), 20);
        denominations = denoms;
    }

    public void checkAmountAvailability(BigDecimal amount){
        BigDecimal total = BigDecimal.ZERO;

        for(Map.Entry<BigDecimal, Integer> entry : denominations.entrySet()){
            total = total.add(entry.getKey().multiply(new BigDecimal(entry.getValue())));
        }
        if (amount.compareTo(total) > 0){
            throw new UnsuccessfulTransactionException("ATM cannot dispense this amount at this moment");
        }
    }

    public Map<BigDecimal, Integer> dispense(BigDecimal amount){

        Map<BigDecimal, Integer> notes = new HashMap<>();

        for(Map.Entry<BigDecimal, Integer> entry : denominations.entrySet()){
            if(amount.compareTo(entry.getKey()) >= 0){
                BigDecimal noteCount = amount.divide(entry.getKey());
                amount = amount.subtract(noteCount.multiply(entry.getKey()));
                notes.put(entry.getKey(), noteCount.intValue());
                denominations.put(entry.getKey(), entry.getValue() - noteCount.intValue());
            }
        }

        return notes;
    }

}
