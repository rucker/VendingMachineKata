package com.rucker.vendingmachine.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.rucker.vendingmachine.VendingMachine;
import com.rucker.vendingmachine.components.Display;

public class VendingMachineTest {

	@Test
	public void whenNoCoinsHaveBeenInsertedDisplayReadsInsertCoin() {
		VendingMachine vendingMachine = new VendingMachine();
		assertEquals(Display.INSERT_COIN, vendingMachine.getDisplayMessage());
	}

}
