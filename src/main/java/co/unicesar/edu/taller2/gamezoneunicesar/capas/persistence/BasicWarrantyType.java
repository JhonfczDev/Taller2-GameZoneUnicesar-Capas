package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.BasicWarranty;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Product;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Sale;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Warranty;

import java.time.LocalDate;

/**
 * Storage description of {@link BasicWarranty}.
 */
public class BasicWarrantyType implements WarrantyType {

    @Override
    public Class<? extends Warranty> getWarrantyClass() {
        return BasicWarranty.class;
    }

    @Override
    public String getTag() {
        return "BASIC";
    }

    @Override
    public Warranty create(String id, Product product, Sale sale, LocalDate startDate) {
        return new BasicWarranty(id, product, sale, startDate);
    }
}
