package co.unicesar.edu.taller2.gamezoneunicesar.capas.service;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.ProductRepository;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Product;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.VideoGame;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Console;

import java.util.List;

public class ProductService {
    private static ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public void registerConsole(String id, String title, double price, int stockquantity, String brand, String model, String generation){
        Console c = new Console(id, title, price, stockquantity, brand, model, generation);
        List<Product> products = repository.loadAll();
        products.add(c);
        repository.saveAll(products);
    }

    public void registerVideoGame(String id, String title, double price, int stockquantity, String platform, String genre, String ageRating){
        VideoGame vg = new VideoGame(id, title, price, stockquantity, platform, genre, ageRating);
        List<Product> products = repository.loadAll();
        products.add(vg);
        repository.saveAll(products);
    }
    
    public void restoreStock(String productId, int quantity) {
        Product product = findById(productId);
        if (product != null) {
            int currentStock = product.getStockQuantity();
            product.setStockQuantity(currentStock + quantity);
            update(product); // Esto persiste el cambio en el archivo de productos
        } else {
            throw new IllegalArgumentException("Product with ID " + productId + " not found for stock restoration.");
        }
    }

    public List<Product> getAllProducts(){
        return repository.loadAll();
    }

    public static Product findById(String id){
        return repository.findById(id);
    }

    public void update(Product product){
        repository.update(product);
    }
}
