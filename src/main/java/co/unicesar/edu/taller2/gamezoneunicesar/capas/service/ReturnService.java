package co.unicesar.edu.taller2.gamezoneunicesar.capas.service;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Sale;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Product;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.service.SaleService;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.ReturnRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReturnService {

    private ReturnRepository repository;

    public ReturnService(ReturnRepository repository, SaleService saleService, ProductService productService) {
        this.repository = repository;
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
}
