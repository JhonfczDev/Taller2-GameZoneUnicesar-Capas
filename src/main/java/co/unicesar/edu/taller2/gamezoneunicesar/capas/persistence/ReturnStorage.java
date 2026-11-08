package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Return;

import java.util.List;

/**
 * Operations that the return service needs from the place where returns are
 * stored. The service depends on this interface, so the storage mechanism can
 * change without touching the service.
 */
public interface ReturnStorage {

    /**
     * Loads the raw data of every stored return.
     *
     * @return the raw records of the stored returns
     */
    List<ReturnRecord> loadRecords();

    /**
     * Replaces the stored returns with the given list.
     *
     * @param returns the returns to store
     */
    void saveAll(List<Return> returns);
}
