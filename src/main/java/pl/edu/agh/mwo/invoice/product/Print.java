package pl.edu.agh.mwo.invoice.product;

import org.w3c.dom.ls.LSOutput;
import pl.edu.agh.mwo.invoice.Invoice;

public class Print {

   public static void main(String[] args) {

       System.out.println("Numer faktury: " + Invoice.createNumber());


   }
}
