package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.PersistenceException;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Person;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Customer;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Seller;

import java.io.*;
import java.util.*;

/**
 * Class responsible for persisting Person objects (Customer and Seller)
 * to a plain text file, using a simple CSV-like format.
 * Manages saving (saveAll), loading (loadAll), and searching
 * for persons by ID, and also verifies that the data file exists
 * before using it.
 * <p>
 * Every {@link IOException} raised while accessing the file is caught here and
 * rethrown as a {@link PersistenceException} that includes the file path and the
 * operation being performed.
 * </p>
 */
public class PersonRepository {
    private File file; // Physical file where data is read from / written to

    /**
     * Default constructor: uses a fixed default path
     * ("data/persons.txt") for the persistence file.
     * Internally delegates to the constructor that receives the path.
     *
     * @throws PersistenceException if the data file cannot be created or initialized
     */
    public PersonRepository() {
        this("data/persons.txt");
    }

    /**
     * Constructor: receives the path of the file used to persist data.
     *
     * @param filePath path of the text file
     * @throws PersistenceException if the data file cannot be created or initialized
     */
    public PersonRepository(String filePath) {
        this.file = new File(filePath);
        ensureFileExists();
        initializeDefaultSellers();
    }

    /**
     * Saves a full list of people (customers and sellers) to the file,
     * overwriting its previous content. Each type of person is saved
     * on a line with a different format, identified by the "CUSTOMER"
     * or "SELLER" tag at the beginning.
     *
     * @param persons list of people to save
     * @throws PersistenceException if the file cannot be written
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
            // The original IOException is kept as the cause to preserve traceability
            throw new PersistenceException(
                    "Error writing persons to file '" + file.getPath() + "' (save operation)", e);
        }
    }

    /**
     * Reads the file line by line and rebuilds the list of Person objects,
     * distinguishing between Customer and Seller based on the first column
     * of each line. If the file doesn't exist, returns an empty list instead
     * of failing. Empty lines are skipped.
     *
     * @return list of people loaded from the file
     * @throws PersistenceException if the file cannot be read
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
            throw new PersistenceException(
                    "Error reading persons from file '" + file.getPath() + "' (load operation)", e);
        }

        return persons;
    }

    /**
     * Finds a customer by their id, by iterating over the full list loaded from the file.
     * Note: internally calls loadAll(), so every search re-reads the entire file.
     * Returns null when the customer does not exist; deciding whether that is an
     * error is the responsibility of the service/validator layer.
     *
     * @param id customer's identifier
     * @return the Customer found, or null if it doesn't exist
     * @throws PersistenceException if the file cannot be read
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
     * Returns null when the seller does not exist.
     *
     * @param id seller's identifier
     * @return the Seller found, or null if it doesn't exist
     * @throws PersistenceException if the file cannot be read
     */
    public Seller findSellerById(String id) {
        for (Person person : loadAll()) {
            if (person instanceof Seller && person.getId().equals(id)) {
                return (Seller) person;
            }
        }
        return null;
    }

    /**
     * Ensures that the data file physically exists on disk.
     * If the parent directory doesn't exist, it is created (including any
     * necessary subdirectories). If the file itself doesn't exist, it is
     * created empty.
     *
     * @throws PersistenceException if the directories or the file cannot be created
     */
    private void ensureFileExists() {
        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs(); // Creates all necessary intermediate directories
            }
            if (!file.exists()) {
                file.createNewFile(); // Creates an empty file if it doesn't exist
            }
        } catch (IOException e) {
            throw new PersistenceException(
                    "Error creating persons file '" + file.getPath() + "' (create operation)", e);
        }
    }

    /**
     * Initializes the persistence file with three default sellers
     * when the file does not exist or is empty.
     * This ensures that the application starts with the minimum
     * required number of preloaded sellers.
     *
     * @throws PersistenceException if the default sellers cannot be written
     */
    private void initializeDefaultSellers() {
        if(file.exists() && file.length() > 0) {return;}

        List<Person> defaultSellers = new ArrayList<>();

        defaultSellers.add(
                new Seller(
                        "0001",
                        "Carlos Gomez",
                        "3105456211",
                        "EMP001",
                        "Morning"
                )
        );

        defaultSellers.add(
                new Seller(
                        "0002",
                        "Laura Martinez",
                        "3015114171",
                        "EMP002",
                        "Evening"
                )
        );

        defaultSellers.add(
                new Seller(
                        "0003",
                        "Andres Rodriguez",
                        "3155186270",
                        "EMP003",
                        "Afternoon"
                )
        );
        // We save the pre-set sellers
        saveAll(defaultSellers);
    }
}