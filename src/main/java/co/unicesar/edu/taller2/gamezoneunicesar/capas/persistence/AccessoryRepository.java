package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import java.io.File;

public class AccessoryRepository {
    private File file;

    public AccessoryRepository() {this("data/accessories.csv");}

    public AccessoryRepository(String filePath) {
        this.file = new File(filePath);
    }
}
