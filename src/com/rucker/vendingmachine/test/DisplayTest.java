package com.rucker.vendingmachine.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.rucker.vendingmachine.components.Display;

public class DisplayTest {
	
	Display display;
	
	@Before
	public void setUp() {
		display = new Display();
	}

	@Test
	public void whenNoCoinsHaveBeenInsertedDisplaySaysInsertCoin() {
		assertEquals(Display.INSERT_COIN, display.getMessage());
	}

}
