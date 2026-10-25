package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class WarrantyRepository {

    private File file;

    /**
     * Creates a repository that uses the default file path ("data/warranties.csv").
     */
    public WarrantyRepository() {
        this("data/warranties.csv");
    }

    /**
     * Creates a repository that uses the given file path.
     *
     * @param fileName path of the file used to store the warranties
     */
    public WarrantyRepository(String fileName) {
        this.file = new File(fileName);
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
            System.err.println("Error creating the warranties file: " + e.getMessage());
        }
    }

    public void saveAll(List<Warranty> warranties) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Warranty warranty : warranties) {

                String type = "";

                if (warranty instanceof BasicWarranty) {
                    type = "BASIC";
                } else if (warranty instanceof ExtendedWarranty) {
                    type = "EXTENDED";
                } else {

                    continue;
                }

                String line = type + ","
                        + warranty.getId() + ","
                        + warranty.getProduct().getId() + ","
                        + warranty.getSale().getId() + ","
                        + warranty.getStartDate();

                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {

            throw new RuntimeException("Error saving warranties...", e);
        }
    }

    /**
     * Reads the warranties file and returns the raw data of every stored warranty.
     * The product and the sale are kept as ids: resolving them is a job of the
     * service layer. If the file does not exist, an empty list is returned.
     *
     * @return the raw records read from the file
     */
    public List<WarrantyRecord> loadRecords() {

        List<WarrantyRecord> records = new ArrayList<>();

        if (!file.exists()) {
            return records;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",");

                String type = data[0];
                String id = data[1];
                String productId = data[2];
                String saleId = data[3];
                LocalDate startDate = LocalDate.parse(data[4]);

                records.add(new WarrantyRecord(type, id, productId, saleId, startDate));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading warranties...", e);
        }

        return records;
    }
}
