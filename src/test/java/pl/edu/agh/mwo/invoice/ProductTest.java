package pl.edu.agh.mwo.invoice;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import pl.edu.agh.mwo.invoice.Product;

public class ProductTest {
	private static final String PRODUCT_1 = "Product 1";

	@Test
	public void testProductNameIsCorrect() {
		Product product = Product.create(PRODUCT_1, new BigDecimal("100.0"));
		assertEquals(PRODUCT_1, product.getName());
	}

	@Test
	public void testProductPriceAndTaxWithDefaultTax() {
		Product product = Product.create(PRODUCT_1, new BigDecimal("100.0"));
		assertBigDecimalsAreEquals("100.0", product.getPrice());
		assertBigDecimalsAreEquals("0.23", product.getTaxPercent());
	}

	@Test
	public void testProductPriceAndTaxWithCustomTax() {
		Product product = Product.create(PRODUCT_1, new BigDecimal("100.0"), new BigDecimal("0.08"));
		assertBigDecimalsAreEquals("100.0", product.getPrice());
		assertBigDecimalsAreEquals("0.08", product.getTaxPercent());
	}

	@Test
	public void testPriceWithTax() {
		Product product = Product.create(PRODUCT_1, new BigDecimal("100.0"), new BigDecimal("0.08"));
		assertBigDecimalsAreEquals("108.0", product.getPriceWithTax());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductWithNullName() {
		Product.create(null, new BigDecimal("100.0"), new BigDecimal("0.08"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductWithEmptyName() {
		Product.create("", new BigDecimal("100.0"), new BigDecimal("0.08"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductWithNullPrice() {
		Product.create(PRODUCT_1, null, new BigDecimal("0.08"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductWithNegativePrice() {
		Product.create(PRODUCT_1, new BigDecimal("-1.00"), new BigDecimal("0.08"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductWithNullTax() {
		Product.create(PRODUCT_1, new BigDecimal("100.0"), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductWithNegativeTax() {
		Product.create(PRODUCT_1, new BigDecimal("100.0"), new BigDecimal("-1.00"));
	}

	private void assertBigDecimalsAreEquals(String expected, BigDecimal actual) {
		assertEquals(new BigDecimal(expected).stripTrailingZeros(), actual.stripTrailingZeros());
	}
}
