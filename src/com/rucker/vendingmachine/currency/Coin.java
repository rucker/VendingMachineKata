package com.rucker.vendingmachine.currency;

public enum Coin {
	NICKEL(5.0, 21.21, 1.95),
	DIME(2.268, 17.91, 1.35),
	QUARTER(5.670, 24.26, 1.75),
	METAL_SLUG(-1, -1, -1);
	
	//Measured in grams.
	public final double weight;
	//Measured in millimeters.
	public final double diameter;
	//Measured in millimeters.
	public final double thickness;

	Coin(double weight, double diameter, double thickness) {
		this.weight = weight;
		this.diameter = diameter;
		this.thickness = thickness;
	}
}
