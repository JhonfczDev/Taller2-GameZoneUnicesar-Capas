package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AccessoryRepository {
    private File file;

    private static final String CONSOLE_SEPARATOR = "\\|";

    public AccessoryRepository() {this("data/accessories.csv");}

    public AccessoryRepository(String filePath) {
        this.file = new File(filePath);
        ensureFileExists();
        initializeDefaultAccessories();
    }

    public void saveAll(List<Accessory> accessories){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Accessory accessory: accessories) {

                if (accessory instanceof Controller) {
                    Controller controller = (Controller) accessory;
                    String line = "CONTROLLER,"
                            + controller.getId() + ","
                            + controller.getTitle() + ","
                            + controller.getPrice() + ","
                            + controller.getStockQuantity() + ","
                            + controller.getConnectionType() + ","
                            + joinConsoleIds(controller.getCompatibleConsoles());

                    writer.write(line);
                    writer.newLine();

                } else if (accessory instanceof Cable) {
                    Cable cable = (Cable) accessory;

                    String line = "CABLE,"
                            + cable.getId() + ","
                            + cable.getTitle() + ","
                            + cable.getPrice() + ","
                            + cable.getStockQuantity() + ","
                            + cable.getLengthMeters() + ","
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
                            + memory.getCapacityGB() + ","
                            + memory.getMemoryType() + ","
                            + joinConsoleIds(memory.getCompatibleConsoles());

                    writer.write(line);
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error saving accessories...", e);
        }
    }

    public List<Accessory> loadAll(){

        List<Accessory> accessories = new ArrayList<>();

        if (!file.exists()) {
            return accessories;

        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",");

                if (data[0].equals("CONTROLLER")) {

                    Controller controller = new Controller(
                            data[1],
                            data[2],
                            Double.parseDouble(data[3]),
                            Integer.parseInt(data[4]),
                            data[5]
                    );

                    controller.setCompatibleConsoles(splitConsoleIds(data.length > 6 ? data[6] : ""));

                    accessories.add(controller);

                } else if (data[0].equals("CABLE")) {

                    Cable cable = new Cable(
                            data[1],
                            data[2],
                            Double.parseDouble(data[3]),
                            Integer.parseInt(data[4]),
                            Double.parseDouble(data[5]),
                            data[6]
                    );

                    accessories.add(cable);

                } else if (data[0].equals("MEMORY")) {

                    Memory memory = new Memory(
                            data[1],
                            data[2],
                            Double.parseDouble(data[3]),
                            Integer.parseInt(data[4]),
                            Integer.parseInt(data[5]),
                            data[6]
                    );

                    memory.setCompatibleConsoles(splitConsoleIds(data.length > 7 ? data[7] : ""));

                    accessories.add(memory);
                }
            }
        } catch (IOException e) {

            throw new RuntimeException("Error loading accessories...", e);
        }

        return accessories;
    }

    public Accessory findById(String id) {
        for (Accessory accessory : loadAll()) {
            if (accessory.getId().equals(id)) {
                return accessory;
            }
        }
        return null;
    }

    private String joinConsoleIds(List<String> consoleIds) {
        if (consoleIds == null || consoleIds.isEmpty()) {

            return "";
        }

        return String.join("|", consoleIds);
    }

    private List<String> splitConsoleIds(String rawField) {
        List<String> consoleIds = new ArrayList<>();
        if (rawField == null || rawField.isBlank()) {
            return consoleIds;
        }
        for (String consoleId : rawField.split(CONSOLE_SEPARATOR)) {
            if (!consoleId.isBlank()) {
                consoleIds.add(consoleId.trim());
            }
        }
        return consoleIds;
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

    private void initializeDefaultAccessories() {
        if (file.exists() && file.length() > 0) {
            
            return;
        }

        List<Accessory> defaultAccessories = new ArrayList<>();

        Controller defaultController = new Controller(
                "AC001",
                "Wireless remote Pro",
                180000.0,
                15,
                "WIRELESS"
        );
        defaultController.setCompatibleConsoles(new ArrayList<>(List.of("CON001")));
        defaultAccessories.add(defaultController);

        defaultAccessories.add(
                new Cable(
                        "AC002",
                        "Cable HDMI 2.1",
                        45000.0,
                        30,
                        1.8,
                        "HDMI"
                )
        );

        Memory defaultMemory = new Memory(
                "AC003",
                "Card MicroSD 128GB",
                95000.0,
                20,
                128,
                "MICRO_SD"
        );
        defaultMemory.setCompatibleConsoles(new ArrayList<>(List.of("CON001")));
        defaultAccessories.add(defaultMemory);

        saveAll(defaultAccessories);
    }
}
