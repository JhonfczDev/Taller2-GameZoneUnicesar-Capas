package co.unicesar.edu.taller2.gamezoneunicesar.capas.ui;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.service.PersonService;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.service.ProductService;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.service.SaleService;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Customer;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Product;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Sale;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Seller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class responsible for managing the console-based user interface of the
 * GameZone Unicesar application.
 * <p>
 * It is responsible for displaying the different menus (main, products,
 * people, and sales), capturing user input through a {@link Scanner}, and
 * delegating the business logic to the corresponding services
 * ({@link ProductService}, {@link PersonService}, and {@link SaleService}).
 */
public class UI {

    /** Object used to read user input from the console. */
    private final Scanner scanner;

    /** Service responsible for the business logic related to sales. */
    private final SaleService saleService;

    /** Service responsible for the business logic related to people (customers and sellers). */
    private final PersonService personService;

    /** Service responsible for the business logic related to products. */
    private final ProductService productService;

    /**
     * Creates a new instance of the console user interface.
     *
     * @param scanner        object used to read user input
     * @param saleService    service for managing sales
     * @param personService  service for managing people (customers and sellers)
     * @param productService service for managing products
     */
    public UI(Scanner scanner,
            SaleService saleService,
            PersonService personService,
            ProductService productService) {
        this.scanner = scanner;
        this.saleService = saleService;
        this.personService = personService;
        this.productService = productService;
    }

    /**
     * Starts the execution of the application by showing the main menu.
     * <p>
     * Remains in a loop until the user selects the exit option, redirecting
     * the user to the different submenus depending on the chosen option.
     */
    public void launch() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n========== GAMEZONE UNICESAR - MENU PRINCIPAL ==========");
            System.out.println("1. Gestion de productos");
            System.out.println("2. Gestion de personas");
            System.out.println("3. Gestion de ventas");
            System.out.println("0. Salir de la aplicacion");
            System.out.print("Seleccione una opcion: ");
            String option = scanner.nextLine().trim();

