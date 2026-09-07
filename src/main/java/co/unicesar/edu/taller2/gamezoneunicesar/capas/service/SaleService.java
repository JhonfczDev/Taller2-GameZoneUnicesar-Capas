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
            throw new IllegalArgumentException("A sale must contain at least one product.");
        }
        
        Customer customer = personService.findCustomerById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found with id: " + customerId);
        }
        
        Seller seller = personService.findSellerById(sellerId);
        if (seller == null) {
            throw new IllegalArgumentException("Seller not found with id: " + sellerId);
        }
        
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Product product = productService.findById(productId);
            if (product == null) {
                throw new IllegalArgumentException("Product not found with id: " + productId);
            }
            if (product.getStockQuantity() <= 0) {
                throw new IllegalArgumentException(
                        "Insufficient stock for product: " + product.getTitle());
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
    
    private String generateSaleId() {
        return "S" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    
    
    
}
