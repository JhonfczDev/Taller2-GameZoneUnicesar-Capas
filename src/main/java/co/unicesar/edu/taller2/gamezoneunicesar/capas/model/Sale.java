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
    
    public String getId() {
        return id;
    }
    public LocalDate getDate() {
        return date;
    }
    public Customer getCustomer() {
        return customer;
    }
    public Seller getSeller() {
        return seller;
    }
    public List<Product> getProducts() {
        return new ArrayList<>(products); 
    }
    
    public void setId(String id) {
        this.id = id;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public void setSeller(Seller seller) {
        this.seller = seller;
    }
    public void setProducts(List<Product> products) {
        this.products = products != null ? new ArrayList<>(products) : new ArrayList<>();
    }
    
    public double calculateTotal() {
        double total = 0.0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }
    
    public void addProduct(Product product) {
        if (product != null) {
            this.products.add(product);
        }
    }
    
}
