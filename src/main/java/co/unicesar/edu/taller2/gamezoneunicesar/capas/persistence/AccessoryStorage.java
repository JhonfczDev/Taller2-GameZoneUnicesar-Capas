package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Accessory;

import java.util.List;

/**
 * Operations that the accessory service needs from the place where accessories
 * are stored. The service depends on this interface, so the storage mechanism
 * can change without touching the service.
 */
public interface AccessoryStorage {

    /**
     * Loads every stored accessory.
     *
     * @return the list of stored accessories
     */
    List<Accessory> loadAll();

    /**
     * Replaces the stored accessories with the given list.
     *
     * @param accessories the accessories to store
     */
    void saveAll(List<Accessory> accessories);

    /**
     * Finds an accessory by id.
     *
     * @param id id of the accessory
     * @return the accessory found, or {@code null} if it does not exist
     */
    Accessory findById(String id);
}
