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
 * <p>
 * How each type of person is written to and rebuilt from a line is delegated to
 * its own {@link LineSerializer}.
 * </p>
 */
public class PersonRepository implements PersonStorage {

    /** Serializers of the types of person that can be stored in the file. */
    private static final SerializerRegistry<Person> SERIALIZERS = new SerializerRegistry<Person>()
            .register(new CustomerSerializer())
            .register(new SellerSerializer());

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
     * overwriting its previous content. Each person is written by the serializer
     * of its type, which also writes the tag at the beginning of the line.
     *
     * @param persons list of people to save
     * @throws PersistenceException if the file cannot be written
     */
    @Override
    public void saveAll(List<Person> persons){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Person person : persons) {
                LineSerializer<Person> serializer = SERIALIZERS.findByItem(person);

                if (serializer != null) {
                    writer.write(serializer.toLine(person));
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
     * using the serializer that matches the tag in the first column of each
     * line. If the file doesn't exist, returns an empty list instead
     * of failing. Empty lines are skipped.
     *
     * @return list of people loaded from the file
     * @throws PersistenceException if the file cannot be read
     */
    @Override
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

                LineSerializer<Person> serializer = SERIALIZERS.findByTag(data[0]);

                if (serializer != null) {
                    persons.add(serializer.fromFields(data));
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
    @Override
    public Customer findCustomerById(String id) {
        return findById(id, Customer.class);
    }

    /**
     * Finds a seller by their id, by iterating over the full list loaded from the file.
     * Returns null when the seller does not exist.
     *
     * @param id seller's identifier
     * @return the Seller found, or null if it doesn't exist
     * @throws PersistenceException if the file cannot be read
     */
    @Override
    public Seller findSellerById(String id) {
        return findById(id, Seller.class);
    }

    /**
     * Finds a person of the given type by id.
     *
     * @param id   person's identifier
     * @param type type of person to look for
     * @return the person found, or null if it doesn't exist
     */
    private <T extends Person> T findById(String id, Class<T> type) {
        for (Person person : loadAll()) {
            if (type.isInstance(person) && person.getId().equals(id)) {
                return type.cast(person);
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