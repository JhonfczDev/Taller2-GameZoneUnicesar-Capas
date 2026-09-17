package co.unicesar.edu.taller2.gamezoneunicesar.capas.service;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.WarrantyRepository;

import java.util.UUID;

public class WarrantyService {

    private WarrantyRepository repository;

    public WarrantyService(WarrantyRepository repository) {
        this.repository = repository;
    }

    private String generateWarrantyId() {
        return "W" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
