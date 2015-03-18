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
	private static HashMap<KeyCodes, Product> productCodes;
	private static HashMap<Product, Integer> inventory;
	static {
		productCodes = new HashMap<KeyCodes, Product>();
		productCodes.put(KeyCodes.KEY_ONE, Product.COLA);
		productCodes.put(KeyCodes.KEY_TWO, Product.CHIPS);
		productCodes.put(KeyCodes.KEY_THREE, Product.CANDY);
		
		inventory = new HashMap<Product, Integer> ();
		inventory.put(Product.CANDY, 5);
		inventory.put(Product.CHIPS, 5);
		inventory.put(Product.COLA, 5);
		inventory.put(Product.NONE, 0);
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
	
	public Product selectProductByKeyCode(KeyCodes selectedCode) {
		Product product = productCodes.get(selectedCode);
		if (product == null) {
			product = Product.NONE;
		}
		return product;
	}
	
	public boolean hasEnoughMoneyBeenReceivedForProduct(Product product) {
		return totalMoneyReceived.compareTo(product.price) >= 0;
	}

	public Object getQuantityAvailable(Product candy) {
		return inventory.get(candy);
	}
}
