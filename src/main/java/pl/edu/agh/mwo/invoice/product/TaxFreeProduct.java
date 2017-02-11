package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public class TaxFreeProduct extends Product {
	public TaxFreeProduct(String newName, BigDecimal price) {
		super(newName, price, BigDecimal.ZERO);
	}
}
