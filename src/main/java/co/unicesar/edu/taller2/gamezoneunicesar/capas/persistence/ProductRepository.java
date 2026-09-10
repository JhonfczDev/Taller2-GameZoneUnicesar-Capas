package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Product;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.VideoGame;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Console;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductRepository {
    private File file;

    public ProductRepository() {
        this("data/products.txt");
    }

    public ProductRepository(String filePath) {
        this.file = new File(filePath);
        ensureFileExists();
    }

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
            throw new RuntimeException("Error al guardar los productos ", e);
        }
    }

    public List<Product> loadAll(){
        List<Product> products = new ArrayList<>();
        
        if (!file.exists()){
            return products;
        }

        try (FileInputStream in = new FileInputStream(file);
             Scanner scanner = new Scanner(in)){

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
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
                 throw new RuntimeException("Error al cargar los productos ", e);
             }
             return products; 
    }

    public Product findById(String id) {
        List<Product> products = loadAll();
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

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

    private void ensureFileExists() {
        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs(); //crea carpetas intermedias si no existen
            }
            if (!file.exists()) {
                file.createNewFile(); // crea el archivo vacio
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al crear el archivo de productos" + e.getMessage());
        }
    }
}
