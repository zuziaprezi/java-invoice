package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;

public class Invoice {
	public void addProduct(Product product) {
		// TODO: implement
	}

	public void addProduct(Product product, Integer quantity) {
		if (quantity <= 0) {
			throw new IllegalArgumentException();
		}
		// TODO: implement
	}

	public BigDecimal getSubtotal() {
		return null;
	}

	public BigDecimal getTax() {
		return null;
	}

	public BigDecimal getTotal() {
		return null;
	}
}
