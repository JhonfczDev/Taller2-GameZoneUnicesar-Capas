package co.unicesar.edu.taller2.gamezoneunicesar.capas.service;
 
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Customer;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Product;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Sale;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Console;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Seller;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Accessory;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Promotion;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.SaleRepository;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.validations.PersonValidator;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.validations.ProductValidator;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.validations.SaleValidator;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.*;

/**
 * Service responsible for the business logic of the sales module.
 * <p>
 * All the checks that used to be written inline with
 * {@code throw new IllegalArgumentException(...)} now live in the validators
 * ({@link PersonValidator}, {@link ProductValidator}, {@link SaleValidator}),
 * which throw the typed excepcions of the custom hierarchy. This class only
 * orchestrates the flow of the operation.
 * </p>
 */
public class SaleService {
 
    /** Repository used to persist and retrieve raw sale data. */
    private static SaleRepository saleRepository = null;
 
    /** Service used to look up and update products. */
    private static ProductService productService = null;
 
    /** Service used to look up customers and sellers. */
    private static PersonService personService = null;
    
    /** Service used to look up and update accessories. */
    private static AccessoryService accessoryService = null;

    /** Service used to evaluate and calculate applicable promotions. */
    private final PromotionService promotionService;
    
    private final WarrantyService warrantyService;

    /**
     * Creates a new {@code SaleService} with the given dependencies.
     *
     * @param saleRepository   repository used to persist and retrieve sales
     * @param productService   service used to look up and update products
     * @param personService    service used to look up customers and sellers
     * @param accessoryService service used to look up and update accessories
     * @param promotionService service used to evaluate and calculate promotions
     * @param warrantyService  service used to create warranties
     */
    public SaleService(SaleRepository saleRepository,
                       ProductService productService,
                       PersonService personService,
                       AccessoryService accessoryService,
                       PromotionService promotionService,
                       WarrantyService warrantyService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.personService = personService;
        this.accessoryService = accessoryService;
        this.promotionService = promotionService;
        this.warrantyService = warrantyService;
    }

    /**
     * Registers a new sale for the given customer, seller, and products.
     * <p>
     * Validates that the customer and seller exist, that every product exists
     * and has available stock, and that the sale has at least one product.
     * On success, evaluates any applicable promotions, creates the warranties
     * (automatic basic warranty for consoles, extended warranty when requested),
     * decreases the stock of each involved product by one, persists the
     * resulting sale, and generates a new unique sale id.
     * </p>
     *
     * @param customerId                   id of the customer making the purchase
     * @param sellerId                     id of the seller handling the sale
     * @param productIds                   ids of the products included in the sale;
     *                                     a {@code null} or empty list is rejected
     * @param productIdsWithExtendedWarranty ids of the products that get an extended
     *                                     warranty; {@code null} or empty means none
     * @throws co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.ResourceNotFoundExcepcion
     *         if the customer, the seller or any product cannot be found
     * @throws co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.BussinesRuleException
     *         if any product has insufficient stock or the sale has no products
     * @throws co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.PersistenceException
     *         if a data file cannot be read or written
     */
    public void registerSale(String customerId, String sellerId, List<String> productIds, List<String> productIdsWithExtendedWarranty)
            throws FileNotFoundException {
        // The lookups keep returning null; the validators turn "null" into ResourceNotFoundException
        Customer customer = personService.findCustomerById(customerId);
        PersonValidator.validatePersonExist(customer, customerId);

        Seller seller = personService.findSellerById(sellerId);
        PersonValidator.validatePersonExist(seller, sellerId);

        // A null list is treated like an empty one so the sale validator rejects both cases
        List<String> idsToProcess = (productIds != null) ? productIds : new ArrayList<>();

        List<Product> products = new ArrayList<>();
        for (String productId : idsToProcess) {
            Product product = productService.findById(productId);
            if (product == null) {
                // Accessories are stored separately, so they are searched as a fallback
                product = (Product) accessoryService.findById(productId);
            }

            // The product (or accessory) must exist...
            ProductValidator.validateProductExist(product, productId);
            // ...and have stock for the one unit that each sale line consumes
            ProductValidator.validateStockAvailability(product, 1);

            products.add(product);
        }

        // Final check over the wholesale: customer, seller and a non-empty product list
        SaleValidator.validateSaleData(customer, seller, products);

        String saleId = generateSaleId();
        Sale sale = new Sale(saleId, LocalDate.now(), customer, seller, products);

        // Promotions: only the best one is applied, and only if it gives a real discount
        Promotion bestPromotion = promotionService.findBestPromotionFor(sale);
        if (bestPromotion != null) {
            double discount = bestPromotion.calculateDiscount(sale);
            if (discount > 0) {
                sale.setAppliedPromotionName(bestPromotion.getName());
                sale.setDiscountAmount(discount);
            }
        }

        // Warranties: basic for every console, extended only for the products requested
        for (Product product : products) {
            if (product instanceof Console) {
                warrantyService.assignBasicWarranty(product, sale, sale.getDate());
            }
            
            if (productIdsWithExtendedWarranty != null && productIdsWithExtendedWarranty.contains(product.getId())) {
                warrantyService.assignExtendedWarranty(product, sale, sale.getDate());
            }
        }

        // Business behavior kept as it was: one unit of stock is consumed per product
        for (Product product : products) {
            product.setStockQuantity(product.getStockQuantity() - 1);
            if (product instanceof Accessory) {
                accessoryService.update((Accessory) product);
            } else {
                productService.update(product);
            }
        }

        saleRepository.save(sale);
    }

