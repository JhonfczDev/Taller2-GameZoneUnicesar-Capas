package co.unicesar.edu.taller2.gamezoneunicesar.capas.model;

import java.util.List;
import java.util.ArrayList;

public abstract class Accessory extends Product {
    private List<Console> compatibleConsoles;

    public Accessory(String id, String title, double price, int stockQuantity){
        super(id, title, price, stockQuantity);
        this.compatibleConsoles = new ArrayList<>();
    }

    public String getDescription() {
        return "ID: " + getId() + " | Título: " + getTitle() + " | Precio: $" + getPrice() 
                + " | Stock: " + getStockQuantity() + " | Consolas compatibles: " + compatibleConsoles.size();
    }

    public List<Console> getCompatibleConsoles() {
        return compatibleConsoles;
    }

    public void setCompatibleConsoles(List<Console> compatibleConsoles) {
        this.compatibleConsoles = compatibleConsoles;
    }

    public void addCompatibleConsole(Console console) {
        compatibleConsoles.add(console);
    }
}