package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.*;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.service.ProductService;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.service.SaleService;

import java.io.*;
import java.time.LocalDate;
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

    public List<Warranty> loadAll() {

        List<Warranty> warranties = new ArrayList<>();

        if (!file.exists()) {
            return warranties;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",");

                String type = data[0];
                String id = data[1];
                Product product = productService.findById(data[2]);
                Sale sale = findSaleById(data[3]);
                LocalDate startDate = LocalDate.parse(data[4]);
                int duration = Integer.parseInt(data[5]);
                String fabricDefects = data[6];
                String priceProduct = String.valueOf(product != null ? product.getPrice() : 0);

                if (product == null || sale == null) continue;

                if (type.equals("BASIC")) {
                    BasicWarranty basic = new BasicWarranty(fabricDefects, duration, priceProduct, id);
                    basic.setProduct(product);
                    basic.setSale(sale);

                    basic.setStartDate(startDate);

                    warranties.add(basic);

                } else if (type.equals("EXTENDED")) {
                    String accidentalDamage = data[7];
                    double additionalCost = Double.parseDouble(data[8]);

                    ExtendedWarranty extended = new ExtendedWarranty(
                            accidentalDamage, additionalCost, fabricDefects, duration, priceProduct, id);
                    extended.setProduct(product);
                    extended.setSale(sale);
                    extended.setStartDate(startDate);

                    warranties.add(extended);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading warranties...", e);
        }

        return warranties;
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
