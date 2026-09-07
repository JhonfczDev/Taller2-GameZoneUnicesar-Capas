package co.unicesar.edu.taller2.gamezoneunicesar.capas.service;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Customer;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Product;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Sale;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Seller;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.SaleRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SaleService {
    
    private final SaleRepository saleRepository;
    private final ProductService productService;
    private final PersonService personService;

    public SaleService(SaleRepository saleRepository,
                       ProductService productService,
                       PersonService personService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.personService = personService;
    }
    
    public void registerSale(String customerId, String sellerId, List<String> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            throw new IllegalArgumentException("Una venta tiene que tener por lo menos un producto.");
        }
        
        Customer customer = personService.findCustomerById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("No se encontro cliente con el id: " + customerId);
        }
        
        Seller seller = personService.findSellerById(sellerId);
        if (seller == null) {
            throw new IllegalArgumentException("No se encontro vendedor con el id: " + sellerId);
        }
        
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Product product = productService.findById(productId);
            if (product == null) {
                throw new IllegalArgumentException("Product not found with id: " + productId);
            }
            if (product.getStockQuantity() <= 0) {
                throw new IllegalArgumentException(
                        "Stock insuficiente para el producto: " + product.getTitle());
            }
            products.add(product);
        }
        
        String saleId = generateSaleId();
        Sale sale = new Sale(saleId, LocalDate.now(), customer, seller, products);
        
        for (Product product : products) {
            product.setStockQuantity(product.getStockQuantity() - 1);
            productService.update(product);
        }
        
        saleRepository.save(sale);
        
    }
    
    public List<Sale> getSalesByCustomer(String customerId) {
        Customer customer = personService.findCustomerById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("No se encontro cliente con el id: " + customerId);
        }

        List<Sale> result = new ArrayList<>();
        for (String line : saleRepository.findAllLines()) {
            if (line.isBlank()) continue;

            String[] fields = line.split(";", -1);
            if (fields.length > 2 && fields[2].equals(customerId)) {
                Sale sale = buildSaleFromLine(line);
                if (sale != null) {
                    result.add(sale);
                }
            }
        }
        return result;
    }
    
    public List<Sale> getAllSales() {
        List<Sale> sales = new ArrayList<>();

        for (String line : saleRepository.findAllLines()) {
            if (line.isBlank()) continue;

            Sale sale = buildSaleFromLine(line);
            if (sale != null) {
                sales.add(sale);
            }
        }
        return sales;
    }
    
    private Sale buildSaleFromLine(String line) {
        try {
            String[] fields = line.split(";", -1);

            String id = fields[0];
            LocalDate date = LocalDate.parse(fields[1]);
            String customerId = fields[2];
            String sellerId = fields[3];
            String productsField = fields.length > 4 ? fields[4] : "";

            List<String> productIds = new ArrayList<>();
            if (!productsField.isBlank()) {
                for (String pid : productsField.split(",")) {
                    productIds.add(pid.trim());
                }
            }

            return buildSale(id, date, customerId, sellerId, productIds);

        } catch (Exception e) {
            System.err.println("Error al procesar la linea de venta: " + line + " → " + e.getMessage());
            return null;
        }
    }
    
    private Sale buildSale(String id, LocalDate date, String customerId,
                           String sellerId, List<String> productIds) {

        Customer customer = personService.findCustomerById(customerId);
        Seller seller = personService.findSellerById(sellerId);

        if (customer == null || seller == null) {
            return null;
        }

        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Product product = productService.findById(productId);
            if (product != null) {
                products.add(product);
            }
        }

        return new Sale(id, date, customer, seller, products);
    }
    
    private String generateSaleId() {
        return "S" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    
    
    
}
