package com.rucker.vendingmachine.components;

public class Display {

	public static final String INSERT_COIN = "INSERT COIN";
	private static final String CURRENCY_PREFIX = "$";
	
	private String message;
	
	public Display() {
		message = INSERT_COIN;
	}
	
	public String getMessage() {
		return message;
	}

	public void setDisplayTotal(String string) {
		message = CURRENCY_PREFIX + string;
	}

}
