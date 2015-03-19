package com.rucker.vendingmachine.components;

import java.util.LinkedList;

import com.rucker.vendingmachine.currency.Coin;

public class CoinReturn {
	
	private LinkedList<Coin> returnedCoins = new LinkedList<Coin>();
	
	public LinkedList<Coin> getReturnedCoins() {
		return returnedCoins;
	}

	public void returnCoin(Coin coin) {
		returnedCoins.add(coin);
	}

}
