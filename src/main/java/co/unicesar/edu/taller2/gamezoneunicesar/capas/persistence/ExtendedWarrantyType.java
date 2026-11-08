package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.ExtendedWarranty;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Product;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Sale;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Warranty;

import java.time.LocalDate;

/**
 * Storage description of {@link ExtendedWarranty}.
 */
public class ExtendedWarrantyType implements WarrantyType {

    @Override
    public Class<? extends Warranty> getWarrantyClass() {
        return ExtendedWarranty.class;
    }

    @Override
    public String getTag() {
        return "EXTENDED";
    }

    @Override
    public Warranty create(String id, Product product, Sale sale, LocalDate startDate) {
        return new ExtendedWarranty(id, product, sale, startDate);
    }
}
