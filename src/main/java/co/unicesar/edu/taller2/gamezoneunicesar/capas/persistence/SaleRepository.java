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
