package co.unicesar.edu.taller2.gamezoneunicesar.capas.model;

public class Controller extends Accessory {
    private String connectionType;

    public Controller(String id, String title, double price, int stockQuantity, String connectionType) {
        super(id, title, price, stockQuantity);
        this.connectionType = connectionType;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | Tipo de conexión: " + connectionType;
    }


    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }
}