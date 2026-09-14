package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

public class PromotionService {

    private PromotionRepository repository;

    public PromotionService(PromotionRepository repository) {
        this.repository = repository;
    }
}