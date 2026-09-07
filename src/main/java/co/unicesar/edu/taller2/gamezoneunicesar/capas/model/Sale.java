package co.unicesar.edu.taller2.gamezoneunicesar.capas.model;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Product;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Sale {
    
    private String id;
    private LocalDate date;
    private Customer customer;
    private Seller seller;
    private List<Product> products;
  
    public Sale(String id, LocalDate date, Customer customer, Seller seller, List<Product> products) {
        this.id = id;
        this.date = date;
        this.customer = customer;
        this.seller = seller;
        this.products = products != null ? new ArrayList<>(products) : new ArrayList<>();
    }
    
    
}
