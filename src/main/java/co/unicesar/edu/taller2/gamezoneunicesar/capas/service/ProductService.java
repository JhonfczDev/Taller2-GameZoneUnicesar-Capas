package co.unicesar.edu.taller2.gamezoneunicesar.capas.service;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.ProductRepository;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.validations.ProductValidator;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Product;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.VideoGame;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Console;

import java.util.List;

/**
 * Service responsible for the business logic of the product module:
 * registering consoles and video games, listing products, looking them up
 * by id and updating them.
 * <p>
 * Input validation is delegated to {@link ProductValidator}, which throws
 * the typed excepcions of the custom exception hierarchy.
 * </p>
 */
public class ProductService {
    private static ProductRepository repository;

    /**
     * Creates a new {@code ProductService} with the given repository.
     *
     * @param repository repository used to persist and retrieve products
     */
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    /**
     * Registers a new console after validating its common product data.
     *
     * @param id            console identifier
     * @param title         console title
     * @param price         console price; must be positive
     * @param stockquantity initial stock; must not be negative
     * @param brand         console brand
     * @param model         console model
     * @param generation    console generation
     * @throws co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.InvalidDataException
     *         if the id or title is empty, the price is not positive or the stock is negative
     * @throws co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.PersistenceException
     *         if the products file cannot be read or written
     */
    public void registerConsole(String id, String title, double price, int stockquantity, String brand, String model, String generation){
        Console c = new Console(id, title, price, stockquantity, brand, model, generation);
        List<Product> products = repository.loadAll();
        products.add(c);
        repository.saveAll(products);
    }

    /**
     * Registers a new video game after validating its common product data.
     *
     * @param id            video game identifier
     * @param title         video game title
     * @param price         video game price; must be positive
     * @param stockquantity initial stock; must not be negative
     * @param platform      platform the game runs on
     * @param genre         game genre
     * @param ageRating     age rating
     * @throws co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.InvalidDataException
     *         if the id or title is empty, the price is not positive or the stock is negative
     * @throws co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.PersistenceException
     *         if the products file cannot be read or written
     */
    public void registerVideoGame(String id, String title, double price, int stockquantity, String platform, String genre, String ageRating){
        // Same validation as consoles: the rules are shared by every product type
        ProductValidator.validateProductData(id, title, price, stockquantity);

        VideoGame vg = new VideoGame(id, title, price, stockquantity, platform, genre, ageRating);
        List<Product> products = repository.loadAll();
        products.add(vg);
        repository.saveAll(products);
    }

    /**
     * Increases the stock of a product by the given quantity and persists the
     * change. It is used by the returns module so that returned units become
     * available for sale again, reusing the existing update logic instead of
     * duplicating it.
     *
     * @param productId identifier of the product whose stock is restored
     * @param quantity  number of units to add to the current stock
     * @throws co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.ResourceNotFoundExcepcion
     *         if the product does not exist
     * @throws co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.PersistenceException
     *         if the products file cannot be read or written
     */
    public void restoreStock(String productId, int quantity) {
        Product product = findById(productId);

        // The validator turns a null product into a ResourceNotFoundException
        ProductValidator.validateProductExist(product, productId);

        product.setStockQuantity(product.getStockQuantity() + quantity);
        update(product);
    }


    /**
     * Retrieves every stored product.
     *
     * @return the list of all products; empty if none are stored
     * @throws co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.PersistenceException
     *         if the products file cannot be read
     */
    public List<Product> getAllProducts(){
        return repository.loadAll();
    }

    /**
     * Finds a product by its id.
     * <p>
     * It intentionally keeps returning {@code null} when the product does not
     * exist: other classes (SaleService, repositories that rebuild sales,
     * returns or warranties) rely on that contract. Callers that must treat a
     * missing product as an error call
     * {@link ProductValidator#validateProductExist(Product, String)}.
     * </p>
     *
     * @param id product identifier
     * @return the product found, or {@code null} if it does not exist
     */
    public static Product findById(String id){
        return repository.findById(id);
    }

    /**
     * Updates an existing product in the storage.
     *
     * @param product product with the updated data
     * @throws co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.PersistenceException
     *         if the products file cannot be read or written
     */
    public static void update(Product product){
        repository.update(product);
    }
}
