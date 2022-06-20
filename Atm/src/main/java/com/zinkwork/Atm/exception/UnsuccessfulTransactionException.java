package com.zinkwork.Atm.exception;

public class UnsuccessfulTransactionException extends IllegalStateException {

    public UnsuccessfulTransactionException(String message){
        super(message);
    }

}
