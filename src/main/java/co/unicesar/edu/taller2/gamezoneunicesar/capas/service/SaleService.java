package co.unicesar.edu.taller2.gamezoneunicesar.capas.service;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Customer;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Product;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Sale;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Seller;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.SaleRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SaleService {
    
    private final SaleRepository saleRepository;
    private final ProductService productService;
    private final PersonService personService;

    public SaleService(SaleRepository saleRepository,
                       ProductService productService,
                       PersonService personService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.personService = personService;
    }
    
    private String generateSaleId() {
        return "S" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    
    
    
}
