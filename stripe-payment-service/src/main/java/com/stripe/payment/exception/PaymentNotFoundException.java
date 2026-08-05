package com.stripe.payment.exception;

public class PaymentNotFoundException extends Exception{
    private String message;
    public PaymentNotFoundException(){
        super();
    }
    public PaymentNotFoundException(String message){
        super(message);
        this.message=message;
    }
    public String getMessage(){
        return message;
    }
}
