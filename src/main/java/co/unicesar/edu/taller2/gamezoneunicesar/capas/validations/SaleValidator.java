package co.unicesar.edu.taller2.gamezoneunicesar.capas.validations;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.BussinesRuleException;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.InvalidDataException;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.ResourceNotFoundExcepcion;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Customer;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Product;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Sale;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Seller;

import java.util.List;

public class SaleValidator {
    public static void validateSaleData(Customer customer, Seller seller, List<Product> products){
        if (customer == null){
            throw new BussinesRuleException("La venta debe estar asociada con un cliente valido");
        }
        if (seller == null){
            throw new BussinesRuleException("La venta debe estar asociada con un vendedor valido");
        }
        if (products == null || products.isEmpty()){
            throw new InvalidDataException("productos", "La lista de productos no puede estar vacia");
        }
    }
    public static void validateSaleExists(Sale sale, String saleId){
        if (sale == null){
            throw new ResourceNotFoundExcepcion("venta", saleId);
        }
    }
}