    /**
     * Retrieves all sales handled by the given seller.
     *
     * @param sellerId id of the seller whose sales should be retrieved
     * @return the list of sales handled by the seller; empty if none
     *         are found
     * @throws co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.ResourceNotFoundExcepcion
     *         if the seller cannot be found
     * @throws co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.PersistenceException
     *         if a data file cannot be read
     */
    public List<Sale> getSalesBySeller(String sellerId) {
        Seller seller = personService.findSellerById(sellerId);
        PersonValidator.validatePersonExist(seller, sellerId);

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
     * @throws co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.ResourceNotFoundExcepcion
     *         if the customer cannot be found
     * @throws co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.PersistenceException
     *         if a data file cannot be read
     */
    public List<Sale> getSalesByCustomer(String customerId) {
        Customer customer = personService.findCustomerById(customerId);
        PersonValidator.validatePersonExist(customer, customerId);

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
     * @throws co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.PersistenceException
     *         if a data file cannot be read
     */
    public static List<Sale> getAllSales() {
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
     * This is a tolerant reader, not a validation: if the line is malformed
     * or an error occurs while parsing it, the error is logged to the
     * standard error stream and {@code null} is returned instead of throwing.
     * That behavior is intentionally unchanged by the exception refactor.
     * </p>
     *
     * @param line the raw line read from the sale storage
     * @return the reconstructed sale, or {@code null} if the line
     *         could not be parsed
     */
    private static Sale buildSaleFromLine(String line) {
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
    private static Sale buildSale(String id, LocalDate date, String customerId,
                                  String sellerId, List<String> productIds) {

        Customer customer = personService.findCustomerById(customerId);
        Seller seller = personService.findSellerById(sellerId);

        if (customer == null || seller == null) {
            return null;
        }

        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Product product = productService.findById(productId);
            if (product == null) {
                product = (Product) accessoryService.findById(productId);
            }

            if (product != null) {
                products.add(product);
            }
        }

        return new Sale(id, date, customer, seller, products);
    }

    /**
     * Finds a stored sale by its id.
     * <p>
     * Returns {@code null} when the sale does not exist; callers that must treat
     * a missing sale as an error call
     * {@link SaleValidator#validateSaleExist(Sale, String)}.
     * </p>
     *
     * @param saleId id of the sale to find
     * @return the sale found, or {@code null} if it does not exist
     * @throws co.unicesar.edu.taller2.gamezoneunicesar.capas.exceptions.PersistenceException
     *         if a data file cannot be read
     */
    public static Sale findById(String saleId) {
        for (String line : saleRepository.findAllLines()) {
            if (line.isBlank()) {
                continue;
            }

            String[] fields = line.split(";", -1);

            if (fields.length > 0 && fields[0].equals(saleId)) {
                return buildSaleFromLine(line);
            }
        }

        return null;
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