package com.onlineshopping;
import java.util.*;
public class Order {
	 private User user;
	    private Cart cart;
	    private PaymentMethod paymentMethod;

	    public Order(User user, Cart cart, PaymentMethod paymentMethod) {
	        this.user = user;
	        this.cart = cart;
	        this.paymentMethod = paymentMethod;
	    }

	    public User getUser() {
	        return user;
	    }

	    public Cart getCart() {
	        return cart;
	    }

	    public PaymentMethod getPaymentMethod() {
	        return paymentMethod;
	    }
}
