package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;
import java.io.File;

/**
 * Class responsible for persisting Person objects (Customer and Seller)
 * to a plain text file, using a simple CSV-like format.
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
}