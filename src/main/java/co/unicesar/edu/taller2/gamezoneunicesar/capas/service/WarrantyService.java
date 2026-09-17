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
        String fabricDefects = "Covers manufacturing defects";
        int duration = 12;
        String priceProduct = String.valueOf(product.getPrice());

        BasicWarranty warranty = new BasicWarranty(fabricDefects, duration, priceProduct, generateWarrantyId());

        List<Warranty> warranties = repository.loadAll();
        warranties.add(warranty);
        repository.saveAll(warranties);

        return warranty;
    }

    public ExtendedWarranty assignExtendedWarranty(Product product, Sale sale, LocalDate startDate) {
        String accidentalDamage = "Covers accidental damage";
        String fabricDefects = "Covers manufacturing defects";
        int duration = 12;
        String priceProduct = String.valueOf(product.getPrice());
        double additionalCost = 0.10 * product.getPrice();

        ExtendedWarranty warranty = new ExtendedWarranty(
                accidentalDamage, additionalCost, fabricDefects, duration, priceProduct, generateWarrantyId());

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
}
