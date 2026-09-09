package co.unicesar.edu.taller2.gamezoneunicesar.capas.service;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.PersonRepository;

/**
 * Service layer that acts as an intermediary between the business logic
 * and the persistence repository (PersonRepository).
 */
public class PersonService {
    private PersonRepository repository; // Repository used to read/write data

    /**
     * Constructor: receives the repository to use (dependency injection).
     *
     * @param repository the person repository
     */
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }
}