package pl.edu.agh.mwo.java2;

import java.math.BigDecimal;

public class Product {
	private static final BigDecimal DEFAULT_TAX = BigDecimal.valueOf(0.23);

	private final String name;

	private final BigDecimal price;

	private final BigDecimal taxPercent;

	private Product(String name, BigDecimal price, BigDecimal tax) {
		this.name = name;
		this.price = price;
		this.taxPercent = tax;
	}

	public String getName() {
		return null;
	}

	public BigDecimal getPrice() {
		return null;
	}

	public BigDecimal getTaxPercent() {
		return null;
	}

	public BigDecimal getPriceWithTax() {
		return null;
	}

	public static Product create(final String name, final BigDecimal price, final BigDecimal tax) {
		return new Product(name, price, tax);
	}

	public static Product create(final String name, final BigDecimal price) {
		return create(name, price, DEFAULT_TAX);
	}
}
