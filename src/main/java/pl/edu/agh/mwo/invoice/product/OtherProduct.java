package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public class OtherProduct extends Product {
	public OtherProduct(String newName, BigDecimal price) {
		super(newName, price, new BigDecimal("0.23"));
	}
}
