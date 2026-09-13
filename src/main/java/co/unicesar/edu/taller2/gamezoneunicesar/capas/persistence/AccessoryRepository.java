package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Controller;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Cable;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Memory;

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

    public void saveAll(List<Accessory> Accessories){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Accessory accessory: accessories) {

                if (accessory instanceof Controller) {
                    Controller controller = (Controller) accessory;
                    String line = "CONTROLLER,"
                            + controller.getId() + ","
                            + controller.getTitle() + ","
                            + controller.getPrice() + ","
                            + controller.getStockQuantity() + ","
                            + joinConsoleIds(memory.getCompatibleConsoleIds());

                    writer.write(line);
                    writer.newLine();

                } else if (accessory instanceof Cable) {
                    Cable cable = (Cable) accessory;

                    String line = "CABLE,"
                            + cable.getId() + ","
                            + cable.getTitle() + ","
                            + cable.getPrice() + ","
                            + cable.getStockQuantity() + ","
                            + cable.getLengthInMeters() + ","
                            + cable.getConnectorType();

                    writer.write(line);
                    writer.newLine();

                } else if (accessory instanceof Memory) {
                    Memory memory = (Memory) accessory;

                    String line = "MEMORY,"
                            + memory.getId() + ","
                            + memory.getTitle() + ","
                            + memory.getPrice() + ","
                            + memory.getStockQuantity() + ","
                            + memory.getCapacityInGigabytes() + ","
                            + memory.getMemoryType() + ","
                            + joinConsoleIds(memory.getCompatibleConsoleIds());

                    writer.write(line);
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error saving accessories...", e);
        }
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
