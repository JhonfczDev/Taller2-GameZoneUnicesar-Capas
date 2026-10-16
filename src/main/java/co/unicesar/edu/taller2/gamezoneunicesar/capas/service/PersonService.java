package co.unicesar.edu.taller2.gamezoneunicesar.capas.service;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.PersonRepository;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.validations.PersonValidator;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Person;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Customer;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Seller;

import java.util.*;

/**
 * Service layer that acts as an intermediary between the business logic
 * and the persistence repository (PersonRepository). It provides
 * higher-level operations for registering and querying customers and sellers.
 * <p>
 * Input and business-rule validations are delegated to {@link PersonValidator},
 * which throws the typed excepcions of the custom exception hierarchy.
 * </p>
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
     * Registers a new customer in the repository.
     * <p>
     * First validates the data format and that no other person already uses the
     * same id. Then creates a new {@link Customer} with the provided data, adds
     * it to the existing list of persons retrieved from the repository, and
     * persists the updated list.
     * </p>
     *
     * @param id the unique identifier of the customer
     * @param name the customer's name
     * @param phone the customer's phone number
     * @param email the customer's email address
     * @throws co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.InvalidDataException
     *         if a required field is empty or the email format is not valid
     * @throws co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.BussinesRuleException
     *         if a person with the same id is already registered
     * @throws co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.PersistenceException
     *         if the persons file cannot be read or written
     */
    public void registerCustomer(String id, String name, String phone, String email) {
        // Format validation first. Note: the validator receives (id, name, email, phone),
        // which is a different order from this method's parameters.
        PersonValidator.validatePersonData(id, name, email, phone);

        List<Person> persons = repository.loadAll();

        // Business rule: ids must be unique among the registered persons
        PersonValidator.validateUniquePerson(persons, id);

        Customer customer = new Customer(id, name, phone, email);
        persons.add(customer);
        repository.saveAll(persons);
    }

    /**
     * Retrieves all registered customers by filtering the full list
     * of people, keeping only those that are instances of Customer.
     *
     * @return list of all customers
     * @throws co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.PersistenceException
     *         if the persons file cannot be read
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
     *
             * @return list of all sellers
     * @throws co.unicesar.edu.taller2.gamezoneunicesar.capas.exceptions.PersistenceException
     *         if the persons file cannot be read
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

    /**
     * Finds a customer by id, delegating directly to the repository.
     * <p>
     * It intentionally keeps returning {@code null} when the customer does not
     * exist, because {@code SaleService} relies on that contract. Callers that must
     * treat a missing customer as an error call
     * {@link PersonValidator#validatePersonExist(Person, String)}.
     * </p>
     *
     * @param id customer's identifier
     * @return the customer found, or {@code null}
     * @throws co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.PersistenceException
     *         if the persons file cannot be read
     */
    public Customer findCustomerById(String id) {
        return repository.findCustomerById(id);
    }

    /**
     * Finds a seller by id, delegating directly to the repository.
     * <p>
     * It intentionally keeps returning {@code null} when the seller does not
     * exist, for the same reason as {@link #findCustomerById(String)}.
     * </p>
     *
     * @param id seller's identifier
     * @return the seller found, or {@code null}
     * @throws co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.PersistenceException
     *         if the persons file cannot be read
     */
    public Seller findSellerById(String id) {
        return repository.findSellerById(id);
    }
}