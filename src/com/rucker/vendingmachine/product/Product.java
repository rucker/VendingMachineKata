package com.rucker.vendingmachine.product;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum Product {
	COLA(new BigDecimal(1.00).setScale(2, RoundingMode.HALF_UP)),
	CHIPS(new BigDecimal(0.50).setScale(2, RoundingMode.HALF_UP)),
	CANDY(new BigDecimal(0.65).setScale(2, RoundingMode.HALF_UP)),
	NONE(new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP));
	
	public final BigDecimal price;
	
	private Product(BigDecimal price){
		this.price = price.setScale(2, RoundingMode.HALF_UP);
	}
}