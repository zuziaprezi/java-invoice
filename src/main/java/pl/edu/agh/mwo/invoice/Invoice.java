package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Map<Product, Integer> products = new HashMap<>();

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Cannot add null product");
        }
        this.products.put(product,1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Cannot add null product");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Cannot add negative quantity");
        }
        this.products.put(product, quantity);
    }

    public BigDecimal getNetPrice() {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            subtotal = subtotal.add(entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
        }
        return subtotal;
    }

    public BigDecimal getTax() {
        return getGrossPrice().subtract(getNetPrice());
    }

    public BigDecimal getGrossPrice() {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            subtotal = subtotal.add(entry.getKey().getPriceWithTax().multiply(BigDecimal.valueOf(entry.getValue())));
        }
        return subtotal;
    }
}
