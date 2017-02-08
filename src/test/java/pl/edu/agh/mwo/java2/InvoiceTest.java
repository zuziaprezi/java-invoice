package pl.edu.agh.mwo.java2;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class InvoiceTest {

	private static final String _765_99 = "765.99";
	private static final String _739_97 = "739.97";
	private static final String _259_99 = "259.99";
	private static final String _12_3 = "12.3";
	private static final String _272_2 = "272.2";
	private static final String _10_0 = "10.0";
	private static final String _50_0 = "50.0";
	private static final String _199_99 = "199.99";
	private static final String PRODUCT_3 = "Product 3";
	private static final String PRODUCT_2 = "Product 2";
	private static final String PRODUCT_1 = "Product 1";

	@Test
	public void testEmptyInvoiceHasEmptySubtotal() {
		// given
		Invoice invoice = createEmptyInvoice();

		// when

		// then
		assertBigDecimalsAreEquals(BigDecimal.ZERO, invoice.getSubtotal());
	}

	@Test
	public void testEmptyInvoiceHasEmptyTaxAmount() {
		// given
		Invoice invoice = createEmptyInvoice();

		// when

		// then
		assertBigDecimalsAreEquals(BigDecimal.ZERO, invoice.getTax());
	}

	@Test
	public void testEmptyInvoiceHasEmptyTotal() {
		// given
		Invoice invoice = createEmptyInvoice();

		// when

		// then
		assertBigDecimalsAreEquals(BigDecimal.ZERO, invoice.getTotal());
	}

	@Test
	public void testInvoiceHasTheSameSubtotalAndTotalIfTaxIsZero() {
		// given
		Invoice invoice = createEmptyInvoice();

		// when
		invoice.addProduct(createProductWithZeroTax(), 1);

		// then
		assertBigDecimalsAreEquals(invoice.getSubtotal(), invoice.getTotal());
	}

	@Test
	public void testInvoiceHasProperSubtotalForManyProduct() {
		// given
		Invoice invoice = createEmptyInvoice();

		// when
		invoice.addProduct(createProductWithZeroTax(), 1);
		invoice.addProduct(createProductWithDefaultTax(), 1);
		invoice.addProduct(createProductWithCustomTax(), 1);

		// then
		assertBigDecimalsAreEquals(_259_99, invoice.getSubtotal());
	}

	@Test
	public void testInvoiceHasProperTaxValueForManyProduct() {
		// given
		Invoice invoice = createEmptyInvoice();

		// when
		invoice.addProduct(createProductWithZeroTax(), 1);
		invoice.addProduct(createProductWithDefaultTax(), 1);
		invoice.addProduct(createProductWithCustomTax(), 1);

		// then
		assertBigDecimalsAreEquals(_12_3, invoice.getTax());
	}

	@Test
	public void testInvoiceHasProperTotalValueForManyProduct() {
		// given
		Invoice invoice = createEmptyInvoice();

		// when
		invoice.addProduct(createProductWithZeroTax());
		invoice.addProduct(createProductWithDefaultTax());
		invoice.addProduct(createProductWithCustomTax());

		// then
		assertBigDecimalsAreEquals(_272_2, invoice.getTotal());
	}

	@Test
	public void testInvoiceHasPropoerSubtotalWithQuantityMoreThanOne() {
		// given
		Invoice invoice = createEmptyInvoice();

		// when
		invoice.addProduct(createProductWithZeroTax(), 3); // Subtotal: 599.97
		invoice.addProduct(createProductWithDefaultTax(), 2); // Subtotal:
																// 100.00
		invoice.addProduct(createProductWithCustomTax(), 4); // Subtotal: 40.00

		// then
		assertBigDecimalsAreEquals(_739_97, invoice.getSubtotal());
	}

	@Test
	public void testInvoiceHasPropoerTotalWithQuantityMoreThanOne() {
		// given
		Invoice invoice = createEmptyInvoice();

		// when
		invoice.addProduct(createProductWithZeroTax(), 3); // Total: 599.97
		invoice.addProduct(createProductWithDefaultTax(), 2); // Total: 123.00
		invoice.addProduct(createProductWithCustomTax(), 4); // Total: 43.2

		// then
		assertBigDecimalsAreEquals(_765_99, invoice.getTotal());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvoiceWithZeroQuantity() {
		// given
		Invoice invoice = createEmptyInvoice();

		// when
		invoice.addProduct(createProductWithZeroTax(), 0);

		// then
		// IllegalArgumentException should be thrown
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvoiceWithNegativeQuantity() {
		// given
		Invoice invoice = createEmptyInvoice();

		// when
		invoice.addProduct(createProductWithZeroTax(), -1);

		// then
		// IllegalArgumentException should be thrown
	}

	private Invoice createEmptyInvoice() {
		return Invoice.create();
	}

	private Product createProductWithZeroTax() {
		return Product.create(PRODUCT_1, new BigDecimal(_199_99), BigDecimal.ZERO);
	}

	private Product createProductWithDefaultTax() {
		return Product.create(PRODUCT_2, new BigDecimal(_50_0));
	}

	private Product createProductWithCustomTax() {
		return Product.create(PRODUCT_3, new BigDecimal(_10_0), BigDecimal.valueOf(0.08));
	}
	
	private void assertBigDecimalsAreEquals(String expected, BigDecimal actual) {
		assertEquals(0, new BigDecimal(expected).compareTo(actual));
	}
	
	private void assertBigDecimalsAreEquals(BigDecimal expected, BigDecimal actual) {
		assertEquals(0, expected.compareTo(actual));
	}

}
