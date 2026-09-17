package co.unicesar.edu.taller2.gamezoneunicesar.capas.model;

import java.time.LocalDate;

public class PercentagePromotion extends Promotion {
    private double percentage;

    public PercentagePromotion(String id, String name, LocalDate startDate, LocalDate endDate, double percentage) {
        super(id, name, startDate, endDate);
        this.percentage = percentage;
    }

    @Override
    public double calculateDiscount(Sale sale) {
        // sale.calculateTotal() from Sale class is used here
        return sale.calculateTotal() * (this.percentage / 100);
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
