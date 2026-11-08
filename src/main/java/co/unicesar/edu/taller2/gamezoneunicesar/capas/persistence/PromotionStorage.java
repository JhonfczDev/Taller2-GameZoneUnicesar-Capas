package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Promotion;

import java.util.List;

/**
 * Operations that the promotion service needs from the place where promotions
 * are stored. The service depends on this interface, so the storage mechanism
 * can change without touching the service.
 */
public interface PromotionStorage {

    /**
     * Loads every stored promotion.
     *
     * @return the list of stored promotions
     */
    List<Promotion> loadAll();

    /**
     * Replaces the stored promotions with the given list.
     *
     * @param promotions the promotions to store
     */
    void saveAll(List<Promotion> promotions);
}
