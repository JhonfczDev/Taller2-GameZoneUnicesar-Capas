package co.unicesar.edu.taller2.gamezoneunicesar.capas;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.*;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.service.*;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.ui.UI;

import java.util.Scanner;

/**
 * Main class of the GameZone Unicesar application.
 * <p>
 * Responsible for initializing all the layers of the system (persistence,
 * services, and presentation) and starting the console-based user
 * interface through which the user interacts with the application.
 */
public class Main {

    /**
     * Entry point of the application.
     * <p>
     * Creates and injects the required dependencies in the following order:
     * <ol>
     *   <li>Repositories ({@link PersonRepository}, {@link ProductRepository},
     *       {@link SaleRepository}), responsible for data persistence.</li>
     *   <li>Services ({@link PersonService}, {@link ProductService},
     *       {@link SaleService}), responsible for the business logic.</li>
     *   <li>The console user interface ({@link ConsoleUI}), which receives
     *       the services and a {@link Scanner} to interact with the user.</li>
     * </ol>
     * Finally, it invokes {@link ConsoleUI#launch()} to start the
     * execution of the application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("Initializing GameZone Unicesar System...");
        
        PersonRepository personRepository = new PersonRepository();
        ProductRepository productRepository = new ProductRepository();
        SaleRepository saleRepository = new SaleRepository();
        AccessoryRepository accessoryRepository = new AccessoryRepository();
        PromotionRepository promotionRepository = new PromotionRepository();
        ReturnRepository returnRepository = new ReturnRepository();
        
        
        
        
        
        PersonService personService = new PersonService(personRepository);
        ProductService productService = new ProductService(productRepository);
        AccessoryService accessoryService = new AccessoryService(accessoryRepository);
        PromotionService promotionService = new PromotionService(promotionRepository);
        
        WarrantyRepository warrantyRepository = new WarrantyRepository(productService);
        WarrantyService warrantyService = new WarrantyService(warrantyRepository);
        
        
        SaleService saleService = new SaleService(saleRepository, productService, personService, accessoryService, promotionService, warrantyService);
        ReturnService returnService = new ReturnService(returnRepository, saleService, productService);
        Scanner scanner = new Scanner(System.in);
        
        warrantyRepository.setSaleService(saleService);
        
        returnRepository.setSaleService(saleService);      
        returnRepository.setProductService(productService);
        
        UI consoleUI = new UI(scanner, saleService, personService, productService, accessoryService, promotionService, returnService, warrantyService );
        
        consoleUI.launch();
    }
}