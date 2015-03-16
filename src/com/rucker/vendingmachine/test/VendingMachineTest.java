package com.rucker.vendingmachine.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;

import com.rucker.vendingmachine.VendingMachine;
import com.rucker.vendingmachine.components.Display;
import com.rucker.vendingmachine.currency.Coin;

public class VendingMachineTest {
	
	private VendingMachine vendingMachine;
	
	@Before
	public void setUp() {
		vendingMachine = new VendingMachine();
	}

	@Test
	public void whenNoCoinsHaveBeenInsertedDisplayReadsInsertCoin() {
		assertEquals(Display.INSERT_COIN, vendingMachine.getDisplayMessage());
	}

	@Test
	public void whenNoCoinsHaveBeenInsertedTotalMoneyReceivedIsZero() {
		assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), vendingMachine.getTotalMoneyReceived());
	}
	
	@Test
	public void whenValidCoinsAreReceivedTheTotalMoneyInsertedIsUpdated() {
		vendingMachine.receiveCoin(Coin.NICKEL.weight, Coin.NICKEL.diameter, Coin.NICKEL.thickness);
		assertEquals(Coin.NICKEL.value, vendingMachine.getTotalMoneyReceived());
		vendingMachine.receiveCoin(Coin.DIME.weight, Coin.DIME.diameter, Coin.DIME.thickness);
		assertEquals(new BigDecimal(.15).setScale(2, RoundingMode.HALF_UP), vendingMachine.getTotalMoneyReceived());
		vendingMachine.receiveCoin(Coin.QUARTER.weight, Coin.QUARTER.diameter, Coin.QUARTER.thickness);
		assertEquals(new BigDecimal(.40).setScale(2, RoundingMode.HALF_UP), vendingMachine.getTotalMoneyReceived());
	}
}
