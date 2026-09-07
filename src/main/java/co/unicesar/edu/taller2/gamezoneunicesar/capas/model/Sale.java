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
    
    public String printSale() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sale:\n");
        sb.append("  id: ").append(id).append("\n");
        sb.append("  date: ").append(date).append("\n");
        sb.append("  customer: ").append(customer != null ? customer.getName() : "N/A").append("\n");
        sb.append("  seller: ").append(seller != null ? seller.getName() : "N/A").append("\n");
        sb.append("  products:\n");
        if (products.isEmpty()) {
            sb.append("    (none)\n");
        } else {
            for (Product product : products) {
                sb.append("    - ").append(product.getTitle()).append("\n");
            }
        }
        sb.append("  total: ").append(calculateTotal());
        return sb.toString();
    }
    
}
