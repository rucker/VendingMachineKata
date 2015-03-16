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
		vendingMachine.receiveCoin(Coin.NICKEL.weight, Coin.NICKEL.diameter, Coin.NICKEL.diameter);
		assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), vendingMachine.getTotalMoneyReceived());
	}
}
