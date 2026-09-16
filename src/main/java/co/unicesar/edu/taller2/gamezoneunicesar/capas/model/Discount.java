package co.unicesar.edu.taller2.gamezoneunicesar.capas.model;

import java.time.LocalDate;

public abstract class Discount {
    private String id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    public Discount(String id, String name, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //Metod to validate the vigency of the discount
    public boolean isActive(LocalDate date) {
        if (date == null) return false;
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    public abstract double calculateDiscount(Sale sale);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
