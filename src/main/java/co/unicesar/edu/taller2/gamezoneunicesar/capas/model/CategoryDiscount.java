package co.unicesar.edu.taller2.gamezoneunicesar.capas.model;

import java.time.LocalDate;

public class CategoryDiscount extends Discount {
    private double percentage;
    private String targetCategory;

    public CategoryDiscount(String id, String name, LocalDate startDate, LocalDate endDate, double percentage, String targetCategory) {
        super(id, name, startDate, endDate);
        this.percentage = percentage;
        this.targetCategory = targetCategory;
    }

    @Override
    public double calculateDiscount(Sale sale) {
        double categoryTotal = 0.0;
        
        // null validation for products list
        for (Product product : sale.getProducts()) {
            if (product != null) {
                // Get derived class name
                String productClassName = product.getClass().getSimpleName();
                
                if (productClassName.equalsIgnoreCase(this.targetCategory)) {
                    categoryTotal += product.getPrice();
                }
            }
        }
        return categoryTotal * (this.percentage / 100);
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getTargetCategory() {
        return targetCategory;
    }

    public void setTargetCategory(String targetCategory) {
        this.targetCategory = targetCategory;
    }

}
