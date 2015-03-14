package com.rucker.vendingmachine.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.rucker.vendingmachine.components.CoinSlot;
import com.rucker.vendingmachine.currency.Coin;

public class CoinSlotTest {
	
	CoinSlot coinSlot;
	
	@Before
	public void setUp() {
		coinSlot = new CoinSlot();
	}

	@Test
	public void whenValidCoinsAreInsertedTheyAreAccepted() {
		assertEquals(Coin.NICKEL, coinSlot.receiveCoin(Coin.NICKEL.weight, Coin.NICKEL.diameter, Coin.NICKEL.thickness));
		assertEquals(Coin.DIME, coinSlot.receiveCoin(Coin.DIME.weight, Coin.DIME.diameter, Coin.DIME.thickness));
		assertEquals(Coin.QUARTER, coinSlot.receiveCoin(Coin.QUARTER.weight, Coin.QUARTER.diameter, Coin.QUARTER.thickness));
	}
	
	@Test
	public void whenInvalidCoinsAreInsertedTheyAreRejected() {
		assertEquals(Coin.METAL_SLUG, coinSlot.receiveCoin(Coin.NICKEL.weight, Coin.NICKEL.diameter, Coin.DIME.thickness));
		assertEquals(Coin.METAL_SLUG, coinSlot.receiveCoin(Coin.DIME.weight, Coin.NICKEL.diameter, Coin.DIME.thickness));
		assertEquals(Coin.METAL_SLUG, coinSlot.receiveCoin(Coin.NICKEL.weight, Coin.QUARTER.diameter, Coin.NICKEL.thickness));
	}
}
