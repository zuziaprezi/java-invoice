package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import pl.edu.agh.mwo.invoice.product.Product;

public class ProductTest {
	private static final String SAMPLE_PRODUCT_NAME = "Product 1";

	@Test
	public void testProductNameIsCorrect() {
		Product product = new OtherProduct(SAMPLE_PRODUCT_NAME, new BigDecimal("100.0"));
		Assert.assertEquals(SAMPLE_PRODUCT_NAME, product.getName());
	}

	@Test
	public void testProductPriceAndTaxWithDefaultTax() {
		Product product = new OtherProduct(SAMPLE_PRODUCT_NAME, new BigDecimal("100.0"));
		assertBigDecimalsAreEqual("100.0", product.getPrice());
		assertBigDecimalsAreEqual("0.23", product.getTaxPercent());
	}

	@Test
	public void testProductPriceAndTaxWithDairyProduct() {
		Product product = new DairyProduct(SAMPLE_PRODUCT_NAME, new BigDecimal("100.0"));
		assertBigDecimalsAreEqual("100.0", product.getPrice());
		assertBigDecimalsAreEqual("0.08", product.getTaxPercent());
	}

	@Test
	public void testPriceWithTax() {
		Product product = new DairyProduct(SAMPLE_PRODUCT_NAME, new BigDecimal("100.0"));
		assertBigDecimalsAreEqual("108.0", product.getPriceWithTax());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductWithNullName() {
		new OtherProduct(null, new BigDecimal("100.0"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductWithEmptyName() {
		new TaxFreeProduct("", new BigDecimal("100.0"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductWithNullPrice() {
		new DairyProduct(SAMPLE_PRODUCT_NAME, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductWithNegativePrice() {
		new TaxFreeProduct(SAMPLE_PRODUCT_NAME, new BigDecimal("-1.00"));
	}

	private void assertBigDecimalsAreEqual(String expected, BigDecimal actual) {
		Assert.assertEquals(new BigDecimal(expected).stripTrailingZeros(), actual.stripTrailingZeros());
	}
}
