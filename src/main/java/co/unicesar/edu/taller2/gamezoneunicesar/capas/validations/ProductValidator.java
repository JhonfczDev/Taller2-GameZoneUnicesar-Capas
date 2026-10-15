package co.unicesar.edu.taller2.gamezoneunicesar.capas.validations;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.BussinesRuleException;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.InvalidDataException;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.ResourceNotFoundExcepcion;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Product;

public class ProductValidator {
    public static void  validateProductData(String id, String title, double price, int stockQuantity){
        if (id == null || id.trim().isEmpty()){
            throw new InvalidDataException("id", " El identificador no puede estar vacio");
        }
        if (title == null || title.trim().isEmpty()){
            throw new InvalidDataException("titulo", " El titulo no puede estar vacio");
        }
        if (price < 0){
            throw new InvalidDataException("Precio", " EL precio no puede ser negativo");
        }
        if (stockQuantity < 0){
            throw new InvalidDataException("Stock", " El stock no puede ser negativo");
        }
    }
    public static void validateStockAvailability(Product product, int requestQuantity){
        if (product == null){
            throw new BussinesRuleException("No se puede validar el estock de en producto nulo");
        }
        if (product.getStockQuantity() < requestQuantity){
            throw new BussinesRuleException("Stock insuficiente para el producto: " + product.getTitle() +
                    " [Disponible: " + product.getStockQuantity() + ", Solicitado: " + requestQuantity + ")");
        }
    }
    public static void validateProductExist(Product product, String produtcId){
        if (product == null){
            throw new ResourceNotFoundExcepcion("producto", produtcId);
        }
    }
}
