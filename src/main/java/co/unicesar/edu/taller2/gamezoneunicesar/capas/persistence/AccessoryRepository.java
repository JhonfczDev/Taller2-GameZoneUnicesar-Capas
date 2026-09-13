package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import java.io.File;

public class AccessoryRepository {
    private File file;

    public AccessoryRepository() {this("data/accessories.csv");}

    public AccessoryRepository(String filePath) {
        this.file = new File(filePath);
        ensureFileExists();
    }

    private void ensureFileExists() {
        if (!file.exists()) {
            try {
                File parent = file.getParentFile();

                if (parent != null &&  !parent.exists()) {parent.mkdirs();}

                if (!file.exists()) {file.createNewFile();}
                
            } catch (Exception e) {
                throw new RuntimeException("Error creating accessories file", e);
            }
        }
    }
}
