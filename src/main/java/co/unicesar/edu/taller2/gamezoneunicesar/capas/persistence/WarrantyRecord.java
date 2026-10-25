package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.BasicWarranty;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.ExtendedWarranty;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Product;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Sale;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Warranty;

import java.time.LocalDate;

/**
 * Raw data of a warranty exactly as it is stored in the warranties file.
 * <p>
 * It keeps the ids of the product and of the sale instead of the objects, so
 * the persistence layer does not need any service to read the file. The
 * service layer resolves those ids and then asks this record to build the
 * {@link Warranty}.
 * </p>
 */
public class WarrantyRecord {

    private final String type;
    private final String id;
    private final String productId;
    private final String saleId;
    private final LocalDate startDate;

    /**
     * Creates a record with the data read from one line of the warranties file.
     *
     * @param type      type tag stored in the file ("BASIC" or "EXTENDED")
     * @param id        id of the warranty
     * @param productId id of the covered product
     * @param saleId    id of the sale the warranty belongs to
     * @param startDate date the coverage starts
     */
    public WarrantyRecord(String type, String id, String productId, String saleId, LocalDate startDate) {
        this.type = type;
        this.id = id;
        this.productId = productId;
        this.saleId = saleId;
        this.startDate = startDate;
    }

    /**
     * Gets the type tag stored in the file.
     *
     * @return the type tag
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the id of the warranty.
     *
     * @return the id of the warranty
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the id of the covered product.
     *
     * @return the id of the product
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Gets the id of the sale the warranty belongs to.
     *
     * @return the id of the sale
     */
    public String getSaleId() {
        return saleId;
    }

    /**
     * Gets the date the coverage starts.
     *
     * @return the start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Builds the {@link Warranty} from this record and the already resolved references.
     *
     * @param product the covered product
     * @param sale    the sale the warranty belongs to
     * @return the rebuilt warranty, or {@code null} if the type tag is unknown
     */
    public Warranty toWarranty(Product product, Sale sale) {
        if (type.equals("BASIC")) {
            return new BasicWarranty(id, product, sale, startDate);

        } else if (type.equals("EXTENDED")) {
            return new ExtendedWarranty(id, product, sale, startDate);
        }

        return null;
    }
}
