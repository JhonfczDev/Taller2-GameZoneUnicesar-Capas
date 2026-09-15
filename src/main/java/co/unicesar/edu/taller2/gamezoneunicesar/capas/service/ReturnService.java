package co.unicesar.edu.taller2.gamezoneunicesar.capas.service;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.ReturnRepository;

public class ReturnService {

    private ReturnRepository repository;

    public ReturnService(ReturnRepository repository, SaleService saleService, ProductService productService) {
        this.repository = repository;
    }
}
