package co.unicesar.edu.taller2.gamezoneunicesar.capas.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Return {
    private String id;
    private LocalDate returnDate;
    private Sale sale;
    private List<Product> returnedProducts;
    private String reason;
    private double refundAmount;

    public Return(String id, LocalDate returnDate, Sale sale, List<Product> returnedProducts, String reason){
        if (sale == null) {
            throw new IllegalArgumentException("la venta original no puede ser nula");
        }

        if (returnDate == null) {
            throw new IllegalArgumentException("la fecha de devolución no puede ser nula");
        }

        if (!sale.canBeReturned(returnDate)){
            throw new IllegalArgumentException("la devolución supera el plazo limite de 30 días desde la venta");
        }

        for (Product product : returnedProducts){
            if (!sale.getProducts().contains(product)){
                throw new IllegalArgumentException("El producto " + product.getTitle() + " no pertenece a la venta original.");
            }
        }

        this.id = id;
        this.returnDate = returnDate;
        this.sale = sale;
        this.returnedProducts = new ArrayList<>(returnedProducts);
        this.reason = reason;
        this.refundAmount = calculateRefundAmount();
    }

    public double calculateRefundAmount(){
        double total = 0.0;

        if (returnedProducts != null){
            for (Product product : returnedProducts){
                total += product.getPrice();
            }
        }
        this.refundAmount = total;
        return total;
    }

    public String generateReturnReceipt(){
        StringBuilder receipt = new StringBuilder();

        receipt.append("        COMPROBANTE DE DEVOLUCIÓN        \n");
        receipt.append("ID Devolución    : ").append(id).append("\n");
        receipt.append("Fecha Devolución : ").append(returnDate).append("\n");
        receipt.append("ID Venta Original: ").append(sale.getId()).append("\n");
        receipt.append("Motivo           : ").append(reason).append("\n");
        receipt.append("Productos Devueltos:\n");

        for (Product p : returnedProducts){
            receipt.append(String.format(" - %-25s $ %.2f \n", p.getTitle(), p.getPrice()));
        }

        receipt.append(String.format("Monto Reembolsado Total: $%.2f\n", refundAmount));

        return receipt.toString();
    }

    public String getId() {
        return id;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public Sale getSale() {
        return sale;
    }

    public List<Product> getReturnedProducts() {
        return Collections.unmodifiableList(returnedProducts);
    }

    public String getReason() {
        return reason;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

}
