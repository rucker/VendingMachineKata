package com.rucker.vendingmachine;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	private BigDecimal totalMoneyReceived;
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
		setTotalMoneyReceivedToZero();
		inventory = new HashMap<Product, Integer> ();
		inventory.put(Product.CANDY, 5);
		inventory.put(Product.CHIPS, 5);
		inventory.put(Product.COLA, 5);
		inventory.put(Product.NONE, 0);
	}
	
	private void setTotalMoneyReceivedToZero() {
		totalMoneyReceived = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
	}
	
	public String getDisplayMessage() {
		if (Display.THANK_YOU.equals(display.getMessage()) && displayCheckedOnce) {
			display.displayInsertCoin();
		}
		else if (display.getMessage().contains(Display.PRICE) && displayCheckedOnce) {
			if (totalMoneyReceived.compareTo(new BigDecimal(0).setScale(2)) > 0) {
				display.setDisplayTotal(totalMoneyReceived.toString());
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
			totalMoneyReceived = totalMoneyReceived.add(receivedCoin.value);
			receivedCoins.add(receivedCoin);
			display.setDisplayTotal(totalMoneyReceived.toString());
		}
		return receivedCoin;
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
		boolean enoughMoneyReceived = totalMoneyReceived.compareTo(product.price) >= 0;
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
		setTotalMoneyReceivedToZero();
		display.displayThankYou();
	}
	
	public LinkedList<Coin> getCoinsInCoinReturn() {
		return coinReturn.getReturnedCoins();
	}

	public void returnCoins() {
		for (Coin coin : receivedCoins) {
			coinReturn.returnCoin(coin);
		}
		receivedCoins.removeAll(receivedCoins);
		setTotalMoneyReceivedToZero();
	}
}
