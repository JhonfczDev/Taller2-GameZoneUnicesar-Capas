package co.unicesar.edu.taller2.gamezoneunicesar.capas.service;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.*;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.WarrantyRepository;

import java.time.LocalDate;
import java.util.*;
import java.io.*;

public class WarrantyService {

    private WarrantyRepository repository;

    public WarrantyService(WarrantyRepository repository) {
        this.repository = repository;
    }

    private String generateWarrantyId() {
        return "W" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public BasicWarranty assignBasicWarranty(Product product, Sale sale, LocalDate startDate) {
        BasicWarranty warranty = new BasicWarranty(generateWarrantyId(), product, sale, startDate);

        List<Warranty> warranties = repository.loadAll();
        warranties.add(warranty);
        repository.saveAll(warranties);

        return warranty;
    }

    public ExtendedWarranty assignExtendedWarranty(Product product, Sale sale, LocalDate startDate) {
        ExtendedWarranty warranty = new ExtendedWarranty(generateWarrantyId(), product, sale, startDate);

        List<Warranty> warranties = repository.loadAll();
        warranties.add(warranty);
        repository.saveAll(warranties);

        return warranty;
    }

    public Warranty findWarrantyByProduct(String productId, String saleId) {
        for (Warranty warranty : repository.loadAll()) {
            if (warranty.getProduct().getId().equals(productId)
                    && warranty.getSale().getId().equals(saleId)) {
                return warranty;
            }
        }
        return null;
    }

    public List<Warranty> listAllWarranties() {
        return repository.loadAll();
    }

    public List<Warranty> listActiveWarranties() {
        List<Warranty> active = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (Warranty warranty : repository.loadAll()) {
            if (warranty.isActive(today)) {
                active.add(warranty);
            }
        }

        return active;
    }

    public List<Warranty> listWarrantiesExpiringSoon(int daysAhead) {
        List<Warranty> expiringSoon = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate limit = today.plusDays(daysAhead);

        for (Warranty warranty : repository.loadAll()) {
            LocalDate endDate = warranty.getEndDate();
            // Still covered today, but expiring on or before the limit date
            if (!endDate.isBefore(today) && !endDate.isAfter(limit)) {
                expiringSoon.add(warranty);
            }
        }

        return expiringSoon;
    }
}
