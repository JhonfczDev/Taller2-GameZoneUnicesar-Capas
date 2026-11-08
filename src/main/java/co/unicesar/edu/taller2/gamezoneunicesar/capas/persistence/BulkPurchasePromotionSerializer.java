package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.BulkPurchasePromotion;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Promotion;

import java.time.LocalDate;

/**
 * Writes and rebuilds {@link BulkPurchasePromotion} objects in the promotions file.
 */
public class BulkPurchasePromotionSerializer implements LineSerializer<Promotion> {

    @Override
    public Class<? extends Promotion> getHandledClass() {
        return BulkPurchasePromotion.class;
    }

    @Override
    public String getTag() {
        return "BULK";
    }

    @Override
    public String toLine(Promotion promotion) {
        // The registry only gives this serializer the objects of its own class
        BulkPurchasePromotion bulk = (BulkPurchasePromotion) promotion;

        return getTag() + ","
                + bulk.getId() + ","
                + bulk.getName() + ","
                + bulk.getStartDate() + ","
                + bulk.getEndDate() + ","
                + bulk.getMinProducts() + ","
                + bulk.getPercentage();
    }

    @Override
    public Promotion fromFields(String[] data) {
        return new BulkPurchasePromotion(
                data[1],
                data[2],
                LocalDate.parse(data[3]),
                LocalDate.parse(data[4]),
                Integer.parseInt(data[5]),
                Double.parseDouble(data[6])
        );
    }
}
