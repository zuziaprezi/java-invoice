package pl.edu.agh.mwo.java2;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class ProductTest {

	private static final String _1_00 = "-1.00";
	private static final String _108_0 = "108.0";
	private static final String _0_08 = "0.08";
	private static final String _0_23 = "0.23";
	private static final String _100_0 = "100.0";
	private static final String PRODUCT_1 = "Product 1";

	@Test
	public void testProductNameIsCorrect() {
		// given
		Product product = Product.create(PRODUCT_1, new BigDecimal(_100_0));

		// when
		// then
		assertEquals(PRODUCT_1, product.getName());

	}

	
	@Test
	public void testProductPriceAndTaxWithDefaultTax() {
		// given
		Product product = Product.create(PRODUCT_1, new BigDecimal(_100_0));

		// when
		// then
		assertBigDecimalsAreEquals(_100_0, product.getPrice());
		assertBigDecimalsAreEquals(_0_23, product.getTaxPercent());

	}

	@Test
	public void testProductPriceAndTaxWithCustomTax() {
		// given
		Product product = Product.create(PRODUCT_1, new BigDecimal(_100_0), new BigDecimal(_0_08));

		// when
		// then	
		assertBigDecimalsAreEquals(_100_0, product.getPrice());
		assertBigDecimalsAreEquals(_0_08, product.getTaxPercent());
	}

	@Test
	public void testPriceWithTax() {
		// given
		Product product = Product.create(PRODUCT_1, new BigDecimal(_100_0), new BigDecimal(_0_08));

		// when
		// then
		assertBigDecimalsAreEquals(_108_0, product.getPriceWithTax());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductWithNullName() {
		// given
		Product.create(null, new BigDecimal(_100_0), new BigDecimal(_0_08));

		// when
		// then
		// IllegalArgumentException is thrown
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductWithEmptyName() {
		// given
		Product.create("", new BigDecimal(_100_0), new BigDecimal(_0_08));

		// when
		// then
		// IllegalArgumentException is thrown
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductWithNullPrice() {
		// given
		Product.create(PRODUCT_1, null, new BigDecimal(_0_08));

		// when
		// then
		// IllegalArgumentException is thrown
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductWithNegativePrice() {
		// given
		Product.create(PRODUCT_1, new BigDecimal(_1_00), new BigDecimal(_0_08));

		// when
		// then
		// IllegalArgumentException is thrown
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductWithNullTax() {
		// given
		Product.create(PRODUCT_1, new BigDecimal(_100_0), null);

		// when
		// then
		// IllegalArgumentException is thrown
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductWithNegativeTax() {
		// given
		Product.create(PRODUCT_1, new BigDecimal(_100_0), new BigDecimal(_1_00));

		// when
		// then
		// IllegalArgumentException is thrown
	}

	private void assertBigDecimalsAreEquals(String expected, BigDecimal actual) {
		assertEquals(0, new BigDecimal(expected).compareTo(actual));
	}

}
