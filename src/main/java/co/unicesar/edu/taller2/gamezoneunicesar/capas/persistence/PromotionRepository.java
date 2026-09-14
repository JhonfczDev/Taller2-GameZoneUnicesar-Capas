package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PromotionRepository {
    private File file;

    public PromotionRepository() {this("data/promotions.csv");}

    public PromotionRepository(String filePath) {
        this.file = new File(filePath);
        ensureFileExists();
    }

    public void saveAll(List<Promotion> promotions){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Promotion promotion : promotions) {

                if (promotion instanceof PercentageDiscount) {
                    PercentageDiscount percentage = (PercentageDiscount) promotion;
                    String line = "PERCENTAGE,"
                            + percentage.getId() + ","
                            + percentage.getName() + ","
                            + percentage.getStartDate() + ","
                            + percentage.getEndDate() + ","
                            + percentage.getPercentage();

                    writer.write(line);
                    writer.newLine();

                } else if (promotion instanceof CategoryDiscount) {
                    CategoryDiscount category = (CategoryDiscount) promotion;

                    String line = "CATEGORY,"
                            + category.getId() + ","
                            + category.getName() + ","
                            + category.getStartDate() + ","
                            + category.getEndDate() + ","
                            + category.getPercentage() + ","
                            + category.getTargetCategory();

                    writer.write(line);
                    writer.newLine();

                } else if (promotion instanceof BulkPurchaseDiscount) {
                    BulkPurchaseDiscount bulk = (BulkPurchaseDiscount) promotion;

                    String line = "CATEGORY,"
                            + bulk.getId() + ","
                            + bulk.getName() + ","
                            + bulk.getStartDate() + ","
                            + bulk.getEndDate() + ","
                            + bulk.getMinQuantity() + ","
                            + bulk.getPercentage();

                    writer.write(line);
                    writer.newLine();
                }
            }

        } catch (IOException e) {

            throw new RuntimeException("Error saving promotions...", e);
        }
    }

    public List<Person> loadAll() throws FileNotFoundException {

        List<Promotion> promotions = new ArrayList<>();

        if (!file.exists()) {
            return promotions;

        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",");

                if (data[0].equals("PERCENTAGE")) {

                    PercentageDiscount percentage = new PercentageDiscount(
                            data[1],
                            data[2],
                            LocalDate.parse(data[3]),
                            LocalDate.parse(data[4]),
                            Double.parseDouble(data[5])
                    );

                    promotions.add(persentage);

                } else if (data[0].equals("CATEGORY")) {

                    CategoryDiscount category = new CategoryDiscount(
                            data[1],
                            data[2],
                            LocalDate.parse(data[3]),
                            LocalDate.parse(data[4]),
                            Integer.parseInt(data[5]),
                            Double.parseDouble(data[6])
                    );

                    promotions.add(category);

                } else if (data[0].equals("BULK")) {

                    BulkPurchaseDiscount bulk = new BulkPurchaseDiscount(
                            data[1],
                            data[2],
                            LocalDate.parse(data[3]),
                            LocalDate.parse(data[4]),
                            Integer.parseInt(data[5]),
                            Double.parseDouble(data[6])
                    );

                    promotions.add(bulk);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading promotions", e);
        }
        
        return promotions;
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
            System.err.println("Error creating the promotions file: " + e.getMessage());
        }
    }
}
