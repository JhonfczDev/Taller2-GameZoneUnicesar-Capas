package co.unicesar.edu.taller2.gamezoneunicesar.capas.model;

import java.time.LocalDate;

public class ExtendedWarranty extends Warranty {

    public ExtendedWarranty(String id, Product product, Sale sale, LocalDate startDate) {
        super(id, product, sale, startDate);
    }

    @Override
    public int getDurationInMonths(){
        return 12;
    }

    @Override
    public String getWarrantyType(){
        return "Garantia Extendida";
    }

    @Override
    public double getAdditionalCost(){
        // calculate the 10% using getPrice from the abstract class Product
        return this.getProduct().getPrice() * 0.10;
    }
}
