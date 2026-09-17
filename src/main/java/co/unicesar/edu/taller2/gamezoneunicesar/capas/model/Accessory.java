package co.unicesar.edu.taller2.gamezoneunicesar.capas.model;

import java.util.List;
import java.util.ArrayList;

/**
 * Abstract class representing a generic accessory, extending {@link Product}.
 */
public abstract class Accessory extends Product {

    /** List of compatible console IDs. */
    private List<String> compatibleConsoles;

    /**
     * Creates a new accessory with the specified details.
     *
     * @param id            accessory identifier
     * @param title         accessory title
     * @param price         accessory price
     * @param stockQuantity stock quantity available
     */
    public Accessory(String id, String title, double price, int stockQuantity) {
        super(id, title, price, stockQuantity);
        this.compatibleConsoles = new ArrayList<>();
    }

    /**
     * Returns a formatted description of the accessory.
     *
     * @return description string
     */
    public String getDescription() {
        return "ID: " + getId() + " | Título: " + getTitle() + " | Precio: $" + getPrice() 
                + " | Stock: " + getStockQuantity() + " | Consolas compatibles: " + compatibleConsoles.size();
    }

    public List<String> getCompatibleConsoles() {
        return compatibleConsoles;
    }

    public void setCompatibleConsoles(List<String> compatibleConsoles) {
        this.compatibleConsoles = compatibleConsoles;
    }

    public void addCompatibleConsole(String consoleId) {
        compatibleConsoles.add(consoleId);
    }
}
