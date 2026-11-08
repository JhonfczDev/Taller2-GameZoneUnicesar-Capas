package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Warranty;

import java.util.List;

/**
 * Operations that the warranty service needs from the place where warranties
 * are stored. The service depends on this interface, so the storage mechanism
 * can change without touching the service.
 */
public interface WarrantyStorage {

    /**
     * Loads the raw data of every stored warranty.
     *
     * @return the raw records of the stored warranties
     */
    List<WarrantyRecord> loadRecords();

    /**
     * Replaces the stored warranties with the given list.
     *
     * @param warranties the warranties to store
     */
    void saveAll(List<Warranty> warranties);
}
