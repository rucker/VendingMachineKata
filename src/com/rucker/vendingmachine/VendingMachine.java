package com.rucker.vendingmachine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;

import com.rucker.vendingmachine.components.CoinReturn;
import com.rucker.vendingmachine.components.CoinSlot;
import com.rucker.vendingmachine.components.Display;
import com.rucker.vendingmachine.components.KeyCodes;
import com.rucker.vendingmachine.currency.Coin;
import com.rucker.vendingmachine.product.Product;

public class VendingMachine {
	
	private Display display = new Display();
	private LinkedList<Coin> receivedCoins = new LinkedList<Coin>();
	private CoinSlot coinSlot = new CoinSlot();
	private CoinReturn coinReturn = new CoinReturn();
	private static HashMap<KeyCodes, Product> productCodes;
	private static HashMap<Product, Integer> inventory;
	static {
		productCodes = new HashMap<KeyCodes, Product>();
		productCodes.put(KeyCodes.KEY_ONE, Product.COLA);
		productCodes.put(KeyCodes.KEY_TWO, Product.CHIPS);
		productCodes.put(KeyCodes.KEY_THREE, Product.CANDY);
	}
	
	private boolean displayCheckedOnce = false;

	public VendingMachine() {
		inventory = new HashMap<Product, Integer> ();
		inventory.put(Product.CANDY, 5);
		inventory.put(Product.CHIPS, 5);
		inventory.put(Product.COLA, 5);
		inventory.put(Product.NONE, 0);
	}
	
	public String getDisplayMessage() {
		if (Display.THANK_YOU.equals(display.getMessage()) && displayCheckedOnce) {
			display.displayInsertCoin();
		}
		else if (display.getMessage().contains(Display.PRICE) && displayCheckedOnce) {
			if (getTotalMoneyReceived().compareTo(new BigDecimal(0).setScale(2)) > 0) {
				display.setDisplayTotal(getTotalMoneyReceived().toString());
			}
			else {
				display.displayInsertCoin();
			}
		}
		if (!displayCheckedOnce) {
			displayCheckedOnce = true;
		}
		return display.getMessage();
	}

	public Coin receiveCoin(double weight, double diameter, double thickness) {
		Coin receivedCoin = coinSlot.receiveCoin(weight, diameter, thickness);
		if (Coin.METAL_SLUG.equals(receivedCoin)) {
			coinReturn.returnCoin(receivedCoin);
		}
		else {
			receivedCoins.add(receivedCoin);
			display.setDisplayTotal(getTotalMoneyReceived().toString());
		}
		return receivedCoin;
	}

	public BigDecimal getTotalMoneyReceived() {
		BigDecimal totalMoneyReceived = new BigDecimal(0).setScale(2);
		for (Coin coin : receivedCoins) {
			totalMoneyReceived = totalMoneyReceived.add(coin.value);
		}
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
		boolean enoughMoneyReceived = getTotalMoneyReceived().compareTo(product.price) >= 0;
		if (!enoughMoneyReceived) {
			display.displayPriceOfProduct(product);
		}
		return enoughMoneyReceived;
	}

	public int getQuantityAvailable(Product product) {
		return inventory.get(product);
	}

	public void dispenseProduct(Product product) {
		inventory.put(product, inventory.get(product) - 1);
		dispenseChange();
		display.displayThankYou();
	}
	
	private void dispenseChange() {
		// FIXME implement change calculation
		receivedCoins.removeAll(receivedCoins);
	}

	public LinkedList<Coin> getCoinsInCoinReturn() {
		return coinReturn.getReturnedCoins();
	}

	public void returnCoins() {
		for (Coin coin : receivedCoins) {
			coinReturn.returnCoin(coin);
		}
		receivedCoins.removeAll(receivedCoins);
		display.displayInsertCoin();
	}
}
