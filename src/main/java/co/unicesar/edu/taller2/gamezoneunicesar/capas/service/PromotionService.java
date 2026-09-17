package co.unicesar.edu.taller2.gamezoneunicesar.capas.service;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.*;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.PromotionRepository;

import java.time.LocalDate;
import java.util.*;
import java.io.*;

public class PromotionService {

    private PromotionRepository repository;

    public PromotionService(PromotionRepository repository) {
        this.repository = repository;
    }

    public void registerPercentageDiscount(String id, String name, LocalDate startDate,
                                           LocalDate endDate, double percentage) throws FileNotFoundException {

        PercentagePromotion promotion = new PercentagePromotion(id, name, startDate, endDate, percentage);
        List<Promotion> promotions = repository.loadAll();

        promotions.add(promotion);

        repository.saveAll(promotions);
    }

    public void registerCategoryDiscount(String id, String name, LocalDate startDate,
                                         LocalDate endDate, double percentage, String targetCategory) throws FileNotFoundException {

        CategoryPromotion promotion = new CategoryPromotion(id, name, startDate, endDate, percentage, targetCategory);
        List<Promotion> promotions = repository.loadAll();

        promotions.add(promotion);

        repository.saveAll(promotions);
    }

    public void registerBulkPurchaseDiscount(String id, String name, LocalDate startDate,
                                             LocalDate endDate, int minQuantity, double percentage) throws FileNotFoundException {

        BulkPurchasePromotion promotion = new BulkPurchasePromotion(id, name, startDate, endDate, minQuantity, percentage);
        List<Promotion> promotions = repository.loadAll();

        promotions.add(promotion);

        repository.saveAll(promotions);
    }

    public List<Promotion> listAllPromotions() throws FileNotFoundException {
        return repository.loadAll();
    }

    public List<Promotion> listActivePromotions() throws FileNotFoundException {
        List<Promotion> active = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (Promotion promotion : repository.loadAll()) {
            if (promotion.isActive(today)) {

                active.add(promotion);
            }
        }

        return active;
    }

    public Promotion findBestPromotionFor(Sale sale) throws FileNotFoundException {
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

    public Promotion findById(String id) throws FileNotFoundException {
        for (Promotion promotion: repository.loadAll()) {
            if (promotion.getId().equals(id)) {
                
                return promotion;
            }
        }

        return null;
    }
}