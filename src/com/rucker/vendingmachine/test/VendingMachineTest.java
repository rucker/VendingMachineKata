package com.rucker.vendingmachine.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;

import com.rucker.vendingmachine.VendingMachine;
import com.rucker.vendingmachine.components.Display;
import com.rucker.vendingmachine.components.KeyCodes;
import com.rucker.vendingmachine.currency.Coin;
import com.rucker.vendingmachine.product.Product;

public class VendingMachineTest {
	
	private VendingMachine vendingMachine;
	
	@Before
	public void setUp() {
		vendingMachine = new VendingMachine();
	}
	
	private void insertTwoQuarters() {
		vendingMachine.receiveCoin(Coin.QUARTER.weight, Coin.QUARTER.diameter, Coin.QUARTER.thickness);
		vendingMachine.receiveCoin(Coin.QUARTER.weight, Coin.QUARTER.diameter, Coin.QUARTER.thickness);
	}
	
	
	@Test
	public void whenValidCoinsAreInsertedTheyAreAccepted() {
		assertEquals(Coin.NICKEL, vendingMachine.receiveCoin(Coin.NICKEL.weight, Coin.NICKEL.diameter, Coin.NICKEL.thickness));
		assertEquals(Coin.DIME, vendingMachine.receiveCoin(Coin.DIME.weight, Coin.DIME.diameter, Coin.DIME.thickness));
		assertEquals(Coin.QUARTER, vendingMachine.receiveCoin(Coin.QUARTER.weight, Coin.QUARTER.diameter, Coin.QUARTER.thickness));
	}
	
