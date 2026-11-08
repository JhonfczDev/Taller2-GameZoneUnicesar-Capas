package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Product;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Return;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReturnRepository {

    private File file;

    public ReturnRepository() {
        this("data/returns.csv");
    }

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

    public void saveAll(List<Return> returns){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Return returnItem : returns) {

                StringBuilder productIds = new StringBuilder();
                List<Product> returnedProducts = returnItem.getReturnedProducts();

                for (int i = 0; i < returnedProducts.size(); i++) {
                    productIds.append(returnedProducts.get(i).getId());
                    if (i < returnedProducts.size() - 1) {
                        productIds.append("|");
                    }
                }

                String line = returnItem.getId() + ","
                        + returnItem.getReturnDate() + ","
                        + returnItem.getSale().getId() + ","
                        + productIds + ","
                        + returnItem.getReason() + ","
                        + returnItem.getRefundAmount();

                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {

            throw new RuntimeException("Error saving returns...", e);
        }
    }

    /**
     * Reads the returns file and returns the raw data of every stored return.
     * The sale and the products are kept as ids: resolving them is a job of
     * the service layer. If the file does not exist, an empty list is returned.
     *
     * @return the raw records read from the file
     */
    public List<ReturnRecord> loadRecords(){

        List<ReturnRecord> records = new ArrayList<>();

        if (!file.exists()) {

            return records;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",", -1);

                String id = data[0];
                LocalDate returnDate = LocalDate.parse(data[1]);
                String saleId = data[2];
                String productIdsField = data[3];
                String reason = data[4];

                List<String> productIds = new ArrayList<>();
                if (!productIdsField.isEmpty()) {
                    for (String productId : productIdsField.split("\\|")) {
                        productIds.add(productId);
                    }
                }

                records.add(new ReturnRecord(id, returnDate, saleId, productIds, reason));
            }
        } catch (IOException e) {

            throw new RuntimeException("Error loading returns...", e);
        }

        return records;
    }
}
