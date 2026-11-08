package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PromotionRepository implements PromotionStorage {

    /** Serializers of the types of promotion that can be stored in the file. */
    private static final SerializerRegistry<Promotion> SERIALIZERS = new SerializerRegistry<Promotion>()
            .register(new PercentagePromotionSerializer())
            .register(new CategoryPromotionSerializer())
            .register(new BulkPurchasePromotionSerializer());

    private File file;

    public PromotionRepository() {this("data/promotions.csv");}

    public PromotionRepository(String filePath) {
        this.file = new File(filePath);
        ensureFileExists();
    }

    @Override
    public void saveAll(List<Promotion> promotions){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Promotion promotion : promotions) {
                LineSerializer<Promotion> serializer = SERIALIZERS.findByItem(promotion);

                if (serializer != null) {
                    writer.write(serializer.toLine(promotion));
                    writer.newLine();
                }
            }

        } catch (IOException e) {

            throw new RuntimeException("Error saving promotions...", e);
        }
    }

    /**
     * Reads the promotions file and rebuilds every stored promotion. If the file
     * does not exist, an empty list is returned.
     *
     * @return the list of stored promotions
     */
    @Override
    public List<Promotion> loadAll() {

        List<Promotion> promotions = new ArrayList<>();

        if (!file.exists()) {
            return promotions;

        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",");

                LineSerializer<Promotion> serializer = SERIALIZERS.findByTag(data[0]);

                if (serializer != null) {
                    promotions.add(serializer.fromFields(data));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading promotions", e);
        }

        return promotions;
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
