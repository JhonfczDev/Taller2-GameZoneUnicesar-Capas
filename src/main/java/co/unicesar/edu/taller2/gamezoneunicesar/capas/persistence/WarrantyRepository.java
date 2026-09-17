package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.BasicWarranty;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.ExtendedWarranty;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Sale;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Warranty;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.service.ProductService;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.service.SaleService;

import java.io.*;
import java.util.*;

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

    public void saveAll(List<Warranty> warranties) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Warranty warranty : warranties) {

                String type = "";

                if (warranty instanceof BasicWarranty) {
                    type = "BASIC";
                } else if (warranty instanceof ExtendedWarranty) {
                    type = "EXTENDED";
                } else {

                    continue;
                }

                String line = type + ","
                        + warranty.getId() + ","
                        + warranty.getProduct().getId() + ","
                        + warranty.getSale().getId() + ","
                        + warranty.getStartDate();

                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {

            throw new RuntimeException("Error saving warranties...", e);
        }
    }

    private Sale findSaleById(String saleId) {
        for (Sale sale : saleService.getAllSales()) {
            if (sale.getId().equals(saleId)) {
                return sale;
            }
        }
        return null;
    }
}