            switch (option) {
                case "1" ->
                    productMenu();
                case "2" ->
                    personMenu();
                case "3" ->
                    saleMenu();
                case "0" -> {
                    exit = true;
                    System.out.println("\n¡Gracias por usar GameZone Unicesar! Saliendo...");
                }
                default ->
                    System.out.println("Opcion invalida. Por favor, intente de nuevo.");
            }
        }
    }
    
    /**
     * Displays the product management menu and processes the option
     * selected by the user (register video game, register console, list
     * products, or return to the main menu).
     */
    public void productMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n========== MENU DE PRODUCTOS ==========");
            System.out.println("1. Registrar un nuevo videojuego");
            System.out.println("2. Registrar una nueva consola");
            System.out.println("3. Listar todos los productos disponibles");
            System.out.println("0. Regresar al menu principal");
            System.out.print("Seleccione una opcion: ");
            String option = scanner.nextLine().trim();

            switch (option) {
                case "1" ->
                    registerVideoGame();
                case "2" ->
                    registerConsole();
                case "3" ->
                    listAllProducts();
                case "0" ->
                    back = true;
                default ->
                    System.out.println("Opcion invalida.");
            }
        }
    }
    
    /**
     * Prompts the user for the data of a new video game (ID, title, price,
     * stock quantity, platform, genre, and age rating) and registers it
     * through {@link ProductService}.
     * <p>
     * If the entered price or stock are not valid numbers, or if the
     * service detects any invalid data, the error is reported to the user
     * through the console.
     */
    public void registerVideoGame() {
        System.out.println("\n--- REGISTRAR NUEVO VIDEOJUEGO ---");
        System.out.print("ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Título: ");
        String title = scanner.nextLine().trim();
        System.out.print("Precio: ");
        double price = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Cantidad en stock: ");
        int stockQuantity = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Plataforma: ");
        String platform = scanner.nextLine().trim();
        System.out.print("Género: ");
        String genre = scanner.nextLine().trim();
        System.out.print("Clasificación de edad: ");
        String ageRating = scanner.nextLine().trim();

        try {
            productService.registerVideoGame(id, title, price, stockQuantity, platform, genre, ageRating);
            System.out.println("\n¡Videojuego registrado con éxito!");
        } catch (NumberFormatException e) {
            System.out.println("Error: Por favor, ingrese números válidos para el precio y el stock.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Prompts the user for the data of a new console (ID, title, price,
     * stock quantity, brand, model, and generation) and registers it
     * through {@link ProductService}.
     * <p>
     * If the entered price or stock are not valid numbers, or if the
     * service detects any invalid data, the error is reported to the user
     * through the console.
     */
    public void registerConsole() {
        System.out.println("\n--- REGISTRAR NUEVA CONSOLA ---");
        System.out.print("ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Título: ");
        String title = scanner.nextLine().trim();
        System.out.print("Precio: ");
        double price = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Cantidad en stock: ");
        int stockQuantity = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Marca: ");
        String brand = scanner.nextLine().trim();
        System.out.print("Modelo: ");
        String model = scanner.nextLine().trim();
        System.out.print("Generación: ");
        String generation = scanner.nextLine().trim();

        try {
            productService.registerConsole(id, title, price, stockQuantity, brand, model, generation);
            System.out.println("\n¡Consola registrada con éxito!");
        } catch (NumberFormatException e) {
            System.out.println("Error: Por favor, ingrese números válidos para el precio y el stock.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Retrieves and displays on the console the full list of products
     * registered in the inventory, including their ID, title, price, and
     * stock quantity.
     * <p>
     * If no products are registered, the user is informed and the method
     * execution ends.
     */
    public void listAllProducts() {
        System.out.println("\n--- INVENTARIO DE PRODUCTOS ---");
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No hay productos registrados en el inventario.");
            return;
        }
        for (Product product : products) {
            System.out.println(product.getId() + " - " + product.getTitle()
                    + " | Price: " + product.getPrice()
                    + " | Stock: " + product.getStockQuantity());
            System.out.println("---------------------------");
        }
    }
    
    /**
     * Displays the people management menu and processes the option
     * selected by the user (register customer, list customers, list
     * sellers, or return to the main menu).
     */
    public void personMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n========== MENU DE PERSONAS ==========");
            System.out.println("1. Registrar un nuevo cliente");
            System.out.println("2. Listar todos los clientes registrados");
            System.out.println("3. Listar todos los vendedores registrados");
            System.out.println("0. Regresar al menu principal");
            System.out.print("Seleccione una opcion: ");
            String option = scanner.nextLine().trim();

            switch (option) {
                case "1" ->
                    registerCustomer();
                case "2" ->
                    listAllCustomers();
                case "3" ->
                    listAllSellers();
                case "0" ->
                    back = true;
                default ->
                    System.out.println("Opcion invalida.");
            }
        }
    }
    
    /**
     * Prompts the user for the data of a new customer (ID, name, phone
     * number, and email address) and registers it through
     * {@link PersonService}.
     * <p>
     * If the service detects any invalid data, the error is reported to
     * the user through the console.
     */
    public void registerCustomer() {
        System.out.println("\n--- REGISTRAR NUEVO CLIENTE ---");
        System.out.print("ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Nombre: ");
        String name = scanner.nextLine().trim();
        System.out.print("Teléfono: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Correo electrónico: ");
        String email = scanner.nextLine().trim();

        try {
            personService.registerCustomer(id, name, phone, email);
            System.out.println("\n¡Cliente registrado con éxito!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Retrieves and displays on the console the full list of registered
     * customers, including their ID, name, phone number, and email
     * address.
     * <p>
     * If no customers are registered, the user is informed and the method
     * execution ends.
     */
    public void listAllCustomers() {
        System.out.println("\n--- CLIENTES REGISTRADOS ---");
        List<Customer> customers = personService.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        for (Customer customer : customers) {
            System.out.println(customer.getId() + " - " + customer.getName()
                    + " | Teléfono: " + customer.getPhone()
                    + " | Correo: " + customer.getEmail());
            System.out.println("---------------------------");
        }
    }
    
    /**
     * Retrieves and displays on the console the full list of registered
     * sellers, including their ID, name, work shift, and employee code.
     * <p>
     * If no sellers are registered, the user is informed and the method
     * execution ends.
     */
    public void listAllSellers() {
        System.out.println("\n--- VENDEDORES REGISTRADOS ---");
        List<Seller> sellers = personService.getAllSellers();
        if (sellers.isEmpty()) {
            System.out.println("No hay vendedores registrados.");
            return;
        }
        for (Seller seller : sellers) {
            System.out.println(seller.getId() + " - " + seller.getName()
                    + " | Turno: " + seller.getWorkShift()
                    + " | Código Empleado: " + seller.getEmployeeCode());
            System.out.println("---------------------------");
        }
    }
    
    /**
     * Displays the sales management menu and processes the option
     * selected by the user (register sale, list all sales, view sales
     * history by customer, view sales history by seller, or return to the
     * main menu).
     */
    public void saleMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n========== MENU DE VENTAS==========");
            System.out.println("1. Registrar una nueva venta");
            System.out.println("2. Listar todas las ventas");
            System.out.println("3. Historial de ventas por cliente");
            System.out.println("4. Historial de ventas por vendedor");
            System.out.println("0. Regresar al menu principal");
            System.out.print("Seleccione una opcion: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1" -> registerSale();
                case "2" -> listAllSales();
                case "3" -> salesByClient();
                case "4" -> salesBySeller();
                case "0" -> back = true;
                default -> System.out.println("Opcion invalida.");
            }
        }
    }
    
    /**
     * Guides the user through the process of registering a new sale.
     * <p>
     * Displays the available customers, sellers, and products, prompts
     * for the customer ID, the seller ID, and the product IDs (separated
     * by commas) involved in the sale, and delegates the registration to
     * {@link SaleService}.
     * <p>
     * If there are no customers, sellers, or products available, the user
     * is informed and the registration is cancelled. If the service
     * detects any invalid data, the error is reported through the
     * console.
     */
    public void registerSale() {
        System.out.println("\n--- REGISTRAR NUEVA VENTA ---");
        
        System.out.println("\nClientes registrados:");
        List<Customer> clients = personService.getAllCustomers();
        if (clients.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        for (Customer client : clients) {
            System.out.println(client.getId() + " - " + client.getName());
        }
        System.out.print("ID del cliente: ");
        String clientId = scanner.nextLine().trim();
        
        System.out.println("\nVendedores registrados:");
        List<Seller> sellers = personService.getAllSellers();
        if (sellers.isEmpty()) {
            System.out.println("No hay vendedores registrados.");
            return;
        }
        for (Seller seller : sellers) {
            System.out.println(seller.getId() + " - " + seller.getName());
        }
        System.out.print("ID del vendedor: ");
        String sellerId = scanner.nextLine().trim();
        
        System.out.println("\nProductos Disponibles:");
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No hay productos disponibles.");
            return;
        }
        for (Product product : products) {
            System.out.println(product.getId() + " - " + product.getTitle()
                    + " | Price: " + product.getPrice()
                    + " | Stock: " + product.getStockQuantity());
        }
        
        System.out.println("\nIngrese los IDs de los productos separados por coma (ejemplo: P001,P003):");
        System.out.print("Product IDs: ");
        String productsInput = scanner.nextLine().trim();

        List<String> productIds = new ArrayList<>();
        if (!productsInput.isBlank()) {
            String[] parts = productsInput.split(",");
            for (String part : parts) {
                String id = part.trim();
                if (!id.isEmpty()) {
                    productIds.add(id);
                }
            }
        }

        try {
            saleService.registerSale(clientId, sellerId, productIds);
            System.out.println("\n¡Nueva venta registrada exitosamente!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Retrieves and displays on the console the full list of sales
     * registered in the system.
     * <p>
     * If no sales are registered, the user is informed and the method
     * execution ends.
     */
    public void listAllSales() {
        System.out.println("\n--- TODAS LAS VENTAS ---");
        List<Sale> sales = saleService.getAllSales();
        if (sales.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }
        for (Sale sale : sales) {
            System.out.println(sale.printSale());
            System.out.println("---------------------------");
        }
    }
    
    /**
     * Displays the list of registered customers, prompts for a customer
     * ID, and displays on the console the sales history associated with
     * that customer, obtained through {@link SaleService}.
     * <p>
     * If the customer has no registered purchases, the user is informed.
     * If the service detects an invalid ID, the error is reported through
     * the console.
     */
    public void salesByClient() {
        System.out.println("\n--- VENTAS POR CLIENTE ---");
        System.out.println("Clientes registrados:");
        for (Customer customer : personService.getAllCustomers()) {
            System.out.println(customer.getId() + " - " +customer.getName());
        }
        System.out.print("ID del cliente: ");
        String customerId = scanner.nextLine().trim();

        try {
            List<Sale> sales = saleService.getSalesByCustomer(customerId);
            if (sales.isEmpty()) {
                System.out.println("Este cliente no tiene compras.");
                return;
            }
            for (Sale sale : sales) {
                System.out.println(sale.printSale());
                System.out.println("---------------------------");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Displays the list of registered sellers, prompts for a seller ID,
     * and displays on the console the sales history handled by that
     * seller, obtained through {@link SaleService}.
     * <p>
     * If the seller has not handled any sales, the user is informed. If
     * the service detects an invalid ID, the error is reported through
     * the console.
     */
    public void salesBySeller() {
        System.out.println("\n--- VENTAS POR VENDEDOR ---");
        System.out.println("Vendedores registrados:");
        for (Seller seller : personService.getAllSellers()) {
            System.out.println(seller.getId() + " - " + seller.getName());
        }
        System.out.print("ID del vendedor: ");
        String sellerId = scanner.nextLine().trim();

        try {
            List<Sale> sales = saleService.getSalesBySeller(sellerId);
            if (sales.isEmpty()) {
                System.out.println("Este vendedor no tiene ventas atendidas.");
                return;
            }
            for (Sale sale : sales) {
                System.out.println(sale.printSale());
                System.out.println("---------------------------");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


}