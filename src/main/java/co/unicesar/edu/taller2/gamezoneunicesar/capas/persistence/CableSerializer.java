package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Accessory;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Cable;

/**
 * Writes and rebuilds {@link Cable} objects in the accessories file.
 */
public class CableSerializer implements LineSerializer<Accessory> {

    @Override
    public Class<? extends Accessory> getHandledClass() {
        return Cable.class;
    }

    @Override
    public String getTag() {
        return "CABLE";
    }

    @Override
    public String toLine(Accessory accessory) {
        // The registry only gives this serializer the objects of its own class
        Cable cable = (Cable) accessory;

        return getTag() + ","
                + cable.getId() + ","
                + cable.getTitle() + ","
                + cable.getPrice() + ","
                + cable.getStockQuantity() + ","
                + cable.getLengthMeters() + ","
                + cable.getConnectorType();
    }

    @Override
    public Accessory fromFields(String[] data) {
        return new Cable(
                data[1],
                data[2],
                Double.parseDouble(data[3]),
                Integer.parseInt(data[4]),
                Double.parseDouble(data[5]),
                data[6]
        );
    }
}
