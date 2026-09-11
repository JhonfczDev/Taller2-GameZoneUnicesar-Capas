package co.unicesar.edu.taller2.gamezoneunicesar.capas.service;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.ProductRepository;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Product;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.VideoGame;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Console;

import java.util.List;

public class ProductService {
    private ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public void registerProduct(String id, String title, double price, int stockquantity, String platform, String genre, String ageRating){
        VideoGame videoGame = new VideoGame(id, title, price, stockquantity, platform, genre, ageRating);
        List<Product> products = repository.loadAll();
        products.add(videoGame);
        repository.saveAll(products);
    }

    public void registerConsole(String id, String title, double price, int stockquantity, String brand, String model, String generation){
        Console console = new Console(id, title, price, stockquantity, brand, model, generation);
        List<Product> products = repository.loadAll();
        products.add(console);
        repository.saveAll(products);
    }

    public void registerVideoGame(String id, String title, double price, int stockquantity, String platform, String genre, String ageRating){
        VideoGame videoGame = new VideoGame(id, title, price, stockquantity, platform, genre, ageRating);
        List<Product> products = repository.loadAll();
        products.add(videoGame);
        repository.saveAll(products);
    }

    public List<Product> getAllProducts(){
        return repository.loadAll();
    }

    public Product findById(String id){
        return repository.findById(id);
    }

    public void update(Product product){
        repository.update(product);
    }
}
