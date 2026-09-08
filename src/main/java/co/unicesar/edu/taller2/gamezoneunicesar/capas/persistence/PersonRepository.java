package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;
import java.io.*;
import java.util.*;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Person;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Customer;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Seller;

/**
 * Class responsible for persisting Person objects (Customer and Seller)
 * to a plain text file, using a simple CSV-like format.
 * Manages saving (saveAll) of persons.
 */
public class PersonRepository {
    private File file; // Physical file where data is read from / written to

    /**
     * Default constructor: uses a fixed default path
     * ("data/persons.txt") for the persistence file.
     * Internally delegates to the constructor that receives the path.
     */
    public PersonRepository() {
        this("data/persons.txt");
    }

    /**
     * Constructor: receives the path of the file used to persist data.
     * @param filePath path of the text file
     */
    public PersonRepository(String filePath) {
        this.file = new File(filePath);
    }

    /**
     * Saves a full list of people (customers and sellers) to the file,
     * overwriting its previous content. Each type of person is saved
     * on a line with a different format, identified by the "CUSTOMER"
     * or "SELLER" tag at the beginning.
     * @param persons list of people to save
     */
    public void saveAll(List<Person> persons){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Person person : persons) {
                // If the person is a Customer, save their data + email
                if (person instanceof Customer) {
                    Customer customer = (Customer) person;
                    String line = "CUSTOMER,"
                            + customer.getId() + ","
                            + customer.getName() + ","
                            + customer.getPhone() + ","
                            + customer.getEmail();

                    writer.write(line);
                    writer.newLine();

                    // If the person is a Seller, save their data + employee code and work shift
                } else if (person instanceof Seller) {
                    Seller seller = (Seller) person;

                    String line = "SELLER,"
                            + seller.getId() + ","
                            + seller.getName() + ","
                            + seller.getPhone() + ","
                            + seller.getEmployeeCode() + ","
                            + seller.getWorkShift();

                    writer.write(line);
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            // Rethrown as an unchecked exception to simplify error handling
            throw new RuntimeException("Error saving persons...", e);
        }
    }
}