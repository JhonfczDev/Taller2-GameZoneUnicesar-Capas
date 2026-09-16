package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.service.ProductService;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.service.SaleService;

import java.io.*;

public class WarrantyRepository {

    private File file;
    private SaleService saleService;
    private ProductService productService;

    public WarrantyRepository(SaleService saleService, ProductService productService) {
        this("data/warranties.csv", saleService, productService);
    }

    public WarrantyRepository(String fileName, SaleService saleService, ProductService productService) {
        this.file = new File(fileName);
        this.saleService = saleService;
        this.productService = productService;
        ensureFileExists();
    }

    private void ensureFileExists() {
        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error creating the warranties file: " + e.getMessage());
        }
    }
}
