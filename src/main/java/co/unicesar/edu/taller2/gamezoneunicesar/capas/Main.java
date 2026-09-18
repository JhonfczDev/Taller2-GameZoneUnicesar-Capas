package co.unicesar.edu.taller2.gamezoneunicesar.capas;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.PersonRepository;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.ProductRepository;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.SaleRepository;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.service.PersonService;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.service.ProductService;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.service.SaleService;
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
        
        PersonService personService = new PersonService(personRepository);
        ProductService productService = new ProductService(productRepository);
        SaleService saleService = new SaleService(saleRepository, productService, personService);
        Scanner scanner = new Scanner(System.in);
        
        UI consoleUI = new UI(scanner, saleService, personService, productService );
        
        consoleUI.launch();
    }
}