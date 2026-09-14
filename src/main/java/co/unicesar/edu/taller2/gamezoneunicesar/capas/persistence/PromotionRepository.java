package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.PercentageDiscount;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.CategoryDiscount ;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.BulkPurchaseDiscount;

import java.io.*;
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
