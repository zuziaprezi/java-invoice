package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public abstract class Product {
    private final String name;

    private final BigDecimal price;

    private final BigDecimal taxPercent;

    @Override
    public String toString() {
        return name;
    }
    protected Product(String name, BigDecimal price, BigDecimal tax) {
        if(name == null || price == null || tax == null) {
            throw new IllegalArgumentException("Cannot create product with null parameters");
        }
        if(name.equals("")) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if(price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.name = name;
        this.price = price;
        this.taxPercent = tax;
    }


    public String getName() {
        return this.name;

    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public BigDecimal getTaxPercent() {
        return this.taxPercent;
    }

    public BigDecimal getPriceWithTax() {
        return price.add(price.multiply(this.taxPercent));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }


}
