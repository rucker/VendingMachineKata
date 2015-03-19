# VendingMachineKata
This is an implementation of [Guy Royse's Vending Machine kata](https://github.com/guyroyse/vending-machine-kata). It was completed using TDD (red/green/refactor rhythm).

## OO Design
I designed the Vending Machine to be as faithful a representation of its real-world counterpart as was feasible. Various objects represent distinct parts of the machine, with the exception of the keypad: since it maintains no state and contains no logic, a KeyPad class was not needed.

I implemented coin validation via measurement of known constants (the known weight, diameter, and thicknesses of coins as defined by the US Mint).

As there are only three products in the machine, I chose to implement a numeric-only keypad and assign each item a one-digit code. Altering this to accommodate multi-digit alphanumeric codes would not be difficult.

## Unit Tests
Unit tests are written against the VendingMachine class. With the exception of coin validation (CoinSlot class), the VendingMachine contains all logic (other classes maintain only their state). I test the CoinSlot class by interacting with the VendingMachine class because the CoinSlot class needs to interact with another machine component (the coin return) when rejecting invalid coins.

## Features Not Implemented
Features not implemented include the Make Change, Sold Out, and Exact Change Only use-cases.
