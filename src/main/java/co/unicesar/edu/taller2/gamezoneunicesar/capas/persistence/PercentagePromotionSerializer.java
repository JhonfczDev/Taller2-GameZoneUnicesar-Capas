package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.PercentagePromotion;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Promotion;

import java.time.LocalDate;

/**
 * Writes and rebuilds {@link PercentagePromotion} objects in the promotions file.
 */
public class PercentagePromotionSerializer implements LineSerializer<Promotion> {

    @Override
    public Class<? extends Promotion> getHandledClass() {
        return PercentagePromotion.class;
    }

    @Override
    public String getTag() {
        return "PERCENTAGE";
    }

    @Override
    public String toLine(Promotion promotion) {
        // The registry only gives this serializer the objects of its own class
        PercentagePromotion percentage = (PercentagePromotion) promotion;

        return getTag() + ","
                + percentage.getId() + ","
                + percentage.getName() + ","
                + percentage.getStartDate() + ","
                + percentage.getEndDate() + ","
                + percentage.getPercentage();
    }

    @Override
    public Promotion fromFields(String[] data) {
        return new PercentagePromotion(
                data[1],
                data[2],
                LocalDate.parse(data[3]),
                LocalDate.parse(data[4]),
                Double.parseDouble(data[5])
        );
    }
}
