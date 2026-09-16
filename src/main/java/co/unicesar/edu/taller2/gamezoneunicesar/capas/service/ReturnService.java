package co.unicesar.edu.taller2.gamezoneunicesar.capas.service;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Sale;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Product;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.service.SaleService;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.ReturnRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReturnService {

    private ReturnRepository repository;

    public ReturnService(ReturnRepository repository, SaleService saleService, ProductService productService) {
        this.repository = repository;
    }

    public Return registerReturn(String saleId, List<String> productIds, String reason) {
        if (productIds == null || productIds.isEmpty()) {
            throw new IllegalArgumentException("A return must include at least one product");
        }

        Sale originalSale = findSaleById(saleId);
        if (originalSale == null) {
            throw new IllegalArgumentException("No sale was found with the id: " + saleId);
        }

        if (!originalSale.canBeReturned()) {
            throw new IllegalArgumentException("The 30-day period to return this sale has already expired");
        }

        List<Product> returnedProducts = resolveReturnedProducts(originalSale, productIds);

        String returnId = generateReturnId();
        LocalDate today = LocalDate.now();

        Return newReturn = new Return(returnId, today, originalSale, returnedProducts, reason);
        newReturn.calculateRefundAmount();

        for (Product product : returnedProducts) {
            ProductService.restoreStock(product.getId(), 1);
        }

        List<Return> returns = repository.loadAll();

        returns.add(newReturn);
        repository.saveAll(returns);

        return newReturn;
    }

    private Sale findSaleById(String saleId) {
        for (Sale sale : SaleService.getAllSales()) {
            if (sale.getId().equals(saleId)) {
                return sale;
            }
        }
        return null;
    }

    private Product findProductInSale(List<Product> saleProducts, String productId) {
        for (Product product : saleProducts) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    private List<Product> resolveReturnedProducts(Sale originalSale, List<String> productIds) {
        List<Product> saleProducts = originalSale.getProducts();
        List<Product> returnedProducts = new ArrayList<>();

        for (String productId : productIds) {
            Product product = findProductInSale(saleProducts, productId);
            if (product == null) {
                throw new IllegalArgumentException(
                        "The product " + productId + " does not belong to the indicated sale");
            }
            returnedProducts.add(product);
        }

        return returnedProducts;
    }

    private String generateReturnId() {
        return "R" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
