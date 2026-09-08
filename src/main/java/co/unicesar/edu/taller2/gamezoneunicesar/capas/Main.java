package co.unicesar.edu.taller2.gamezoneunicesar.capas;

import persistence.PersonRepository;
import persistence.ProductRepository;
import persistence.SaleRepository;
import service.PersonService;
import service.ProductService;
import service.SaleService;
import ui.ConsoleUI;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Initializing GameZone Unicesar System...");
        
        PersonRepository personRepository = new PersonRepository();
        ProductRepository productRepository = new ProductRepository();
        SaleRepository saleRepository = new SaleRepository();
        
        PersonService personService = new PersonService(personRepository);
        ProductService productService = new ProductService(productRepository);
        SaleService saleService = new SaleService(saleRepository, productService, personService);
        Scanner scanner = new Scanner(System.in);
        
        ConsoleUI consoleUI = new ConsoleUI(scanner, saleService, personService, productService );
        
        consoleUI.launch();
    }
}
