package com.rucker.vendingmachine.currency;

import java.math.BigDecimal;

public enum Coin {
	NICKEL(5.0, 21.21, 1.95, new BigDecimal(.05)),
	DIME(2.268, 17.91, 1.35, new BigDecimal(.10)),
	QUARTER(5.670, 24.26, 1.75, new BigDecimal(.25)),
	METAL_SLUG(-1, -1, -1, new BigDecimal(.00));
	
	//Measured in grams.
	public final double weight;
	//Measured in millimeters.
	public final double diameter;
	//Measured in millimeters.
	public final double thickness;

	Coin(double weight, double diameter, double thickness, BigDecimal value) {
		this.weight = weight;
		this.diameter = diameter;
		this.thickness = thickness;
	}
}
