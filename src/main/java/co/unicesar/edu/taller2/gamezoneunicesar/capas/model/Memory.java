package co.unicesar.edu.taller2.gamezoneunicesar.capas.model;

public class Memory extends Accessory {
    private int capacityGB;
    private String memoryType;

    public Memory(String id, String title, double price, int stockQuantity, int capacityGB, String memoryType) {
        super(id, title, price, stockQuantity);
        this.capacityGB = capacityGB;
        this.memoryType = memoryType;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | Capacidad: " + capacityGB + "GB | Tipo de memoria: " + memoryType;
    }

    public int getCapacityGB() {
        return capacityGB;
    }

    public void setCapacityGB(int capacityGB) {
        this.capacityGB = capacityGB;
    }

    public String getMemoryType() {
        return memoryType;
    }

    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
    }
}