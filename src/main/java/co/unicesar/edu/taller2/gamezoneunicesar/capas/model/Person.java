package co.unicesar.edu.taller2.gamezoneunicesar.capas.model;

/**
 * Abstract class representing a generic person in the system.
 * Serves as the base class for the Customer and Seller entities,
 * since both share common attributes such as id, name, and phone.
 * Being abstract, it cannot be instantiated directly.
 */
public abstract class Person {
    private String id;       // Unique identifier of the person
    private String name;     // Full name of the person
    private String phone;    // Contact phone number

    /**
     * Person constructor
     * @param id unique identifier
     * @param name person's name
     * @param phone contact phone number
     */
    public Person(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    /**
    * Getters: allow access to private fields from subclasses
    * or other layers (service, persistence) without exposing the fields directly.
    */
    public String getId() {return id;}
    public String getName() {return name;}
    public String getPhone() {return phone;}
}
