package com.onlineshopping;
import java.util.*;
class CreditCardPayment extends PaymentMethod {
    private String cardNumber;
    private String expirationDate;

    public CreditCardPayment(String cardNumber, String expirationDate) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing credit card payment of $" + amount);
    }
}