	@Test
	public void whenInvalidCoinsAreInsertedTheyAreRejected() {
		assertEquals(Coin.METAL_SLUG, vendingMachine.receiveCoin(Coin.NICKEL.weight, Coin.NICKEL.diameter, Coin.DIME.thickness));
		assertEquals(Coin.METAL_SLUG, vendingMachine.receiveCoin(Coin.DIME.weight, Coin.NICKEL.diameter, Coin.DIME.thickness));
		assertEquals(Coin.METAL_SLUG, vendingMachine.receiveCoin(Coin.NICKEL.weight, Coin.QUARTER.diameter, Coin.NICKEL.thickness));
		assertEquals(3, vendingMachine.getCoinsInCoinReturn().size());
		for (Coin coin : vendingMachine.getCoinsInCoinReturn()) {
			assertEquals(Coin.METAL_SLUG, coin);
		}
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
	
	@Test
	public void whenCoinsAreReceivedTheDisplayShowsTheCurrentTotalMoneyReceived() {
		vendingMachine.receiveCoin(Coin.NICKEL.weight, Coin.NICKEL.diameter, Coin.NICKEL.thickness);
		assertEquals("$0.05", vendingMachine.getDisplayMessage());
		vendingMachine.receiveCoin(Coin.DIME.weight, Coin.DIME.diameter, Coin.DIME.thickness);
		assertEquals("$0.15", vendingMachine.getDisplayMessage());
		vendingMachine.receiveCoin(Coin.QUARTER.weight, Coin.QUARTER.diameter, Coin.QUARTER.thickness);
		assertEquals("$0.40", vendingMachine.getDisplayMessage());
	}
	
	@Test
	public void whenAKeyCodeBelongingToAProductIsSelectedTheVendingMachineReturnsThatProduct() {
		assertEquals(Product.COLA, vendingMachine.selectProductByKeyCode(KeyCodes.KEY_ONE));
		assertEquals(Product.CHIPS, vendingMachine.selectProductByKeyCode(KeyCodes.KEY_TWO));
		assertEquals(Product.CANDY, vendingMachine.selectProductByKeyCode(KeyCodes.KEY_THREE));
	}
	
	@Test
	public void whenAKeyCodeBelongingToNoProductIsSelectedTheVendingMachineReturnsNoneProduct() {
		assertEquals(Product.NONE, vendingMachine.selectProductByKeyCode(KeyCodes.KEY_FOUR));
		assertEquals(Product.NONE, vendingMachine.selectProductByKeyCode(KeyCodes.KEY_FIVE));
		assertEquals(Product.NONE, vendingMachine.selectProductByKeyCode(KeyCodes.KEY_SIX));
		assertEquals(Product.NONE, vendingMachine.selectProductByKeyCode(KeyCodes.KEY_SEVEN));
		assertEquals(Product.NONE, vendingMachine.selectProductByKeyCode(KeyCodes.KEY_EIGHT));
		assertEquals(Product.NONE, vendingMachine.selectProductByKeyCode(KeyCodes.KEY_NINE));
		assertEquals(Product.NONE, vendingMachine.selectProductByKeyCode(KeyCodes.KEY_ZERO));
		assertEquals(Product.NONE, vendingMachine.selectProductByKeyCode(KeyCodes.KEY_INVALID));
	}
	
	@Test
	public void whenAProductIsChosenTheVendingMachineVerifiesWhetherEnoughMoneyHasBeenReceived() {
		insertTwoQuarters();
		assertEquals(true, vendingMachine.hasEnoughMoneyBeenReceivedForProduct(Product.CHIPS));
		assertEquals(false, vendingMachine.hasEnoughMoneyBeenReceivedForProduct(Product.CANDY));
	}
	
	@Test
	public void productQuantiesAreInitializedAsExpected() {
		assertEquals(5, vendingMachine.getQuantityAvailable(Product.CANDY));
		assertEquals(5, vendingMachine.getQuantityAvailable(Product.CHIPS));
		assertEquals(5, vendingMachine.getQuantityAvailable(Product.COLA));
		assertEquals(0, vendingMachine.getQuantityAvailable(Product.NONE));
	}
	
	@Test
	public void whenAProductIsDispensedItIsRemovedFromTheInventoryCount() {
		insertTwoQuarters();
		vendingMachine.dispenseProduct(Product.CHIPS);
		assertEquals(4, vendingMachine.getQuantityAvailable(Product.CHIPS));
	}
	
	@Test
	public void whenAProductIsDispensedDisplayReadsThankYou() {
		vendingMachine.dispenseProduct(Product.CHIPS);
		assertEquals(Display.THANK_YOU, vendingMachine.getDisplayMessage());
	}
	
	@Test
	public void whenAProductIsDispensedAndDisplayCheckedASecondTimeItReadsInsertCoin() {
		vendingMachine.dispenseProduct(Product.CHIPS);
		vendingMachine.getDisplayMessage();
		assertEquals(Display.INSERT_COIN, vendingMachine.getDisplayMessage());
	}
	
	@Test
	public void whenProductIsDispensedTotalMoneyReceivedIsSetToZero() {
		insertTwoQuarters();
		vendingMachine.dispenseProduct(Product.CHIPS);
		assertEquals(new BigDecimal(0).setScale(2), vendingMachine.getTotalMoneyReceived());
	}
	
	@Test
	public void whenAProductIsSelectedButNotEnoughMoneyHasBeenReceivedDisplayWillShowProductPrice() {
		insertTwoQuarters();
		vendingMachine.hasEnoughMoneyBeenReceivedForProduct(Product.CANDY);
		assertEquals("PRICE $0.65", vendingMachine.getDisplayMessage());
	}
	
	@Test
	public void whenAProductIsSelectedButNotEnoughMoneyHasBeenReceivedAndDisplayIsCheckedMoreThanOnceDisplayWillShowCurrentAmountReceived() {
		insertTwoQuarters();
		vendingMachine.hasEnoughMoneyBeenReceivedForProduct(Product.CANDY);
		vendingMachine.getDisplayMessage();
		assertEquals("$0.50", vendingMachine.getDisplayMessage());
	}
	
	@Test
	public void whenAProductIsSelectedButNoMoneyHasBeenReceivedAndDisplayIsCheckedMoreThanOnceDisplayWillReadInsertCoin() {
		vendingMachine.hasEnoughMoneyBeenReceivedForProduct(Product.CANDY);
		vendingMachine.getDisplayMessage();
		assertEquals(Display.INSERT_COIN, vendingMachine.getDisplayMessage());
	}
	
	@Test
	public void whenCustomerWantsCoinsReturnedPlaceReceivedCoinsInCoinReturn() {
		insertTwoQuarters();
		vendingMachine.returnCoins();
		assertEquals(2, vendingMachine.getCoinsInCoinReturn().size());
	}
	
	@Test
	public void whenAllCoinsAreReturnedToCustomerTheyArePlacedInCoinReturn() {
		insertTwoQuarters();
		vendingMachine.returnCoins();
		assertEquals(2, vendingMachine.getCoinsInCoinReturn().size());
		for (Coin coin : vendingMachine.getCoinsInCoinReturn()) {
			assertEquals(Coin.QUARTER, coin);
		}
	}
	
	@Test
	public void whenAllCoinsAreReturnedToCustomerTotalMoneyReceivedIsZero() {
		insertTwoQuarters();
		vendingMachine.returnCoins();
		assertEquals(new BigDecimal(0).setScale(2), vendingMachine.getTotalMoneyReceived());
	}
	
	@Test
	public void whenAllCoinsAreReturnedToCustomerDisplayReadsInsertCoin() {
		insertTwoQuarters();
		vendingMachine.returnCoins();
		assertEquals(Display.INSERT_COIN, vendingMachine.getDisplayMessage());
	}
	
	@Test
	public void when75CentsIsInsertedAndAProductCosting65CentsIsPurchased10CentsIsPlacedInCoinReturn () {
		insertTwoQuarters();
		vendingMachine.receiveCoin(Coin.QUARTER.weight, Coin.QUARTER.diameter, Coin.QUARTER.thickness);
		vendingMachine.dispenseProduct(Product.CANDY);
		assertEquals(0, new BigDecimal(.1).setScale(2, RoundingMode.HALF_UP).compareTo(calculateChangeReceived()));
	}
	
	@Test
	public void when80CentsIsInsertedAndAProductCosting50CentsIsPurchased30CentsIsPlacedInCoinReturn () {
		insertTwoQuarters();
		vendingMachine.receiveCoin(Coin.QUARTER.weight, Coin.QUARTER.diameter, Coin.QUARTER.thickness);
		vendingMachine.receiveCoin(Coin.NICKEL.weight, Coin.NICKEL.diameter, Coin.NICKEL.thickness);
		vendingMachine.dispenseProduct(Product.CHIPS);
		assertEquals(0, new BigDecimal(.3).setScale(2, RoundingMode.HALF_UP).compareTo(calculateChangeReceived()));
	}
	
	@Test
	public void when65CentsIsInsertedAndAProductCosting50CentsIsPurchased15CentsIsPlacedInCoinReturn () {
		insertTwoQuarters();
		vendingMachine.receiveCoin(Coin.DIME.weight, Coin.DIME.diameter, Coin.DIME.thickness);
		vendingMachine.receiveCoin(Coin.NICKEL.weight, Coin.NICKEL.diameter, Coin.NICKEL.thickness);
		vendingMachine.dispenseProduct(Product.CHIPS);
		assertEquals(0, new BigDecimal(.15).setScale(2, RoundingMode.HALF_UP).compareTo(calculateChangeReceived()));
	}
	
	@Test
	public void whenOneDollarAndTwentyFiveCentsIsInsertedAndAProductCostingOneDollarIsPurchased25CentsIsPlacedInCoinReturn () {
		insertTwoQuarters();
		insertTwoQuarters();
		vendingMachine.receiveCoin(Coin.QUARTER.weight, Coin.QUARTER.diameter, Coin.QUARTER.thickness);
		vendingMachine.dispenseProduct(Product.COLA);
		assertEquals(0, new BigDecimal(.25).setScale(2, RoundingMode.HALF_UP).compareTo(calculateChangeReceived()));
	}
	
	private BigDecimal calculateChangeReceived() {
		BigDecimal changeReceived = BigDecimal.ZERO;
		for (Coin coin : vendingMachine.getCoinsInCoinReturn()) {
			changeReceived = changeReceived.add(coin.value);
		}
		return changeReceived;
	}
}
