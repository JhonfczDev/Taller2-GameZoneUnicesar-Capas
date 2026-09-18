package co.unicesar.edu.taller2.gamezoneunicesar.capas.model;

public abstract class Product {
    private String id;
    private String title;
    private double price;
    private int stockQuantity;

    public Product(String id, String title, double price, int stockQuantity) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public abstract String getDescription();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
