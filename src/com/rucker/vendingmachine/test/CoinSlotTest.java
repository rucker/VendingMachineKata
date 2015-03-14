package com.rucker.vendingmachine.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.rucker.vendingmachine.components.CoinSlot;
import com.rucker.vendingmachine.currency.Coin;

public class CoinSlotTest {

	@Test
	public void whenValidCoinsAreInsertedTheyAreAccepted() {
		CoinSlot coinSlot = new CoinSlot();
		assertEquals(Coin.NICKEL, coinSlot.receiveCoin(Coin.NICKEL.weight, Coin.NICKEL.diameter, Coin.NICKEL.thickness));
		assertEquals(Coin.DIME, coinSlot.receiveCoin(Coin.DIME.weight, Coin.DIME.diameter, Coin.DIME.thickness));
		assertEquals(Coin.QUARTER, coinSlot.receiveCoin(Coin.QUARTER.weight, Coin.QUARTER.diameter, Coin.QUARTER.thickness));
	}
	
	@Test
	public void whenInvalidCoinsAreInsertedTheyAreRejected() {
		CoinSlot coinSlot = new CoinSlot();
		assertEquals(Coin.METAL_SLUG, coinSlot.receiveCoin(Coin.NICKEL.weight, Coin.NICKEL.diameter, Coin.DIME.thickness));
		assertEquals(Coin.METAL_SLUG, coinSlot.receiveCoin(Coin.DIME.weight, Coin.NICKEL.diameter, Coin.DIME.thickness));
		assertEquals(Coin.METAL_SLUG, coinSlot.receiveCoin(Coin.NICKEL.weight, Coin.QUARTER.diameter, Coin.NICKEL.thickness));
	}
}
