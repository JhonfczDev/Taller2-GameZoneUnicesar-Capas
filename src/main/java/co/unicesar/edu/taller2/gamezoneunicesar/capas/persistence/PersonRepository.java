package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;
import java.io.*;
import java.util.*;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Person;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Customer;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Seller;

/**
 * Class responsible for persisting Person objects (Customer and Seller)
 * to a plain text file, using a simple CSV-like format.
 * Manages saving (saveAll), loading (loadAll), and searching
 * for persons by ID.
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

    /**
     * Reads the file line by line and rebuilds the list of Person objects,
     * distinguishing between Customer and Seller based on the first column
     * of each line. If the file doesn't exist, returns an empty list instead
     * of failing. Empty lines are skipped.
     * @return list of people loaded from the file
     */
    public List<Person> loadAll(){

        List<Person> persons = new ArrayList<>();

        // If the file doesn't exist yet, there's nothing to load
        if (!file.exists()) {
            return persons;

        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;

            while ((line = reader.readLine()) != null) {
                // Skip empty lines (e.g., trailing newlines at the end of the file)
                if (line.trim().isEmpty()) continue;

                // Split the line by commas to get each field
                String[] data = line.split(",");

                if (data[0].equals("CUSTOMER")) {
                    // data[1]=id, data[2]=name, data[3]=phone, data[4]=email
                    Customer customer = new Customer(data[1], data[2], data[3], data[4] );

                    persons.add(customer);

                } else if (data[0].equals("SELLER")) {
                    // data[1]=id, data[2]=name, data[3]=phone, data[4]=employeeCode, data[5]=workShift
                    Seller seller = new Seller(data[1], data[2], data[3], data[4], data[5]);

                    persons.add(seller);
                }
            }
        } catch (IOException e) {
            // Rethrown as an unchecked exception to simplify error handling
            throw new RuntimeException("Error loading persons...", e);
        }

        return persons;
    }

    /**
     * Finds a customer by their id, by iterating over the full list loaded from the file.
     * Note: internally calls loadAll(), so every search re-reads the entire file.
     * @param id customer's identifier
     * @return the Customer found, or null if it doesn't exist
     */
    public Customer findCustomerById(String id) {
        for (Person person : loadAll()) {
            if (person instanceof Customer && person.getId().equals(id)) {
                return (Customer) person;
            }
        }
        return null;
    }

    /**
     * Finds a seller by their id, by iterating over the full list loaded from the file.
     * @param id seller's identifier
     * @return the Seller found, or null if it doesn't exist
     */
    public Seller findSellerById(String id) {
        for (Person person : loadAll()) {
            if (person instanceof Seller && person.getId().equals(id)) {
                return (Seller) person;
            }
        }
        return null;
    }
}