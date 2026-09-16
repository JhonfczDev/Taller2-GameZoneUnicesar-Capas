package co.unicesar.edu.taller2.gamezoneunicesar.capas.model;

public class Cable extends Accessory {
    private double lengthMeters;
    private String connectorType;

    public Cable(String id, String title, double price, int stockQuantity, double lengthMeters, String connectorType) {
        super(id, title, price, stockQuantity);
        this.lengthMeters = lengthMeters;
        this.connectorType = connectorType;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | Longitud: " + lengthMeters + "m | Conector: " + connectorType;
    }

    public double getLengthMeters() {
        return lengthMeters;
    }

    public void setLengthMeters(double lengthMeters) {
        this.lengthMeters = lengthMeters;
    }

    public String getConnectorType() {
        return connectorType;
    }

    public void setConnectorType(String connectorType) {
        this.connectorType = connectorType;
    }

}
