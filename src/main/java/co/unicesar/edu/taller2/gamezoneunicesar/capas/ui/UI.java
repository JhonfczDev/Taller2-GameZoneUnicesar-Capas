package co.unicesar.edu.taller2.gamezoneunicesar.capas.ui;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.service.PersonService;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.service.ProductService;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.service.SaleService;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.service.AccessoryService;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.service.PromotionService;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Customer;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Accessory;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Controller;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Memory;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Cable;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Product;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Promotion;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Sale;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Seller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

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
    
    private final AccessoryService accessoryService;
    
    private final PromotionService promotionService;

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
            ProductService productService,
            AccessoryService accessoryService,
            PromotionService promotionService) {
        this.scanner = scanner;
        this.saleService = saleService;
        this.personService = personService;
        this.productService = productService;
        this.accessoryService = accessoryService;
        this.promotionService = promotionService;
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
            System.out.println("4. Gestion de accesorios");
            System.out.println("5. Gestion de promociones");
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
                case "4" ->
                    accessoryMenu();
                case "5" ->
                    promotionMenu();
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
    
    public void accessoryMenu(){
        boolean back = false;
        while (!back) {
            System.out.println("\n========== MENU DE ACCESORIOS ==========");
            System.out.println("1. Registrar un nuevo control");
            System.out.println("2. Registrar un nuevo cable");
            System.out.println("3. Registrar una nueva memoria");
            System.out.println("4. Listar todos los accesorios");
            System.out.println("5. Listar accesorios por tipo");
            System.out.println("6. Consultar accesorios compatibles con una consola");
            System.out.println("0. Regresar al menu principal");
            System.out.print("Seleccione una opcion: ");
            String option = scanner.nextLine().trim();

            switch (option) {
                case "1" ->
                    registerController();
                case "2" ->
                    registerCable();
                case "3" ->
                    registerMemory();
                case "4" ->
                    listAllAccessories();
                case "5" ->
                    listAccessoriesByType();
                case "6" ->
                    listAccessoriesCompatibleWithConsole();
                case "0" ->
                    back = true;
                default ->
                    System.out.println("Opcion invalida.");
            }
        }
    }

    public void registerController() {
        System.out.println("\n--- REGISTRAR NUEVO CONTROL ---");
        System.out.print("ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Título: ");
        String title = scanner.nextLine().trim();
        System.out.print("Precio: ");
        double price = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Cantidad en stock: ");
        int stockQuantity = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Tipo de conexión (ej. Inalámbrico/Alámbrico): ");
        String connectionType = scanner.nextLine().trim();
        System.out.print("Ingrese la cantidad de consolas compatibles: ");
        int n = scanner.nextInt();
        List<String> compatibleConsoles = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            System.out.print("Ingrese el id de la consola compatible" + (i + 1) + ": ");
            String console = scanner.nextLine().trim();
            compatibleConsoles.add(console);

        }

        try {
            Controller controller = new Controller(id, title, price, stockQuantity, connectionType);
            accessoryService.registerController(controller);
            controller.setCompatibleConsoles(compatibleConsoles);
            System.out.println("\n¡Control registrado con éxito!");
        } catch (NumberFormatException e) {
            System.out.println("Error: Por favor, ingrese números válidos para el precio y el stock.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void registerCable() {
        System.out.println("\n--- REGISTRAR NUEVO CABLE ---");
        System.out.print("ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Título: ");
        String title = scanner.nextLine().trim();
        System.out.print("Precio: ");
        double price = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Cantidad en stock: ");
        int stockQuantity = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Longitud en metros (ej. 1.5): ");
        double length = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Tipo de conector (ej. HDMI, USB-C): ");
        String connectorType = scanner.nextLine().trim();

        try {
            Cable cable = new Cable(id, title, price, stockQuantity, length, connectorType);
            accessoryService.registerCable(cable);
            System.out.println("\n¡Cable registrado con éxito!");
        } catch (NumberFormatException e) {
            System.out.println("Error: Por favor, ingrese números válidos para el precio, el stock y la longitud.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void registerMemory() {
        System.out.println("\n--- REGISTRAR NUEVA MEMORIA ---");
    System.out.print("ID: ");
    String id = scanner.nextLine().trim();
    System.out.print("Título: ");
    String title = scanner.nextLine().trim();
    System.out.print("Precio: ");
    double price = Double.parseDouble(scanner.nextLine().trim());
    System.out.print("Cantidad en stock: ");
    int stockQuantity = Integer.parseInt(scanner.nextLine().trim());
    System.out.print("Capacidad en GB (ej. 128): ");
    int capacity = Integer.parseInt(scanner.nextLine().trim());
    System.out.print("Tipo de memoria (ej. MicroSD, SD): ");
    String memoryType = scanner.nextLine().trim();
    System.out.print("Este producto tiene compatibilidad con consolas? 1-Si, 2-No : ");
    String text = scanner.nextLine();
    char opt = text.charAt(0);
    List<String> compatibleConsoles = new ArrayList<>();
    
        if (opt == '1') {
            System.out.print("Ingrese la cantidad de consolas compatibles: ");
            int n = Integer.parseInt(scanner.nextLine().trim());

            
            for (int i = 0; i < n; i++) {
                System.out.print("Ingrese el id de la consola compatible" + (i + 1) + ": ");
                String console = scanner.nextLine().trim();
                compatibleConsoles.add(console);

            }

        }
        
        try {
            Memory memory = new Memory(id, title, price, stockQuantity, capacity, memoryType);
            if (opt == '1') {
                memory.setCompatibleConsoles(compatibleConsoles);
            }
            accessoryService.registerMemory(memory);
            if (opt == '1') {
                
            }
        System.out.println("\n¡Memoria registrada con éxito!");
    } catch (NumberFormatException e) {
        System.out.println("Error: Por favor, ingrese números válidos para el precio, el stock y la capacidad.");
    } catch (IllegalArgumentException e) {
        System.out.println("Error: " + e.getMessage());
    }
}
    
    public void listAllAccessories() {
    System.out.println("\n--- INVENTARIO DE ACCESORIOS ---");
    List<Accessory> accessories = accessoryService.getAllAccessories();
    if (accessories.isEmpty()) {
        System.out.println("No hay accesorios registrados en el inventario.");
        return;
    }
    for (Accessory accessory : accessories) {
        System.out.println(accessory.getId() + " - " + accessory.getTitle()
                + " | Price: " + accessory.getPrice()
                + " | Stock: " + accessory.getStockQuantity());
        System.out.println("---------------------------");
    }
}
    
    public void listAccessoriesByType() {
    System.out.println("\n--- LISTAR ACCESORIOS POR TIPO ---");
    System.out.println("1. Controles");
    System.out.println("2. Cables");
    System.out.println("3. Memorias");
    System.out.print("Seleccione el tipo de accesorio: ");
    String option = scanner.nextLine().trim();

    String accessoryType = null;
    switch (option) {
        case "1":
            accessoryType = "Controller";
            break;
        case "2":
            accessoryType = "Cable";
            break;
        case "3":
            accessoryType = "Memory";
            break;
        default:
            accessoryType = null;
            break;
    }

    if (accessoryType == null) {
        System.out.println("Opción inválida.");
        return;
    }

    List<Accessory> accessories = accessoryService.getAccessoriesByType(accessoryType);
    if (accessories.isEmpty()) {
        System.out.println("No hay accesorios registrados para este tipo.");
        return;
    }

    System.out.println("\n--- ACCESORIOS DEL TIPO: " + accessoryType + " ---");
    for (Accessory accessory : accessories) {
        System.out.println(accessory.getId() + " - " + accessory.getTitle()
                + " | Price: " + accessory.getPrice()
                + " | Stock: " + accessory.getStockQuantity());
        System.out.println("---------------------------");
    }
}
    
    public void listAccessoriesCompatibleWithConsole() {
    System.out.println("\n--- ACCESORIOS COMPATIBLES CON CONSOLA ---");
    System.out.print("Ingrese el ID de la consola: ");
    String consoleId = scanner.nextLine().trim();

    if (consoleId.isEmpty()) {
        System.out.println("Error: El ID de la consola no puede estar vacío.");
        return;
    }

    try {
        List<Accessory> accessories = accessoryService.getCompatibleAccessories(consoleId);
        if (accessories.isEmpty()) {
            System.out.println("No se encontraron accesorios compatibles con la consola especificada.");
            return;
        }

        System.out.println("\n--- ACCESORIOS COMPATIBLES CON: " + consoleId + " ---");
        for (Accessory accessory : accessories) {
            System.out.println(accessory.getId() + " - " + accessory.getTitle()
                    + " | Price: " + accessory.getPrice()
                    + " | Stock: " + accessory.getStockQuantity());
            System.out.println("---------------------------");
        }
    } catch (IllegalArgumentException e) {
        System.out.println("Error: " + e.getMessage());
    }
}
    
    public void promotionMenu() {
        int option = 0;
        do {
            System.out.println("\n--- Gestión de Promociones ---");
            System.out.println("1. Registrar nueva promoción por porcentaje");
            System.out.println("2. Registrar nueva promoción por categoría");
            System.out.println("3. Registrar nueva promoción por volumen de compra");
            System.out.println("4. Listar todas las promociones registradas");
            System.out.println("5. Listar solo las promociones vigentes");
            System.out.println("6. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            try {
                option = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada

                switch (option) {
                    case 1 ->
                        registerPercentageDiscountUI();
                    case 2 ->
                        registerCategoryDiscountUI();
                    case 3 ->
                        registerBulkPurchaseDiscountUI();
                    case 4 ->
                        listAllPromotionsUI();
                    case 5 ->
                        listActivePromotionsUI();
                    case 6 ->
                        System.out.println("Volviendo al menú principal...");
                    default ->
                        System.out.println("Opción inválida. Por favor, intente de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("Error: Ingrese un valor numérico válido.");
                scanner.nextLine();
                option = 0;
            }
        } while (option != 6);
    }

    private void registerPercentageDiscountUI() {
        try {
            System.out.println("\n--- Registrar Promoción por Porcentaje ---");
            System.out.print("Ingrese ID de la promoción: ");
            String id = scanner.nextLine();
            System.out.print("Ingrese nombre de la promoción: ");
            String name = scanner.nextLine();
            System.out.print("Ingrese fecha de inicio (AAAA-MM-DD): ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine());
            System.out.print("Ingrese fecha de fin (AAAA-MM-DD): ");
            LocalDate endDate = LocalDate.parse(scanner.nextLine());
            System.out.print("Ingrese el porcentaje de descuento (0 a 100): ");
            double percentage = scanner.nextDouble();
            scanner.nextLine();

            promotionService.registerPercentageDiscount(id, name, startDate, endDate, percentage);
            System.out.println("¡Promoción por porcentaje registrada exitosamente!");
        } catch (Exception e) {
            System.out.println("Error al registrar la promoción: " + e.getMessage());
        }
    }

    private void registerCategoryDiscountUI() {
        try {
            System.out.println("\n--- Registrar Promoción por Categoría ---");
            System.out.print("Ingrese ID de la promoción: ");
            String id = scanner.nextLine();
            System.out.print("Ingrese nombre de la promoción: ");
            String name = scanner.nextLine();
            System.out.print("Ingrese fecha de inicio (AAAA-MM-DD): ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine());
            System.out.print("Ingrese fecha de fin (AAAA-MM-DD): ");
            LocalDate endDate = LocalDate.parse(scanner.nextLine());
            System.out.print("Ingrese el porcentaje de descuento (0 a 100): ");
            double percentage = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Ingrese categoría objetivo (VIDEOGAME / CONSOLE): ");
            String targetCategory = scanner.nextLine();

            promotionService.registerCategoryDiscount(id, name, startDate, endDate, percentage, targetCategory);
            System.out.println("¡Promoción por categoría registrada exitosamente!");
        } catch (Exception e) {
            System.out.println("Error al registrar la promoción: " + e.getMessage());
        }
    }

    private void registerBulkPurchaseDiscountUI() {
        try {
            System.out.println("\n--- Registrar Promoción por Volumen de Compra ---");
            System.out.print("Ingrese ID de la promoción: ");
            String id = scanner.nextLine();
            System.out.print("Ingrese nombre de la promoción: ");
            String name = scanner.nextLine();
            System.out.print("Ingrese fecha de inicio (AAAA-MM-DD): ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine());
            System.out.print("Ingrese fecha de fin (AAAA-MM-DD): ");
            LocalDate endDate = LocalDate.parse(scanner.nextLine());
            System.out.print("Ingrese la cantidad mínima de productos: ");
            int minQuantity = scanner.nextInt();
            System.out.print("Ingrese el porcentaje de descuento (0 a 100): ");
            double percentage = scanner.nextDouble();
            scanner.nextLine();

            promotionService.registerBulkPurchaseDiscount(id, name, startDate, endDate, minQuantity, percentage);
            System.out.println("¡Promoción por volumen registrada exitosamente!");
        } catch (Exception e) {
            System.out.println("Error al registrar la promoción: " + e.getMessage());
        }
    }

    private void listAllPromotionsUI() {
        System.out.println("\n--- Listado de Todas las Promociones ---");
        List<Promotion> promotions = promotionService.listAllPromotions();
        if (promotions.isEmpty()) {
            System.out.println("No hay promociones registradas en el sistema.");
        } else {
            for (Promotion p : promotions) {
                System.out.println("- ID: " + p.getId() + " | Nombre: " + p.getName() + " | Vigencia: " + p.getStartDate() + " al " + p.getEndDate());
            }
        }
    }

    private void listActivePromotionsUI() {
        System.out.println("\n--- Listado de Promociones Vigentes ---");
        List<Promotion> promotions = promotionService.listActivePromotions();
        if (promotions.isEmpty()) {
            System.out.println("No hay promociones vigentes en la fecha actual.");
        } else {
            for (Promotion p : promotions) {
                System.out.println("- ID: " + p.getId() + " | Nombre: " + p.getName() + " | Vigencia: " + p.getStartDate() + " al " + p.getEndDate());
            }
        }
    }
    
    
    
    
    
    


}