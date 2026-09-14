package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import java.io.*;

public class PromotionRepository {
    private File file;

    public PromotionRepository() {this("data/promotions.csv");}

    public PromotionRepository(String filePath) {
        this.file = new File(filePath);
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
            System.err.println("Error creating the promotions file: " + e.getMessage());
        }
    }
}
