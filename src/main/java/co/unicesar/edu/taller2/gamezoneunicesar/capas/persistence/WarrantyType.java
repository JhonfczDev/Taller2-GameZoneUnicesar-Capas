package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Product;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Sale;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Warranty;

import java.time.LocalDate;

/**
 * Describes how one concrete type of warranty is stored in the warranties file:
 * which class it is, which tag identifies it, and how to create it once its
 * product and sale are known.
 * <p>
 * Adding a new type of warranty only needs a new implementation registered in
 * the {@link WarrantyTypeRegistry}, without editing the repository.
 * </p>
 */
public interface WarrantyType {

    /**
     * Gets the concrete class of warranty handled by this type.
     *
     * @return the warranty class
     */
    Class<? extends Warranty> getWarrantyClass();

    /**
     * Gets the tag written in the first column of the line.
     *
     * @return the tag of the type
     */
    String getTag();

    /**
     * Creates a warranty of this type.
     *
     * @param id        id of the warranty
     * @param product   covered product
     * @param sale      sale the warranty belongs to
     * @param startDate date the coverage starts
     * @return the created warranty
     */
    Warranty create(String id, Product product, Sale sale, LocalDate startDate);
}
