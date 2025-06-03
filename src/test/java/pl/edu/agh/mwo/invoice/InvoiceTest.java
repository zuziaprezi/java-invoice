package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.*;

import org.hamcrest.Matchers;
import org.hamcrest.number.BigDecimalCloseTo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.mwo.invoice.product.DairyProduct;
import pl.edu.agh.mwo.invoice.product.OtherProduct;
import pl.edu.agh.mwo.invoice.product.Product;
import pl.edu.agh.mwo.invoice.product.TaxFreeProduct;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InvoiceTest {
    private Invoice invoice;

    @Before
    public void createEmptyInvoiceForTheTest() {
        invoice = new Invoice();
    }

    @Test
    public void testEmptyInvoiceHasEmptySubtotal() {
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getSubtotal()));
    }

    @Test
    public void testEmptyInvoiceHasEmptyTaxAmount() {
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getTax()));
    }

    @Test
    public void testEmptyInvoiceHasEmptyTotal() {
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getTotal()));
    }

    @Test
    public void testInvoiceSubtotalWithTwoDifferentProducts() {
        Product onions = new TaxFreeProduct("Warzywa", new BigDecimal("10"));
        Product apples = new TaxFreeProduct("Owoce", new BigDecimal("10"));
        invoice.addProduct(onions);
        invoice.addProduct(apples);
        Assert.assertThat(new BigDecimal("20"), Matchers.comparesEqualTo(invoice.getSubtotal()));
    }

    @Test
    public void testInvoiceSubtotalWithManySameProducts() {
        Product onions = new TaxFreeProduct("Warzywa", BigDecimal.valueOf(10));
        invoice.addProduct(onions, 100);
        Assert.assertThat(new BigDecimal("1000"), Matchers.comparesEqualTo(invoice.getSubtotal()));
    }

    @Test
    public void testInvoiceHasTheSameSubtotalAndTotalIfTaxIsZero() {
        Product taxFreeProduct = new TaxFreeProduct("Warzywa", new BigDecimal("199.99"));
        invoice.addProduct(taxFreeProduct);
        Assert.assertThat(invoice.getTotal(), Matchers.comparesEqualTo(invoice.getSubtotal()));
    }

    @Test
    public void testInvoiceHasProperSubtotalForManyProducts() {
        invoice.addProduct(new TaxFreeProduct("Owoce", new BigDecimal("200")));
        invoice.addProduct(new DairyProduct("Maslanka", new BigDecimal("100")));
        invoice.addProduct(new OtherProduct("Wino", new BigDecimal("10")));
        Assert.assertThat(new BigDecimal("310"), Matchers.comparesEqualTo(invoice.getSubtotal()));
    }

    @Test
    public void testInvoiceHasProperTaxValueForManyProduct() {
        // tax: 0
        invoice.addProduct(new TaxFreeProduct("Pampersy", new BigDecimal("200")));
        // tax: 8
        invoice.addProduct(new DairyProduct("Kefir", new BigDecimal("100")));
        // tax: 2.30
        invoice.addProduct(new OtherProduct("Piwko", new BigDecimal("10")));
        Assert.assertThat(new BigDecimal("10.30"), Matchers.comparesEqualTo(invoice.getTax()));
    }

    @Test
    public void testInvoiceHasProperTotalValueForManyProduct() {
        // price with tax: 200
        invoice.addProduct(new TaxFreeProduct("Maskotki", new BigDecimal("200")));
        // price with tax: 108
        invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("100")));
        // price with tax: 12.30
        invoice.addProduct(new OtherProduct("Chipsy", new BigDecimal("10")));
        Assert.assertThat(new BigDecimal("320.30"), Matchers.comparesEqualTo(invoice.getTotal()));
    }

    @Test
    public void testInvoiceHasPropoerSubtotalWithQuantityMoreThanOne() {
        // 2x kubek - price: 10
        invoice.addProduct(new TaxFreeProduct("Kubek", new BigDecimal("5")), 2);
        // 3x kozi serek - price: 30
        invoice.addProduct(new DairyProduct("Kozi Serek", new BigDecimal("10")), 3);
        // 1000x pinezka - price: 10
        invoice.addProduct(new OtherProduct("Pinezka", new BigDecimal("0.01")), 1000);
        Assert.assertThat(new BigDecimal("50"), Matchers.comparesEqualTo(invoice.getSubtotal()));
    }

    @Test
    public void testInvoiceHasPropoerTotalWithQuantityMoreThanOne() {
        // 2x chleb - price with tax: 10
        invoice.addProduct(new TaxFreeProduct("Chleb", new BigDecimal("5")), 2);
        // 3x chedar - price with tax: 32.40
        invoice.addProduct(new DairyProduct("Chedar", new BigDecimal("10")), 3);
        // 1000x pinezka - price with tax: 12.30
        invoice.addProduct(new OtherProduct("Pinezka", new BigDecimal("0.01")), 1000);
        Assert.assertThat(new BigDecimal("54.70"), Matchers.comparesEqualTo(invoice.getTotal()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvoiceWithZeroQuantity() {
        invoice.addProduct(new TaxFreeProduct("Tablet", new BigDecimal("1678")), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvoiceWithNegativeQuantity() {
        invoice.addProduct(new DairyProduct("Zsiadle mleko", new BigDecimal("5.55")), -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddingNullProduct() {
        invoice.addProduct(null);
    }

    @Test
    public void testCreateNumber() {
     Invoice invoice1 = new Invoice();
        String number = invoice1.createNumber();
        assert number != null;
    }
    @Test
    public void testCreateNumber_uniqe() {

       Set<String> invoice_numbers = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            Invoice invoice = new Invoice();
            invoice_numbers.add(invoice.createNumber());
        }
        assertEquals(100, invoice_numbers.size());
    }

    @Test
    public void testLineItems() {

        invoice.addProduct(new TaxFreeProduct("Chleb", new BigDecimal("5")), 2);

        List<String> lineItems = invoice.lineItems();
        String line = lineItems.get(0);
        String expected = String.format("%-20s | %8.2f PLN x %4d = %10.2f PLN", "Chleb", 5.00, 2, 10.00);
        assertEquals(expected, line);
    }


    @Test
    public void testItemCounter() {

        invoice.addProduct(new TaxFreeProduct("Maslo", new BigDecimal("5")), 2);
        invoice.addProduct(new TaxFreeProduct("Chleb", new BigDecimal("5")), 1);
        invoice.addProduct(new TaxFreeProduct("Pinezka", new BigDecimal("5")), 5);

        Integer expected = 2+1+5;
        assertEquals(expected, invoice.itemsCounter());
    }

    @Test
    public void testLineItemsWhenEmpty(){
        List<String> lines = invoice.lineItems();
        assertTrue(lines.isEmpty());
    }

    @Test
    public void testItemCounterWhenEmpty(){
        Integer expected = 0;
        assertEquals(expected, invoice.itemsCounter());
    }

    @Test
    public void testLineItemDuplicates(){
        invoice.addProduct(new TaxFreeProduct("Chleb", new BigDecimal("5")), 2);
        invoice.addProduct(new TaxFreeProduct("Chleb", new BigDecimal("5")), 2);
        List<String> lineItems = invoice.lineItems();
        String line = lineItems.get(0);
        String expected = String.format("%-20s | %8.2f PLN x %4d = %10.2f PLN", "Chleb", 5.00, 4, 20.00);
        assertEquals(expected, line);
    }

    @Test
    public void testLineItemDuplicatesWithoutQuantity(){
        invoice.addProduct(new TaxFreeProduct("Piwko", new BigDecimal("8")));
        invoice.addProduct(new TaxFreeProduct("Piwko", new BigDecimal("8")));
        List<String> lineItems = invoice.lineItems();
        String line = lineItems.get(0);
        String expected = String.format("%-20s | %8.2f PLN x %4d = %10.2f PLN", "Piwko", 8.00, 2, 16.00);
        assertEquals(expected, line);
    }

    @Test
    public void testLineItemWithTaxProducts(){
        invoice.addProduct(new DairyProduct("Mleko2.0", new BigDecimal("5")));
        invoice.addProduct(new DairyProduct("Mleko3.2", new BigDecimal("6")));
        List<String> lineItems = invoice.lineItems();
        String line1 = lineItems.get(0);
        String line2 = lineItems.get(1);
        String expected1 = String.format("%-20s | %8.2f PLN x %4d = %10.2f PLN", "Mleko2.0", 5.40, 1, 5.40);
        String expected2 = String.format("%-20s | %8.2f PLN x %4d = %10.2f PLN", "Mleko3.2", 6.48, 1, 6.48);
        assertEquals(expected1, line1);
        assertEquals(expected2, line2);
    }


}
