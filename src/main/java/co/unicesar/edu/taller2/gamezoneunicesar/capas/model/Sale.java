package co.unicesar.edu.taller2.gamezoneunicesar.capas.model;
 
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Product;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
 
/**
 * Represents a sale made within the GameZone Unicesar system.
 * <p>
 * A sale groups a unique identifier, the date it was made,
 * the customer who made it, the seller who handled it, and the
 * list of products included in the transaction.
 * </p>
 */
public class Sale {
 
    /** Unique identifier of the sale. */
    private String id;
 
    /** Date on which the sale was made. */
    private LocalDate date;
 
    /** Customer making the purchase. */
    private Customer customer;
 
    /** Seller handling the sale. */
    private Seller seller;
 
    /** List of products included in the sale. */
    private List<Product> products;
 
    /**
     * Creates a new {@code Sale} instance with the given data.
     *
     * @param id       unique identifier of the sale
     * @param date     date on which the sale is made
     * @param customer customer associated with the sale
     * @param seller   seller handling the sale
     * @param products initial list of products for the sale; if
     *                 {@code null}, it is initialized as an empty list
     */
    public Sale(String id, LocalDate date, Customer customer, Seller seller, List<Product> products) {
        this.id = id;
        this.date = date;
        this.customer = customer;
        this.seller = seller;
        this.products = products != null ? new ArrayList<>(products) : new ArrayList<>();
    }
 
    /**
     * Gets the identifier of the sale.
     *
     * @return the id of the sale
     */
    public String getId() {
        return id;
    }
 
    /**
     * Gets the date of the sale.
     *
     * @return the date on which the sale was made
     */
    public LocalDate getDate() {
        return date;
    }
 
    /**
     * Gets the customer associated with the sale.
     *
     * @return the customer of the sale
     */
    public Customer getCustomer() {
        return customer;
    }
 
    /**
     * Gets the seller associated with the sale.
     *
     * @return the seller of the sale
     */
    public Seller getSeller() {
        return seller;
    }
 
    /**
     * Gets the list of products in the sale.
     * <p>
     * A copy of the internal list is returned to prevent
     * uncontrolled external modifications.
     * </p>
     *
     * @return a copy of the list of products in the sale
     */
    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }
 
    /**
     * Sets the identifier of the sale.
     *
     * @param id new identifier of the sale
     */
    public void setId(String id) {
        this.id = id;
    }
 
    /**
     * Sets the date of the sale.
     *
     * @param date new date of the sale
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }
 
    /**
     * Sets the customer associated with the sale.
     *
     * @param customer new customer of the sale
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
 
    /**
     * Sets the seller associated with the sale.
     *
     * @param seller new seller of the sale
     */
    public void setSeller(Seller seller) {
        this.seller = seller;
    }
 
    /**
     * Sets the list of products for the sale.
     * <p>
     * A copy of the received list is stored; if it is {@code null},
     * an empty list is assigned.
     * </p>
     *
     * @param products new list of products for the sale
     */
    public void setProducts(List<Product> products) {
        this.products = products != null ? new ArrayList<>(products) : new ArrayList<>();
    }
 
    /**
     * Calculates the total of the sale by adding up the price of all
     * included products.
     *
     * @return the monetary total of the sale
     */
    public double calculateTotal() {
        double total = 0.0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }
 
    /**
     * Adds a product to the sale.
     * <p>
     * If the given product is {@code null}, no action is taken.
     * </p>
     *
     * @param product product to add to the sale
     */
    public void addProduct(Product product) {
        if (product != null) {
            this.products.add(product);
        }
    }
 
    /**
     * Generates a text representation of the sale, including its
     * id, date, customer, seller, products, and total.
     *
     * @return a string with the full detail of the sale
     */
    public String printSale() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sale:\n");
        sb.append("  id: ").append(id).append("\n");
        sb.append("  date: ").append(date).append("\n");
        sb.append("  customer: ").append(customer != null ? customer.getName() : "N/A").append("\n");
        sb.append("  seller: ").append(seller != null ? seller.getName() : "N/A").append("\n");
        sb.append("  products:\n");
        if (products.isEmpty()) {
            sb.append("    (none)\n");
        } else {
            for (Product product : products) {
                sb.append("    - ").append(product.getTitle()).append("\n");
            }
        }
        sb.append("  total: ").append(calculateTotal());
        return sb.toString();
    }
 
}