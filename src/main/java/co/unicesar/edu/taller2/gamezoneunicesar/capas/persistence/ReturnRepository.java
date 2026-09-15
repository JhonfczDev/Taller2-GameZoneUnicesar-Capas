package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import java.io.File;
import java.io.IOException;

public class ReturnRepository {

    private File file;

    public ReturnRepository() {this("data/returns.csv");}

    public ReturnRepository(String filepath) {
        this.file = new File(filepath);
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
            System.err.println("Error creating the returns file: " + e.getMessage());
        }
    }
}
