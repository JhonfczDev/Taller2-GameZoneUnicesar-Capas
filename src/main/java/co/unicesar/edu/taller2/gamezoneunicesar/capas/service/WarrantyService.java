package co.unicesar.edu.taller2.gamezoneunicesar.capas.service;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.*;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.WarrantyRepository;

import java.time.LocalDate;
import java.util.*;
import java.io.*;

public class WarrantyService {

    private WarrantyRepository repository;
    private ProductService productService;
    private SaleService saleService;

    /**
     * Old form of the constructor, kept only until {@code Main} uses the new one.
     *
     * @param repository repository used to persist and retrieve warranties
     * @deprecated use {@link #WarrantyService(WarrantyRepository, ProductService)}
     */
    @Deprecated
    public WarrantyService(WarrantyRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates a new {@code WarrantyService} that will resolve the products of the
     * stored warranties through the given service. The sale service is set later
     * with {@link #setSaleService(SaleService)}, because {@code SaleService}
     * itself needs this service in its constructor.
     *
     * @param repository     repository used to persist and retrieve warranties
     * @param productService service used to look up products
     */
    public WarrantyService(WarrantyRepository repository, ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    /**
     * Sets the service used to look up the sales of the stored warranties.
     *
     * @param saleService service used to look up sales
     */
    public void setSaleService(SaleService saleService) {
        this.saleService = saleService;
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
