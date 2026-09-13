package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Customer;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Person;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Seller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AccessoryRepository {
    private File file;

    public AccessoryRepository() {this("data/accessories.csv");}

    public AccessoryRepository(String filePath) {
        this.file = new File(filePath);
        ensureFileExists();
    }

    private String joinConsoleIds(List<String> consoleIds) {
        if (consoleIds == null || consoleIds.isEmpty()) {

            return "";
        }
        
        return String.join("|", consoleIds);
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
