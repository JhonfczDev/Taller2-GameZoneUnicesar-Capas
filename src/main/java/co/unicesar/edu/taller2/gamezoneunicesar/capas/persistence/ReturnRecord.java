package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Product;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Return;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Sale;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Raw data of a return exactly as it is stored in the returns file.
 * <p>
 * It keeps the ids of the sale and of the returned products instead of the
 * objects, so the persistence layer does not need any service to read the
 * file. The service layer resolves those ids and then asks this record to
 * build the {@link Return}.
 * </p>
 */
public class ReturnRecord {

    private final String id;
    private final LocalDate returnDate;
    private final String saleId;
    private final List<String> productIds;
    private final String reason;

    /**
     * Creates a record with the data read from one line of the returns file.
     *
     * @param id         id of the return
     * @param returnDate date of the return
     * @param saleId     id of the original sale
     * @param productIds ids of the returned products
     * @param reason     reason for the return
     */
    public ReturnRecord(String id, LocalDate returnDate, String saleId, List<String> productIds, String reason) {
        this.id = id;
        this.returnDate = returnDate;
        this.saleId = saleId;
        this.productIds = Collections.unmodifiableList(new ArrayList<>(productIds));
        this.reason = reason;
    }

    /**
     * Gets the id of the return.
     *
     * @return the id of the return
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the date of the return.
     *
     * @return the date of the return
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Gets the id of the original sale.
     *
     * @return the id of the original sale
     */
    public String getSaleId() {
        return saleId;
    }

    /**
     * Gets the ids of the returned products.
     *
     * @return an unmodifiable list with the product ids
     */
    public List<String> getProductIds() {
        return productIds;
    }

    /**
     * Gets the reason of the return.
     *
     * @return the reason of the return
     */
    public String getReason() {
        return reason;
    }

    /**
     * Builds the {@link Return} from this record and the already resolved references.
     * The refund amount is calculated from the returned products.
     *
     * @param sale             the original sale
     * @param returnedProducts the returned products
     * @return the rebuilt return
     */
    public Return toReturn(Sale sale, List<Product> returnedProducts) {
        Return returnItem = new Return(id, returnDate, sale, returnedProducts, reason);
        returnItem.calculateRefundAmount();

        return returnItem;
    }
}
