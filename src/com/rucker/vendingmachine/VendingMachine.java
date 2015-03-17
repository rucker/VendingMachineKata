package com.rucker.vendingmachine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

import com.rucker.vendingmachine.components.CoinSlot;
import com.rucker.vendingmachine.components.Display;
import com.rucker.vendingmachine.components.KeyCodes;
import com.rucker.vendingmachine.product.Product;

public class VendingMachine {
	
	private Display display = new Display();
	private BigDecimal totalMoneyReceived;
	private CoinSlot coinSlot = new CoinSlot();
	public static HashMap<KeyCodes, Product> productCodes;
	static {
		productCodes = new HashMap<KeyCodes, Product>();
		productCodes.put(KeyCodes.KEY_ONE, Product.COLA);
		productCodes.put(KeyCodes.KEY_TWO, Product.CHIPS);
		productCodes.put(KeyCodes.KEY_THREE, Product.CANDY);
	}

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
		totalMoneyReceived = totalMoneyReceived.add(coinSlot.receiveCoin(weight, diameter, thickness).value);
		display.setDisplayTotal(totalMoneyReceived.toString());
	}

	public BigDecimal getTotalMoneyReceived() {
		return totalMoneyReceived;
	}
	public Product selectKeyCode(KeyCodes selectedCode) {
		Product product = productCodes.get(selectedCode);
		if (product == null) {
			product = Product.NONE;
		}
		return product;
	}
}
