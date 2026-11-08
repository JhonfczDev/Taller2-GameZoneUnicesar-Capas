package co.unicesar.edu.taller2.gamezoneunicesar.capas.model;

/**
 * Represents a customer in the system. Extends Person and adds
 * information specific to a customer: their email address.
 */
public class Customer extends Person {
    private String email; // Customer's email address

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
    }

    /**
     * Email getter
     */
    public String getEmail() {return email;}
}
