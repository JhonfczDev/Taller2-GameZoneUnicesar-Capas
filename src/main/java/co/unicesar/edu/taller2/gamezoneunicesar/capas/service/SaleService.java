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
 
/**
 * Provides the business logic for managing {@link Sale} records.
 * <p>
 * This service coordinates sale registration and retrieval by
 * delegating persistence to {@link SaleRepository}, product lookups
 * and stock updates to {@link ProductService}, and customer/seller
 * lookups to {@link PersonService}.
 * </p>
 */
public class SaleService {
 
    /** Repository used to persist and retrieve raw sale data. */
    private final SaleRepository saleRepository;
 
    /** Service used to look up and update products. */
    private final ProductService productService;
 
    /** Service used to look up customers and sellers. */
    private final PersonService personService;
 
    /**
     * Creates a new {@code SaleService} with the given dependencies.
     *
     * @param saleRepository repository used to persist and retrieve sales
     * @param productService service used to look up and update products
     * @param personService  service used to look up customers and sellers
     */
    public SaleService(SaleRepository saleRepository,
                       ProductService productService,
                       PersonService personService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.personService = personService;
    }
 
    /**
     * Registers a new sale for the given customer, seller, and products.
     * <p>
     * Validates that at least one product id is provided, that the
     * customer and seller exist, and that every product exists and
     * has available stock. On success, decreases the stock of each
     * involved product by one, persists the resulting sale, and
     * generates a new unique sale id.
     * </p>
     *
     * @param customerId id of the customer making the purchase
     * @param sellerId   id of the seller handling the sale
     * @param productIds ids of the products included in the sale;
     *                   must contain at least one element
     * @throws IllegalArgumentException if {@code productIds} is
     *                                  {@code null} or empty, if the
     *                                  customer or seller cannot be
     *                                  found, if any product cannot
     *                                  be found, or if any product
     *                                  has insufficient stock
     */
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
 
    /**
     * Retrieves all sales handled by the given seller.
     *
     * @param sellerId id of the seller whose sales should be retrieved
     * @return the list of sales handled by the seller; empty if none
     *         are found
     * @throws IllegalArgumentException if the seller cannot be found
     */
    public List<Sale> getSalesBySeller(String sellerId) {
        Seller seller = personService.findSellerById(sellerId);
        if (seller == null) {
            throw new IllegalArgumentException("No se encontro vendedor con el id: " + sellerId);
        }
 
        List<Sale> result = new ArrayList<>();
        for (String line : saleRepository.findAllLines()) {
            if (line.isBlank()) continue;
 
            String[] fields = line.split(";", -1);
            if (fields.length > 3 && fields[3].equals(sellerId)) {
                Sale sale = buildSaleFromLine(line);
                if (sale != null) {
                    result.add(sale);
                }
            }
        }
        return result;
    }
 
    /**
     * Retrieves all sales made by the given customer.
     *
     * @param customerId id of the customer whose sales should be retrieved
     * @return the list of sales made by the customer; empty if none
     *         are found
     * @throws IllegalArgumentException if the customer cannot be found
     */
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
 
    /**
     * Retrieves every sale currently stored.
     *
     * @return the list of all persisted sales; empty if none are stored
     */
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
 
    /**
     * Parses a raw storage line and builds the corresponding
     * {@link Sale}, resolving customer, seller, and product
     * references along the way.
     * <p>
     * If the line is malformed or an error occurs while parsing it,
     * the error is logged to the standard error stream and
     * {@code null} is returned instead of throwing.
     * </p>
     *
     * @param line the raw line read from the sale storage
     * @return the reconstructed sale, or {@code null} if the line
     *         could not be parsed
     */
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
 
    /**
     * Builds a {@link Sale} from its already-parsed components,
     * resolving the customer, seller, and products by their ids.
     * <p>
     * Product ids that cannot be resolved to an existing product are
     * silently skipped.
     * </p>
     *
     * @param id         id of the sale
     * @param date       date of the sale
     * @param customerId id of the customer to resolve
     * @param sellerId   id of the seller to resolve
     * @param productIds ids of the products to resolve
     * @return the built sale, or {@code null} if the customer or
     *         seller could not be resolved
     */
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
 
    /**
     * Generates a new unique identifier for a sale.
     *
     * @return a new sale id, prefixed with {@code "S"} followed by
     *         eight uppercase hexadecimal characters
     */
    private String generateSaleId() {
        return "S" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    
    
    
}