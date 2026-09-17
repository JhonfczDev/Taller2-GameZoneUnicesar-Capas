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

                if (promotion instanceof PercentagePromotion) {
                    PercentagePromotion percentage = (PercentagePromotion) promotion;
                    String line = "PERCENTAGE,"
                            + percentage.getId() + ","
                            + percentage.getName() + ","
                            + percentage.getStartDate() + ","
                            + percentage.getEndDate() + ","
                            + percentage.getPercentage();

                    writer.write(line);
                    writer.newLine();

                } else if (promotion instanceof CategoryPromotion) {
                    CategoryPromotion category = (CategoryPromotion) promotion;

                    String line = "CATEGORY,"
                            + category.getId() + ","
                            + category.getName() + ","
                            + category.getStartDate() + ","
                            + category.getEndDate() + ","
                            + category.getPercentage() + ","
                            + category.getTargetCategory();

                    writer.write(line);
                    writer.newLine();

                } else if (promotion instanceof BulkPurchasePromotion) {
                    BulkPurchasePromotion bulk = (BulkPurchasePromotion) promotion;

                    String line = "BULK,"
                            + bulk.getId() + ","
                            + bulk.getName() + ","
                            + bulk.getStartDate() + ","
                            + bulk.getEndDate() + ","
                            + bulk.getMinProducts() + ","
                            + bulk.getPercentage();

                    writer.write(line);
                    writer.newLine();
                }
            }

        } catch (IOException e) {

            throw new RuntimeException("Error saving promotions...", e);
        }
    }

    public List<Promotion> loadAll() throws FileNotFoundException {

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

                    PercentagePromotion percentage = new PercentagePromotion(
                            data[1],
                            data[2],
                            LocalDate.parse(data[3]),
                            LocalDate.parse(data[4]),
                            Double.parseDouble(data[5])
                    );

                    promotions.add(percentage);

                } else if (data[0].equals("CATEGORY")) {

                    CategoryPromotion category = new CategoryPromotion(
                            data[1],
                            data[2],
                            LocalDate.parse(data[3]),
                            LocalDate.parse(data[4]),
                            Double.parseDouble(data[5]),
                            data[6]

                    );

                    promotions.add(category);

                } else if (data[0].equals("BULK")) {

                    BulkPurchasePromotion bulk = new BulkPurchasePromotion(
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
