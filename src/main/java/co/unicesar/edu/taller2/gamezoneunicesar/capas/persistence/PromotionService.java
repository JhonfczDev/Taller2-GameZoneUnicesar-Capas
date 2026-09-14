package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

public class PromotionService {

    private PromotionRepository repository;

    public PromotionService(PromotionRepository repository) {
        this.repository = repository;
    }

    public void registerPercentageDiscount(String id, String name, LocalDate startDate,
                                           LocalDate endDate, double percentage) {

        PercentageDiscount promotion = new PercentageDiscount(id, name, startDate, endDate, percentage);
        List<Promotion> promotions = repository.loadAll();

        promotions.add(promotion);

        repository.saveAll(promotions);
    }

    public void registerCategoryDiscount(String id, String name, LocalDate startDate,
                                         LocalDate endDate, double percentage, String targetCategory) {

        CategoryDiscount promotion = new CategoryDiscount(id, name, startDate, endDate, percentage, targetCategory);
        List<Promotion> promotions = repository.loadAll();

        promotions.add(promotion);

        repository.saveAll(promotions);
    }

    public void registerBulkPurchaseDiscount(String id, String name, LocalDate startDate,
                                             LocalDate endDate, int minQuantity, double percentage) {

        BulkPurchaseDiscount promotion = new BulkPurchaseDiscount(id, name, startDate, endDate, minQuantity, percentage);
        List<Promotion> promotions = repository.loadAll();

        promotions.add(promotion);

        repository.saveAll(promotions);
    }

    public List<Promotion> listAllPromotions() {
        return repository.loadAll();
    }
}