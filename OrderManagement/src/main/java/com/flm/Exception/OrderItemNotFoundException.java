package com.flm.Exception;

public class OrderItemNotFoundException extends RuntimeException{

	public OrderItemNotFoundException(String message) {
       super(message);		
	}
}
