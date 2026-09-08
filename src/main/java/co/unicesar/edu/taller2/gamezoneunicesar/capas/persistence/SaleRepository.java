package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;
 
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Product;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Sale;
 
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
 
/**
 * Handles persistence of {@link Sale} objects to a text file.
 * <p>
 * Sales are stored as delimited lines in a flat file, where each
 * line represents one sale with its id, date, customer id, seller id,
 * and the ids of the products included in it.
 * </p>
 */
public class SaleRepository {
 
    /** Relative path of the file used to store sales. */
    private static final String FILE_PATH = "data/sales.txt";
 
    /** Delimiter used to separate the fields of a sale within a line. */
    private static final String DELIMITER = ";";
 
    /** Separator used between product ids within a sale line. */
    private static final String PRODUCT_SEPARATOR = ",";
 
    /**
     * Creates a new {@code SaleRepository}, ensuring that the
     * underlying storage file and its parent directories exist.
     */
    public SaleRepository() {
        ensureFileExists();
    }
 
    /**
     * Appends a single sale to the storage file.
     * <p>
     * The sale is converted to its line representation and written
     * at the end of the file. If an I/O error occurs, it is logged
     * to the standard error stream and the sale is not persisted.
     * </p>
     *
     * @param sale the sale to save
     */
   public void save(Sale sale) {
        try {
            String line = toLine(sale) + System.lineSeparator();
            Files.writeString(Paths.get(FILE_PATH), line,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error saving sale: " + e.getMessage());
        }
    }
 
    /**
     * Overwrites the storage file with the given list of sales.
     * <p>
     * Each sale in the list is converted to its line representation,
     * and the resulting lines fully replace the current content of
     * the file. If an I/O error occurs, it is logged to the standard
     * error stream.
     * </p>
     *
     * @param sales the list of sales to persist
     */
    public void saveAll(List<Sale> sales) {
        List<String> lines = new ArrayList<>();
        for (Sale sale : sales) {
            lines.add(toLine(sale));
        }
        try {
            Files.write(Paths.get(FILE_PATH), lines);
        } catch (IOException e) {
            System.err.println("Error saving all sales: " + e.getMessage());
        }
    }
 
    /**
     * Reads and returns all raw lines currently stored in the sales
     * file.
     * <p>
     * If the file does not exist, an empty list is returned. If an
     * I/O error occurs while reading, it is logged to the standard
     * error stream and an empty (or partially filled) list is
     * returned.
     * </p>
     *
     * @return the list of raw lines from the sales file
     */
    public List<String> findAllLines() {
        List<String> lines = new ArrayList<>();
        try {
            Path path = Paths.get(FILE_PATH);
            if (!Files.exists(path)) {
                return lines;
            }
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            System.err.println("Error loading sales: " + e.getMessage());
        }
        return lines;
    }
 
    /**
     * Converts a {@link Sale} into its delimited line representation
     * for storage.
     * <p>
     * The line includes the sale id, date, customer id, seller id,
     * and a comma-separated list of product ids.
     * </p>
     *
     * @param sale the sale to convert
     * @return the line representation of the sale
     */
    private String toLine(Sale sale) {
        StringBuilder productIds = new StringBuilder();
        List<Product> products = sale.getProducts();
 
        for (int i = 0; i < products.size(); i++) {
            productIds.append(products.get(i).getId());
            if (i < products.size() - 1) {
                productIds.append(PRODUCT_SEPARATOR);
            }
        }
 
        return sale.getId() + DELIMITER
                + sale.getDate() + DELIMITER
                + sale.getCustomer().getId() + DELIMITER
                + sale.getSeller().getId() + DELIMITER
                + productIds;
    }
 
    /**
     * Ensures that the storage file and its parent directories exist,
     * creating them if necessary.
     * <p>
     * If an I/O error occurs during creation, it is logged to the
     * standard error stream.
     * </p>
     */
    private void ensureFileExists() {
        try {
            Path path = Paths.get(FILE_PATH);
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            System.err.println("Error creating sales file: " + e.getMessage());
        }
    }
 
}