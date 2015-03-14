package com.rucker.vendingmachine.components;

public class Display {

	public static final String INSERT_COIN = "INSERT COIN";
	
	private String message;
	
	public Display() {
		message = INSERT_COIN;
	}
	
	public String getMessage() {
		return message;
	}

}
