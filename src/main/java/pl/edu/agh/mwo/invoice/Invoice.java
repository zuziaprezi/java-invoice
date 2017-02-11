package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.Collection;

public class Invoice {
	private Collection<Product> products;
	
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
		return new BigDecimal(0);
	}

	public BigDecimal getTax() {
		return new BigDecimal(0);
	}

	public BigDecimal getTotal() {
		return new BigDecimal(0);
	}
}
