package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Customer;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Person;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Seller;

import java.util.List;

/**
 * Operations that the person service needs from the place where persons are
 * stored. The service depends on this interface, so the storage mechanism
 * (a text file today) can change without touching the service.
 */
public interface PersonStorage {

    /**
     * Loads every stored person.
     *
     * @return the list of stored persons
     */
    List<Person> loadAll();

    /**
     * Replaces the stored persons with the given list.
     *
     * @param persons the persons to store
     */
    void saveAll(List<Person> persons);

    /**
     * Finds a customer by id.
     *
     * @param id id of the customer
     * @return the customer found, or {@code null} if it does not exist
     */
    Customer findCustomerById(String id);

    /**
     * Finds a seller by id.
     *
     * @param id id of the seller
     * @return the seller found, or {@code null} if it does not exist
     */
    Seller findSellerById(String id);
}
