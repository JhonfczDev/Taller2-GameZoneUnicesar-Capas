package co.unicesar.edu.taller2.gamezoneunicesar.capas.ui;

import service.PersonService;
import service.ProductService;
import service.SaleService;
import model.Customer;
import model.Product;
import model.Sale;
import model.Seller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase encargada de gestionar la interfaz de usuario por consola de la
 * aplicación GameZone Unicesar.
 * <p>
 * Se encarga de mostrar los distintos menús (principal, productos, personas
 * y ventas), capturar la entrada del usuario a través de un {@link Scanner}
 * y delegar la lógica de negocio a los servicios correspondientes
 * ({@link ProductService}, {@link PersonService} y {@link SaleService}).
 */
public class UI {

    /** Objeto utilizado para leer la entrada del usuario desde la consola. */
    private final Scanner scanner;

    /** Servicio encargado de la lógica de negocio relacionada con las ventas. */
    private final SaleService saleService;

    /** Servicio encargado de la lógica de negocio relacionada con las personas (clientes y vendedores). */
    private final PersonService personService;

    /** Servicio encargado de la lógica de negocio relacionada con los productos. */
    private final ProductService productService;

    /**
     * Crea una nueva instancia de la interfaz de usuario por consola.
     *
     * @param scanner        objeto utilizado para leer la entrada del usuario
     * @param saleService    servicio de gestión de ventas
     * @param personService  servicio de gestión de personas (clientes y vendedores)
     * @param productService servicio de gestión de productos
     */
    public ConsoleUI(Scanner scanner,
            SaleService saleService,
            PersonService personService,
            ProductService productService) {
        this.scanner = scanner;
        this.saleService = saleService;
        this.personService = personService;
        this.productService = productService;
    }

    /**
     * Inicia la ejecución de la aplicación mostrando el menú principal.
     * <p>
     * Permanece en un ciclo hasta que el usuario seleccione la opción de
     * salir, redirigiendo al usuario a los distintos submenús según la
     * opción elegida.
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
     * Muestra el menú de gestión de productos y procesa la opción
     * seleccionada por el usuario (registrar videojuego, registrar consola,
     * listar productos o regresar al menú principal).
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
     * Solicita al usuario los datos de un nuevo videojuego (ID, título,
     * precio, cantidad en stock, plataforma, género y clasificación de
     * edad) y lo registra a través de {@link ProductService}.
     * <p>
     * Si el precio o el stock ingresados no son numéricos válidos, o si el
     * servicio detecta algún dato inválido, se informa el error al usuario
     * por consola.
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
     * Solicita al usuario los datos de una nueva consola (ID, título,
     * precio, cantidad en stock, marca, modelo y generación) y la registra
     * a través de {@link ProductService}.
     * <p>
     * Si el precio o el stock ingresados no son numéricos válidos, o si el
     * servicio detecta algún dato inválido, se informa el error al usuario
     * por consola.
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
     * Obtiene y muestra por consola el listado completo de productos
     * registrados en el inventario, incluyendo su ID, título, precio y
     * cantidad en stock.
     * <p>
     * Si no hay productos registrados, se informa al usuario y se termina
     * la ejecución del método.
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
     * Muestra el menú de gestión de personas y procesa la opción
     * seleccionada por el usuario (registrar cliente, listar clientes,
     * listar vendedores o regresar al menú principal).
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
     * Solicita al usuario los datos de un nuevo cliente (ID, nombre,
     * teléfono y correo electrónico) y lo registra a través de
     * {@link PersonService}.
     * <p>
     * Si el servicio detecta algún dato inválido, se informa el error al
     * usuario por consola.
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
     * Obtiene y muestra por consola el listado completo de clientes
     * registrados, incluyendo su ID, nombre, teléfono y correo electrónico.
     * <p>
     * Si no hay clientes registrados, se informa al usuario y se termina
     * la ejecución del método.
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
     * Obtiene y muestra por consola el listado completo de vendedores
     * registrados, incluyendo su ID, nombre, turno de trabajo y código de
     * empleado.
     * <p>
     * Si no hay vendedores registrados, se informa al usuario y se termina
     * la ejecución del método.
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
     * Muestra el menú de gestión de ventas y procesa la opción
     * seleccionada por el usuario (registrar venta, listar todas las
     * ventas, consultar historial por cliente, consultar historial por
     * vendedor o regresar al menú principal).
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
     * Guía al usuario a través del proceso de registro de una nueva venta.
     * <p>
     * Muestra los clientes, vendedores y productos disponibles, solicita
     * el ID del cliente, el ID del vendedor y los IDs de los productos
     * (separados por coma) involucrados en la venta, y delega el registro
     * a {@link SaleService}.
     * <p>
     * Si no hay clientes, vendedores o productos disponibles, se informa
     * al usuario y se cancela el registro. Si el servicio detecta algún
     * dato inválido, se informa el error por consola.
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
     * Obtiene y muestra por consola el listado completo de ventas
     * registradas en el sistema.
     * <p>
     * Si no hay ventas registradas, se informa al usuario y se termina la
     * ejecución del método.
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
     * Muestra el listado de clientes registrados, solicita el ID de un
     * cliente y muestra por consola el historial de ventas asociado a
     * dicho cliente, obtenido a través de {@link SaleService}.
     * <p>
     * Si el cliente no tiene compras registradas, se informa al usuario.
     * Si el servicio detecta un ID inválido, se informa el error por
     * consola.
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
     * Muestra el listado de vendedores registrados, solicita el ID de un
     * vendedor y muestra por consola el historial de ventas atendidas por
     * dicho vendedor, obtenido a través de {@link SaleService}.
     * <p>
     * Si el vendedor no tiene ventas atendidas, se informa al usuario. Si
     * el servicio detecta un ID inválido, se informa el error por
     * consola.
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