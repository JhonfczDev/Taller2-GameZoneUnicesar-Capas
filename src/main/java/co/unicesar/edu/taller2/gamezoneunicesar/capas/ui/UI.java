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

public class UI {

    private final Scanner scanner;
    private final SaleService saleService;
    private final PersonService personService;
    private final ProductService productService;

    public ConsoleUI(Scanner scanner,
            SaleService saleService,
            PersonService personService,
            ProductService productService) {
        this.scanner = scanner;
        this.saleService = saleService;
        this.personService = personService;
        this.productService = productService;
    }

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


}
