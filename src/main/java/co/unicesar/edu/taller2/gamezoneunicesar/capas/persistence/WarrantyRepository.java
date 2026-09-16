package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.service.ProductService;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.service.SaleService;

import java.io.*;

public class WarrantyRepository {

    private File file;
    private SaleService saleService;
    private ProductService productService;

    public WarrantyRepository(SaleService saleService, ProductService productService) {
        this("data/warranties.csv", saleService, productService);
    }

    public WarrantyRepository(String fileName, SaleService saleService, ProductService productService) {
        this.file = new File(fileName);
        this.saleService = saleService;
        this.productService = productService;
    }
}
