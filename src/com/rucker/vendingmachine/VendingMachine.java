package com.rucker.vendingmachine;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.rucker.vendingmachine.components.Display;

public class VendingMachine {
	
	private Display display = new Display();
	private BigDecimal totalMoneyReceived;

	public VendingMachine() {
		setTotalMoneyReceivedToZero();
	}
	private void setTotalMoneyReceivedToZero() {
		totalMoneyReceived = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
	}
	
	public String getDisplayMessage() {
		return display.getMessage();
	}

	public void receiveCoin(double weight, double diameter, double thickness) {
		// TODO Auto-generated method stub
		
	}

	public BigDecimal getTotalMoneyReceived() {
		return totalMoneyReceived;
	}
}
