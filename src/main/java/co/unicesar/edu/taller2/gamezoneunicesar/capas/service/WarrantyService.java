package co.unicesar.edu.taller2.gamezoneunicesar.capas.service;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.BasicWarranty;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Product;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Sale;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Warranty;
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
}
