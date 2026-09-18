package co.unicesar.edu.taller2.gamezoneunicesar.capas.model;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer in the system. Extends Person and adds
 * information specific to a customer: email address and the
 * history of purchases (sales) they have made.
 */
public class Customer extends Person {
    private String email;                  // Customer's email address
    private List<Sale> purchaseHistory;    // List of sales associated with this customer

    /**
     * Customer constructor.
     * @param id unique identifier (inherited from Person)
     * @param name customer's name
     * @param phone customer's phone number
     * @param email customer's email address
     */
    public Customer(String id, String name, String phone, String email) {
        super(id, name, phone); // Initializes the common attributes defined in Person
        this.email = email;
        this.purchaseHistory = new ArrayList<>(); // Starts empty when the customer is created
    }

    /**
     * Email getter
     */
    public String getEmail() {return email;}

    /**
     * Returns the customer's full purchase history.
     */
    public List<Sale> getPurchaseHistory() {
        return purchaseHistory;
    }

    /**
     * Adds a new sale (Sale) to the customer's purchase history.
     * @param sale the sale made by the customer
     */
    public void addPurchase(Sale sale) {
        purchaseHistory.add(sale);
    }
}
