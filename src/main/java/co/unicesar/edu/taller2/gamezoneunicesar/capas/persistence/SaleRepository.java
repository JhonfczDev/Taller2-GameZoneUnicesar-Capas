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
    
    
}
