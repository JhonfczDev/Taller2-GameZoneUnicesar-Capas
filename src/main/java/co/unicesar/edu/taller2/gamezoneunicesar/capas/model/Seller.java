package co.unicesar.edu.taller2.gamezoneunicesar.capas.model;
/**
 * Represents a seller (employee) in the system. Extends Person and adds
 * information specific to a seller: their employee code and work shift.
 */
public class Seller extends Person {
    private String employeeCode;   // Unique code identifying the seller as an employee
    private String workShift;      // Seller's assigned work shift (e.g., morning, afternoon, night)

    /**
     * Seller constructor.
     * @param employeeCode employee code assigned to the seller
     * @param workShift work shift assigned to the seller
     * @param id unique identifier (inherited from Person)
     * @param name seller's name
     * @param phone seller's phone number
     */
    public Seller(String id, String name, String phone, String employeeCode, String workShift) {
        super(id, name, phone);   // Initializes the common attributes defined in Person
        this.employeeCode = employeeCode;
        this.workShift = workShift;
    }

    /**
     * Getter for the employee code
     */
    public String getEmployeeCode() {return employeeCode;}

    /**
     * Getter for the work shift
     */
    public String getWorkShift() {return workShift;}
}
