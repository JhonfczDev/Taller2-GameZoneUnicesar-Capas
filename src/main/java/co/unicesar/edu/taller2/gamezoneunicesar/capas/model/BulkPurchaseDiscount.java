package co.unicesar.edu.taller2.gamezoneunicesar.capas.model;

import java.time.LocalDate;

public class BulkPurchaseDiscount extends Discount {
    private int minProducts;
    private double percentage;

    public BulkPurchaseDiscount(String id, String name, LocalDate startDate, LocalDate endDate, int minProducts, double percentage) {
        super(id, name, startDate, endDate);
        this.minProducts = minProducts;
        this.percentage = percentage;
    }

    @Override
    public double calculateDiscount(Sale sale) {
        // check the products quantity and call sale.calculateTotal() from Sale class
        if (sale.getProducts() != null && sale.getProducts().size() >= this.minProducts) {
            return sale.calculateTotal() * (this.percentage / 100);
        } else {
            return 0.0;
        }
    }

    public int getMinProducts() {
        return minProducts;
    }

    public void setMinProducts(int minProducts) {
        this.minProducts = minProducts;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

}
