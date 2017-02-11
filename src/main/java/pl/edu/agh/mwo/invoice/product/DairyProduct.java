package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public class DairyProduct extends Product {
	public DairyProduct(String newName, BigDecimal price) {
		super(newName, price, new BigDecimal("0.08"));
	}
}
