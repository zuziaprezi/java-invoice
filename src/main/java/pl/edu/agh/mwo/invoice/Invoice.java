package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private static Map<Product, Integer> products = new HashMap<>();

    public  void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Cannot add null product");
        }


            if (!products.containsKey(product)) {
                this.products.put(product, 1);
            } else {
                this.products.put(product, products.getOrDefault(product,0) + 1);
            }

    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Cannot add null product");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Cannot add negative quantity");
        }
        if (!products.containsKey(product)) {
            this.products.put(product, quantity);
        } else {
            this.products.put(product, products.getOrDefault(product,0) + quantity);
        }
    }

    public BigDecimal getSubtotal() {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            subtotal = subtotal.add(entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
        }
        return subtotal;
    }

    public BigDecimal getTax() {
        return getTotal().subtract(getSubtotal());
    }

    public BigDecimal getTotal() {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            subtotal = subtotal.add(entry.getKey().getPriceWithTax().multiply(BigDecimal.valueOf(entry.getValue())));
        }
        return subtotal;
    }

    public String createNumber(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = currentDateTime.format(formatter);
        Random random = new Random();
        int numerWystawcy = random.nextInt(1000000);
        int numerNip = random.nextInt(1000000);
        String invoiceNumber = "";
        invoiceNumber =formattedDateTime + numerNip + numerWystawcy;
        return invoiceNumber;
    }

    public static List<String> lineItems() {
        List<String> itemLines = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {

              Product product = entry.getKey();
              Integer quantity = entry.getValue();
              BigDecimal priceWithTax = product.getPriceWithTax().setScale(2, RoundingMode.HALF_UP);
              BigDecimal sum = BigDecimal.valueOf(quantity).multiply(priceWithTax);
              String temp = String.format("%-20s | %8.2f PLN x %4d = %10.2f PLN", product, priceWithTax, quantity, sum);
              itemLines.add(temp);
        }
            return itemLines;

    }


    public Integer itemsCounter(){
        Integer itemsCount = 0;
        for(Map.Entry<Product, Integer> entry : products.entrySet()) {
            Integer quantity = entry.getValue();
            itemsCount += quantity;
        }
        return itemsCount;
    }



}
