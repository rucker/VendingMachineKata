package com.rucker.vendingmachine.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.rucker.vendingmachine.components.CoinSlot;
import com.rucker.vendingmachine.currency.Coin;

public class CoinSlotTest {

	@Test
	public void whenValidCoinsAreInsertedTheyAreAccepted() {
		CoinSlot coinSlot = new CoinSlot();
		assertEquals(Coin.NICKEL, coinSlot.receiveCoin(5.00, 21.21, 1.95));
		assertEquals(Coin.DIME, coinSlot.receiveCoin(2.268, 17.91, 1.35));
		assertEquals(Coin.QUARTER, coinSlot.receiveCoin(5.670, 24.26, 1.75));
	}

}
