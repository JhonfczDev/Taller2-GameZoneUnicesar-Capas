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

public class SaleRepository {
    
    private static final String FILE_PATH = "data/sales.txt";
    private static final String DELIMITER = ";";
    private static final String PRODUCT_SEPARATOR = ",";
    
    public SaleRepository() {
        ensureFileExists();
    }
    
   public void save(Sale sale) {
        try {
            String line = toLine(sale) + System.lineSeparator();
            Files.writeString(Paths.get(FILE_PATH), line,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error saving sale: " + e.getMessage());
        }
    }
   
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
