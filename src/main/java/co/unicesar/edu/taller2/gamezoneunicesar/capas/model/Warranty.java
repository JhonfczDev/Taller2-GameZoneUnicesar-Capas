package co.unicesar.edu.taller2.gamezoneunicesar.capas.model;

import java.time.LocalDate;

public abstract class Warranty {
    private String id;
    private Product product;
    private Sale sale;
    private LocalDate startDate;
    private LocalDate endDate;

    public Warranty(String id, Product product, Sale sale, LocalDate startDate){
        this.id = id;
        this.product = product;
        this.sale = sale;
        this.startDate = startDate;
        // the endDate is calculated with an abstract method
        this.endDate = startDate.plusMonths(this.getDurationInMonths());
    }

    // abstract methods
    public abstract int getDurationInMonths();
    public abstract String getWarrantyType();
    public abstract double getAdditionalCost();
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        // if the start date is modified the end date is recalculated
        if (startDate != null) {
            this.endDate = startDate.plusMonths(this.getDurationInMonths());
        }
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    // return true if the indicate date is vigency into the warranty period
    public boolean isActive(LocalDate date){
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    public String generateWarrantyCertificate(){
        return String.format(
            "        CERTIFICADO DE GARANTÍA         \n" +
            "ID Garantía   : %s\n" +
            "Tipo          : %s\n" +
            "Producto      : %s\n" +
            "Venta Asociada: %s\n" +
            "Fecha Inicio  : %s\n" +
            "Fecha Fin     : %s\n" +
            "Costo Adic.   : $%.2f\n",
            
            id,
            getWarrantyType(),
            product.getTitle(),
            sale.getId(),
            startDate.toString(),
            endDate.toString(),
            getAdditionalCost()
        );
    }
   
}
