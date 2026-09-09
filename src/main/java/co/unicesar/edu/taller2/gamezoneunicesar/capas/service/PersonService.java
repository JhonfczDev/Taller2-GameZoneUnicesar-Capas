package co.unicesar.edu.taller2.gamezoneunicesar.capas.service;
import java.util.*;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.PersonRepository;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Person;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Customer;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Seller;

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

    /**
     * Retrieves all registered customers by filtering the full list
     * of people, keeping only those that are instances of Customer.
     * @return list of all customers
     */
    public List<Customer> getAllCustomers() {
        List<Person> persons = repository.loadAll();
        List<Customer> customers = new ArrayList<>();

        for (Person person : persons) {
            if (person instanceof Customer) {
                customers.add((Customer) person);
            }
        }

        return customers;
    }

    /**
     * Retrieves all registered sellers by filtering the full list
     * of people, keeping only those that are instances of Seller.
     * @return list of all sellers
     */
    public List<Seller> getAllSellers() {
        List<Person> persons = repository.loadAll();
        List<Seller> sellers = new ArrayList<>();

        for (Person person : persons) {
            if (person instanceof Seller) {
                sellers.add((Seller) person);
            }
        }

        return sellers;
    }
}