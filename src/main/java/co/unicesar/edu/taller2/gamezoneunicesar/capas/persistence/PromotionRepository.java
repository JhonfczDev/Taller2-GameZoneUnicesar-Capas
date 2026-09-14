package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import java.io.File;

public class PromotionRepository {
    private File file;

    public PromotionRepository() {this("data/promotions.csv");}

    public PromotionRepository(String filePath) {
        this.file = new File(filePath);
    }
}
