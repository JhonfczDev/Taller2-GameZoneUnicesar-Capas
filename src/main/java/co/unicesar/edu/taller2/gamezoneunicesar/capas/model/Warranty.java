package co.unicesar.edu.taller2.gamezoneunicesar.capas.model;

public abstract class Warranty {

    private String id;
    private String fabricDefects;
    private int duration; // Duration in months
    private String priceProduct; // Price of the product associated with the warranty

    public Warranty(String fabricDefects, int duration, String priceProduct) {
        this.fabricDefects = fabricDefects;
        this.duration = duration;
        this.priceProduct = priceProduct;
    }

    public abstract String getWarrantyCost();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFabricDefects() {
        return fabricDefects;
    }

    public void setFabricDefects(String fabricDefects) {
        this.fabricDefects = fabricDefects;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(String priceProduct) {
        this.priceProduct = priceProduct;
    }

}
