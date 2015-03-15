package com.rucker.vendingmachine;

import com.rucker.vendingmachine.components.Display;

public class VendingMachine {
	
	private Display display = new Display();
	
	public String getDisplayMessage() {
		return display.getMessage();
	}
}
