package co.unicesar.edu.taller2.gamezoneunicesar.capas.service;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.*;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.PromotionRepository;

import java.time.LocalDate;
import java.util.*;

public class PromotionService {

    private PromotionRepository repository;

    public PromotionService(PromotionRepository repository) {
        this.repository = repository;
    }

    /**
     * Registers a new percentage promotion.
     *
     * @param id         id of the promotion
     * @param name       name of the promotion
     * @param startDate  first day of validity
     * @param endDate    last day of validity
     * @param percentage discount percentage applied to the sale total
     */
    public void registerPercentageDiscount(String id, String name, LocalDate startDate,
                                           LocalDate endDate, double percentage) {

        PercentagePromotion promotion = new PercentagePromotion(id, name, startDate, endDate, percentage);
        List<Promotion> promotions = repository.loadAll();

        promotions.add(promotion);

        repository.saveAll(promotions);
    }

    /**
     * Registers a new category promotion.
     *
     * @param id             id of the promotion
     * @param name           name of the promotion
     * @param startDate      first day of validity
     * @param endDate        last day of validity
     * @param percentage     discount percentage applied to the products of the category
     * @param targetCategory category the promotion applies to
     */
    public void registerCategoryDiscount(String id, String name, LocalDate startDate,
                                         LocalDate endDate, double percentage, String targetCategory) {

        CategoryPromotion promotion = new CategoryPromotion(id, name, startDate, endDate, percentage, targetCategory);
        List<Promotion> promotions = repository.loadAll();

        promotions.add(promotion);

        repository.saveAll(promotions);
    }

    /**
     * Registers a new bulk purchase promotion.
     *
     * @param id          id of the promotion
     * @param name        name of the promotion
     * @param startDate   first day of validity
     * @param endDate     last day of validity
     * @param minQuantity minimum number of products in the sale
     * @param percentage  discount percentage applied to the sale total
     */
    public void registerBulkPurchaseDiscount(String id, String name, LocalDate startDate,
                                             LocalDate endDate, int minQuantity, double percentage) {

        BulkPurchasePromotion promotion = new BulkPurchasePromotion(id, name, startDate, endDate, minQuantity, percentage);
        List<Promotion> promotions = repository.loadAll();

        promotions.add(promotion);

        repository.saveAll(promotions);
    }

    /**
     * Retrieves every stored promotion.
     *
     * @return the list of all promotions
     */
    public List<Promotion> listAllPromotions() {
        return repository.loadAll();
    }

    /**
     * Retrieves the promotions that are valid today.
     *
     * @return the list of active promotions
     */
    public List<Promotion> listActivePromotions() {
        List<Promotion> active = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (Promotion promotion : repository.loadAll()) {
            if (promotion.isActive(today)) {

                active.add(promotion);
            }
        }

        return active;
    }

    /**
     * Finds the active promotion that gives the highest discount to a sale.
     *
     * @param sale the sale to evaluate
     * @return the best promotion, or {@code null} if none gives a discount
     */
    public Promotion findBestPromotionFor(Sale sale) {
        Promotion best = null;
        double maxDiscount = 0.0;

        for (Promotion promotion : listActivePromotions()) {
            double discount = promotion.calculateDiscount(sale);
            if (discount > maxDiscount) {
                maxDiscount = discount;
                best = promotion;
            }
        }

        return best;
    }

    /**
     * Finds a promotion by its id.
     *
     * @param id id of the promotion
     * @return the promotion found, or {@code null} if it does not exist
     */
    public Promotion findById(String id) {
        for (Promotion promotion: repository.loadAll()) {
            if (promotion.getId().equals(id)) {

                return promotion;
            }
        }

        return null;
    }
}