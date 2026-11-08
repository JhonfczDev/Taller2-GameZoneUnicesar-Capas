package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Accessory;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Controller;

/**
 * Writes and rebuilds {@link Controller} objects in the accessories file.
 */
public class ControllerSerializer implements LineSerializer<Accessory> {

    @Override
    public Class<? extends Accessory> getHandledClass() {
        return Controller.class;
    }

    @Override
    public String getTag() {
        return "CONTROLLER";
    }

    @Override
    public String toLine(Accessory accessory) {
        // The registry only gives this serializer the objects of its own class
        Controller controller = (Controller) accessory;

        return getTag() + ","
                + controller.getId() + ","
                + controller.getTitle() + ","
                + controller.getPrice() + ","
                + controller.getStockQuantity() + ","
                + controller.getConnectionType() + ","
                + ConsoleIdsFormat.join(controller.getCompatibleConsoles());
    }

    @Override
    public Accessory fromFields(String[] data) {
        Controller controller = new Controller(
                data[1],
                data[2],
                Double.parseDouble(data[3]),
                Integer.parseInt(data[4]),
                data[5]
        );

        controller.setCompatibleConsoles(ConsoleIdsFormat.split(data.length > 6 ? data[6] : ""));

        return controller;
    }
}
