package com.rucker.vendingmachine.components;

import com.rucker.vendingmachine.currency.Coin;

public class CoinSlot {

	/**
	 * 
	 * @return the coin recognized according to its weight/diameter/thickness
	 */
	public Coin receiveCoin(double weight, double diameter, double thickness) {
		if (weight == Coin.NICKEL.weight &&
				diameter == Coin.NICKEL.diameter &&
				thickness == Coin.NICKEL.thickness) {
				return Coin.NICKEL;
		}
		else if (weight == Coin.DIME.weight &&
				diameter == Coin.DIME.diameter &&
				thickness == Coin.DIME.thickness) {
				return Coin.DIME;
		}
		else if (weight == Coin.QUARTER.weight &&
				diameter == Coin.QUARTER.diameter &&
				thickness == Coin.QUARTER.thickness) {
				return Coin.QUARTER;
		}
		return Coin.METAL_SLUG;
	}

}
