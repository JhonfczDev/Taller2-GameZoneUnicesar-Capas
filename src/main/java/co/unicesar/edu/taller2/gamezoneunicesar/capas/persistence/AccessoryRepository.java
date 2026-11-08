package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AccessoryRepository implements AccessoryStorage {

    /** Serializers of the types of accessory that can be stored in the file. */
    private static final SerializerRegistry<Accessory> SERIALIZERS = new SerializerRegistry<Accessory>()
            .register(new ControllerSerializer())
            .register(new CableSerializer())
            .register(new MemorySerializer());

    private File file;

    public AccessoryRepository() {this("data/accessories.csv");}

    public AccessoryRepository(String filePath) {
        this.file = new File(filePath);
        ensureFileExists();
        initializeDefaultAccessories();
    }

    @Override
    public void saveAll(List<Accessory> accessories){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Accessory accessory: accessories) {
                LineSerializer<Accessory> serializer = SERIALIZERS.findByItem(accessory);

                if (serializer != null) {
                    writer.write(serializer.toLine(accessory));
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error saving accessories...", e);
        }
    }

    @Override
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

                LineSerializer<Accessory> serializer = SERIALIZERS.findByTag(data[0]);

                if (serializer != null) {
                    accessories.add(serializer.fromFields(data));
                }
            }
        } catch (IOException e) {

            throw new RuntimeException("Error loading accessories...", e);
        }

        return accessories;
    }

    @Override
    public Accessory findById(String id) {
        for (Accessory accessory : loadAll()) {
            if (accessory.getId().equals(id)) {
                return accessory;
            }
        }
        return null;
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