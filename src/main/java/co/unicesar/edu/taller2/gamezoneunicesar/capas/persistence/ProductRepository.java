package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.PersistenceException;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Console;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Product;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.VideoGame;

import java.io.*;
import java.util.*;

/**
 * Repository responsible for persisting Product objects (VideoGame and Console)
 * to a plain text file.
 * <p>
 * Every {@link IOException} raised while accessing the file is caught here and
 * rethrown as a {@link PersistenceException} that includes the file path and the
 * operation being performed, so upper layers never deal with low-level I/O errors.
 * </p>
 */
public class ProductRepository {

    /** Physical file where products are read from / written to. */
    private File file;

    /**
     * Default constructor: uses the fixed default path ("data/products.txt").
     *
     * @throws PersistenceException if the data file cannot be created
     */
    public ProductRepository() {
        this("data/products.txt");
    }

    /**
     * Constructor: receives the path of the file used to persist products.
     *
     * @param filePath path of the text file
     * @throws PersistenceException if the data file cannot be created
     */
    public ProductRepository(String filePath) {
        this.file = new File(filePath);
        ensureFileExists();
    }

    /**
     * Saves the full list of products to the file, overwriting its previous content.
     * The type tag at the beginning of each line ("JUEGO" or "CONSOLA") lets
     * {@link #loadAll()} know which concrete class to rebuild.
     *
     * @param products list of products to save
     * @throws PersistenceException if the file cannot be written
     */
    public void saveAll(List<Product> products){
        try (FileOutputStream out = new FileOutputStream(file)){
            for (Product product : products){
                String line = "";

                if (product instanceof VideoGame){
                    VideoGame vg = (VideoGame) product;
                    line = "JUEGO, "
                            + vg.getId() + ", "
                            + vg.getTitle() + ", "
                            + vg.getPrice() + ", "
                            + vg.getStockQuantity() + ", "
                            + vg.getPlatform() + ", "
                            + vg.getGenre() + ", "
                            + vg.getAgeRating() + "\n";

                }else if (product instanceof Console){
                    Console c = (Console) product;
                    line = "CONSOLA, "
                            + c.getId() + ", "
                            + c.getTitle() + ", "
                            + c.getBrand() + ", "
                            + c.getModel() + ", "
                            + c.getGeneration() + ", "
                            + c.getPrice() + ", "
                            + c.getStockQuantity() + "\n";

                }
                out.write(line.getBytes());
            }

        } catch (IOException e){
            // The original IOException is kept as the cause to preserve traceability
            throw new PersistenceException(
                    "Error writing products to file '" + file.getPath() + "' (save operation)", e);
        }
    }

    /**
     * Reads the file and rebuilds the list of products. If the file does not
     * exist, an empty list is returned instead of failing. Empty lines are skipped.
     *
     * @return list of products loaded from the file
     * @throws PersistenceException if the file cannot be read
     */
    public List<Product> loadAll(){
        List<Product> products = new ArrayList<>();

        // Nothing to load if the file does not exist yet
        if (!file.exists()){
            return products;
        }

        try (FileInputStream in = new FileInputStream(file);
             Scanner sc = new Scanner(in)){

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",");

                if (data[0].equals("JUEGO")) {
                    VideoGame vg = new VideoGame(
                            data[1], data[2], Double.parseDouble(data[3]),
                            Integer.parseInt(data[4]), data[5], data[6], data[7]
                    );
                    products.add(vg);

                } else if (data[0].equals("CONSOLA")) {
                    Console c = new Console(
                            data[1], data[2], Double.parseDouble(data[6]),
                            Integer.parseInt(data[7]), data[3], data[4], data[5]
                    );
                    products.add(c);
                }
            }
        }  catch (IOException e){
            throw new PersistenceException(
                    "Error reading products from file '" + file.getPath() + "' (load operation)", e);
        }
        return products;
    }

    /**
     * Finds a product by its id. Returns null when it does not exist; deciding
     * whether that is an error is the responsibility of the service/validator layer.
     *
     * @param id product identifier
     * @return the product found, or null if it does not exist
     * @throws PersistenceException if the file cannot be read
     */
    public Product findById(String id) {
        return loadAll().stream().filter(product -> product.getId().equals(id))
                .findFirst().orElse(null);
    }

    /**
     * Replaces the stored product that has the same id as the given one and
     * persists the whole list again.
     *
     * @param product product with the updated data
     * @throws PersistenceException if the file cannot be read or written
     */
    public void update(Product product) {
        List<Product> products = loadAll();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(product.getId())) {
                products.set(i, product);
                break;
            }
        }
        saveAll(products);
    }

    /**
     * Ensures that the data file physically exists on disk, creating the parent
     * directories and the empty file when needed.
     *
     * @throws PersistenceException if the directories or the file cannot be created
     */
    private void ensureFileExists() {
        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs(); // creates intermediate folders if they do not exist
            }
            if (!file.exists()) {
                file.createNewFile(); // creates an empty file
            }
        } catch (IOException e) {
            throw new PersistenceException(
                    "Error creating products file '" + file.getPath() + "' (create operation)", e);
        }
    }
}