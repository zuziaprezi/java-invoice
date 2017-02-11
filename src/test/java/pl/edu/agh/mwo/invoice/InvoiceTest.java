package pl.edu.agh.mwo.invoice;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import pl.edu.agh.mwo.invoice.Invoice;
import pl.edu.agh.mwo.invoice.Product;

public class InvoiceTest {
	private static final String PRODUCT_1 = "Product 1";
	private static final String PRODUCT_2 = "Product 2";
	private static final String PRODUCT_3 = "Product 3";

	@Test
	public void testEmptyInvoiceHasEmptySubtotal() {
		Invoice invoice = createEmptyInvoice();
		assertBigDecimalsAreEquals(BigDecimal.ZERO, invoice.getSubtotal());
	}

	@Test
	public void testEmptyInvoiceHasEmptyTaxAmount() {
		Invoice invoice = createEmptyInvoice();
		assertBigDecimalsAreEquals(BigDecimal.ZERO, invoice.getTax());
	}

	@Test
	public void testEmptyInvoiceHasEmptyTotal() {
		Invoice invoice = createEmptyInvoice();
		assertBigDecimalsAreEquals(BigDecimal.ZERO, invoice.getTotal());
	}

	@Test
	public void testInvoiceHasTheSameSubtotalAndTotalIfTaxIsZero() {
		Invoice invoice = createEmptyInvoice();
		invoice.addProduct(createProductWithZeroTax(), 1);
		assertBigDecimalsAreEquals(invoice.getSubtotal(), invoice.getTotal());
	}

	@Test
	public void testInvoiceHasProperSubtotalForManyProduct() {
		Invoice invoice = createEmptyInvoice();
		invoice.addProduct(createProductWithZeroTax(), 1);
		invoice.addProduct(createProductWithDefaultTax(), 1);
		invoice.addProduct(createProductWithCustomTax(), 1);
		assertBigDecimalsAreEquals("259.99", invoice.getSubtotal());
	}

	@Test
	public void testInvoiceHasProperTaxValueForManyProduct() {
		Invoice invoice = createEmptyInvoice();
		invoice.addProduct(createProductWithZeroTax(), 1);
		invoice.addProduct(createProductWithDefaultTax(), 1);
		invoice.addProduct(createProductWithCustomTax(), 1);
		assertBigDecimalsAreEquals("12.3", invoice.getTax());
	}

	@Test
	public void testInvoiceHasProperTotalValueForManyProduct() {
		Invoice invoice = createEmptyInvoice();
		invoice.addProduct(createProductWithZeroTax());
		invoice.addProduct(createProductWithDefaultTax());
		invoice.addProduct(createProductWithCustomTax());
		assertBigDecimalsAreEquals("272.2", invoice.getTotal());
	}

	@Test
	public void testInvoiceHasPropoerSubtotalWithQuantityMoreThanOne() {
		Invoice invoice = createEmptyInvoice();
		invoice.addProduct(createProductWithZeroTax(), 3); // Subtotal: 599.97
		invoice.addProduct(createProductWithDefaultTax(), 2); // Subtotal:
																// 100.00
		invoice.addProduct(createProductWithCustomTax(), 4); // Subtotal: 40.00
		assertBigDecimalsAreEquals("739.97", invoice.getSubtotal());
	}

	@Test
	public void testInvoiceHasPropoerTotalWithQuantityMoreThanOne() {
		Invoice invoice = createEmptyInvoice();
		invoice.addProduct(createProductWithZeroTax(), 3); // Total: 599.97
		invoice.addProduct(createProductWithDefaultTax(), 2); // Total: 123.00
		invoice.addProduct(createProductWithCustomTax(), 4); // Total: 43.2
		assertBigDecimalsAreEquals("765.99", invoice.getTotal());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvoiceWithZeroQuantity() {
		Invoice invoice = createEmptyInvoice();
		invoice.addProduct(createProductWithZeroTax(), 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvoiceWithNegativeQuantity() {
		Invoice invoice = createEmptyInvoice();
		invoice.addProduct(createProductWithZeroTax(), -1);
	}

	private Invoice createEmptyInvoice() {
		return new Invoice();
	}

	private Product createProductWithZeroTax() {
		return Product.create(PRODUCT_1, new BigDecimal("199.99"), BigDecimal.ZERO);
	}

	private Product createProductWithDefaultTax() {
		return Product.create(PRODUCT_2, new BigDecimal("50.0"));
	}

	private Product createProductWithCustomTax() {
		return Product.create(PRODUCT_3, new BigDecimal("10.0"), BigDecimal.valueOf(0.08));
	}

	private void assertBigDecimalsAreEquals(String expected, BigDecimal actual) {
		assertEquals(0, new BigDecimal(expected).compareTo(actual));
	}

	private void assertBigDecimalsAreEquals(BigDecimal expected, BigDecimal actual) {
		assertEquals(expected.stripTrailingZeros(), actual.stripTrailingZeros());
	}

}
