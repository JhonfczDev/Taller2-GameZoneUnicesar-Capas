package co.unicesar.edu.taller2.gamezoneunicesar.capas.service;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.WarrantyRepository;

public class WarrantyService {

    private WarrantyRepository repository;

    public WarrantyService(WarrantyRepository repository) {
        this.repository = repository;
    }
}
