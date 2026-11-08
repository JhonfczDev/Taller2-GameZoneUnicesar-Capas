package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Accessory;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Memory;

/**
 * Writes and rebuilds {@link Memory} objects in the accessories file.
 */
public class MemorySerializer implements LineSerializer<Accessory> {

    @Override
    public Class<? extends Accessory> getHandledClass() {
        return Memory.class;
    }

    @Override
    public String getTag() {
        return "MEMORY";
    }

    @Override
    public String toLine(Accessory accessory) {
        // The registry only gives this serializer the objects of its own class
        Memory memory = (Memory) accessory;

        return getTag() + ","
                + memory.getId() + ","
                + memory.getTitle() + ","
                + memory.getPrice() + ","
                + memory.getStockQuantity() + ","
                + memory.getCapacityGB() + ","
                + memory.getMemoryType() + ","
                + ConsoleIdsFormat.join(memory.getCompatibleConsoles());
    }

    @Override
    public Accessory fromFields(String[] data) {
        Memory memory = new Memory(
                data[1],
                data[2],
                Double.parseDouble(data[3]),
                Integer.parseInt(data[4]),
                Integer.parseInt(data[5]),
                data[6]
        );

        memory.setCompatibleConsoles(ConsoleIdsFormat.split(data.length > 7 ? data[7] : ""));

        return memory;
    }
}
