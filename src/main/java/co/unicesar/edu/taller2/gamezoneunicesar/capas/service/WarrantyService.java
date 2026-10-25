package co.unicesar.edu.taller2.gamezoneunicesar.capas.service;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.*;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.WarrantyRecord;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.WarrantyRepository;

import java.time.LocalDate;
import java.util.*;
import java.io.*;

public class WarrantyService {

    private WarrantyRepository repository;
    private ProductService productService;
    private SaleService saleService;

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

    /**
     * Rebuilds the stored warranties from the raw records of the repository.
     * A record whose product or sale no longer exists, or whose type is
     * unknown, is skipped.
     */
    private List<Warranty> loadWarranties() {
        List<Warranty> warranties = new ArrayList<>();

        for (WarrantyRecord row : repository.loadRecords()) {
            Product product = productService.findById(row.getProductId());
            Sale sale = findSaleById(row.getSaleId());

            if (product == null || sale == null) continue;

            Warranty warranty = row.toWarranty(product, sale);
            if (warranty != null) {
                warranties.add(warranty);
            }
        }

        return warranties;
    }

    private Sale findSaleById(String saleId) {
        for (Sale sale : saleService.getAllSales()) {
            if (sale.getId().equals(saleId)) {
                return sale;
            }
        }
        return null;
    }

    /**
     * Creates and stores a basic warranty for a product of a sale.
     *
     * @param product   covered product
     * @param sale      sale the warranty belongs to
     * @param startDate date the coverage starts
     * @return the created warranty
     */
    public BasicWarranty assignBasicWarranty(Product product, Sale sale, LocalDate startDate) {
        BasicWarranty warranty = new BasicWarranty(generateWarrantyId(), product, sale, startDate);

        List<Warranty> warranties = loadWarranties();
        warranties.add(warranty);
        repository.saveAll(warranties);

        return warranty;
    }

    /**
     * Creates and stores an extended warranty for a product of a sale.
     *
     * @param product   covered product
     * @param sale      sale the warranty belongs to
     * @param startDate date the coverage starts
     * @return the created warranty
     */
    public ExtendedWarranty assignExtendedWarranty(Product product, Sale sale, LocalDate startDate) {
        ExtendedWarranty warranty = new ExtendedWarranty(generateWarrantyId(), product, sale, startDate);

        List<Warranty> warranties = loadWarranties();
        warranties.add(warranty);
        repository.saveAll(warranties);

        return warranty;
    }

    /**
     * Finds the warranty that covers a product within a sale.
     *
     * @param productId id of the covered product
     * @param saleId    id of the sale
     * @return the warranty found, or {@code null} if there is none
     */
    public Warranty findWarrantyByProduct(String productId, String saleId) {
        for (Warranty warranty : loadWarranties()) {
            if (warranty.getProduct().getId().equals(productId)
                    && warranty.getSale().getId().equals(saleId)) {
                return warranty;
            }
        }
        return null;
    }

    /**
     * Retrieves every stored warranty.
     *
     * @return the list of all warranties
     */
    public List<Warranty> listAllWarranties() {
        return loadWarranties();
    }

    /**
     * Retrieves the warranties that are active today.
     *
     * @return the list of active warranties
     */
    public List<Warranty> listActiveWarranties() {
        List<Warranty> active = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (Warranty warranty : loadWarranties()) {
            if (warranty.isActive(today)) {
                active.add(warranty);
            }
        }

        return active;
    }

    /**
     * Retrieves the warranties that expire within the given number of days.
     *
     * @param daysAhead number of days to look ahead
     * @return the list of warranties about to expire
     */
    public List<Warranty> listWarrantiesExpiringSoon(int daysAhead) {
        List<Warranty> expiringSoon = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate limit = today.plusDays(daysAhead);

        for (Warranty warranty : loadWarranties()) {
            LocalDate endDate = warranty.getEndDate();
            // Still covered today, but expiring on or before the limit date
            if (!endDate.isBefore(today) && !endDate.isAfter(limit)) {
                expiringSoon.add(warranty);
            }
        }

        return expiringSoon;
    }
}