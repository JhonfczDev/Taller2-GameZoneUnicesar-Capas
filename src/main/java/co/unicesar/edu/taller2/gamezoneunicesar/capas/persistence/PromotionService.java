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
}