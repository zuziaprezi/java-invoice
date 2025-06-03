package pl.edu.agh.mwo.invoice;
import pl.edu.agh.mwo.invoice.product.DairyProduct;
import pl.edu.agh.mwo.invoice.product.OtherProduct;
import pl.edu.agh.mwo.invoice.product.TaxFreeProduct;

import java.math.BigDecimal;


public class Print {

   public static void main(String[] args) {

       Invoice invoice = new Invoice();
       invoice.addProduct(new TaxFreeProduct("Kubek", new BigDecimal("5")), 2);
       invoice.addProduct(new DairyProduct("Kozi Serek", new BigDecimal("10")), 3);
       invoice.addProduct(new OtherProduct("Pinezka", new BigDecimal("0.01")), 1000);
       invoice.addProduct(new TaxFreeProduct("Chleb", new BigDecimal("5")), 2);
       invoice.addProduct(new DairyProduct("Chedar", new BigDecimal("10")), 3);
       invoice.addProduct(new OtherProduct("Pinezka", new BigDecimal("0.01")), 1000);
       invoice.addProduct(new TaxFreeProduct("Owoce", new BigDecimal("200")));
       invoice.addProduct(new DairyProduct("Maslanka", new BigDecimal("100")));
       invoice.addProduct(new OtherProduct("Wino", new BigDecimal("10")));
       invoice.addProduct(new OtherProduct("Pinezka", new BigDecimal("0.01")), 1000);
       System.out.println("Numer faktury: " + invoice.createNumber());

       for(String itemLine : Invoice.lineItems()){
           System.out.println(itemLine);
       }
       System.out.println("Liczba produktów: " + invoice.itemsCounter());
       System.out.println("Suma: " + invoice.getTotal().setScale(2, BigDecimal.ROUND_HALF_UP));

   }
}
