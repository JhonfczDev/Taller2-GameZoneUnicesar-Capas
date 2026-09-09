package co.unicesar.edu.taller2.gamezoneunicesar.capas.service;
import java.util.List;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.PersonRepository;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Person;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Customer;

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

    /**
     * Registers a new customer: loads the current list of people,
     * adds the new customer, and saves the updated full list.
     * @param c the customer to register
     */
    public void registerCustomer(Customer c) {
        List<Person> persons = repository.loadAll();

        persons.add(c);
        repository.saveAll(persons);
    }
}