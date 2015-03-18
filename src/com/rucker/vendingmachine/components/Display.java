package com.rucker.vendingmachine.components;

public class Display {

	public static final String INSERT_COIN = "INSERT COIN";
	public static final String THANK_YOU = "THANK YOU";	
	private static final String CURRENCY_PREFIX = "$";
	
	private String message;
	
	public Display() {
		displayInsertCoin();
	}
	
	public String getMessage() {
		return message;
	}

	public void setDisplayTotal(String string) {
		message = CURRENCY_PREFIX + string;
	}
	
	public void displayThankYou() {
		message = THANK_YOU;
	}
	
	public void displayInsertCoin() {
		message = INSERT_COIN;
	}
}
