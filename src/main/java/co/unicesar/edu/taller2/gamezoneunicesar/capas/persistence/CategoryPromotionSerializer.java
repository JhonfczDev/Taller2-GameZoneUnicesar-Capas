package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.CategoryPromotion;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Promotion;

import java.time.LocalDate;

/**
 * Writes and rebuilds {@link CategoryPromotion} objects in the promotions file.
 */
public class CategoryPromotionSerializer implements LineSerializer<Promotion> {

    @Override
    public Class<? extends Promotion> getHandledClass() {
        return CategoryPromotion.class;
    }

    @Override
    public String getTag() {
        return "CATEGORY";
    }

    @Override
    public String toLine(Promotion promotion) {
        // The registry only gives this serializer the objects of its own class
        CategoryPromotion category = (CategoryPromotion) promotion;

        return getTag() + ","
                + category.getId() + ","
                + category.getName() + ","
                + category.getStartDate() + ","
                + category.getEndDate() + ","
                + category.getPercentage() + ","
                + category.getTargetCategory();
    }

    @Override
    public Promotion fromFields(String[] data) {
        return new CategoryPromotion(
                data[1],
                data[2],
                LocalDate.parse(data[3]),
                LocalDate.parse(data[4]),
                Double.parseDouble(data[5]),
                data[6]

        );
    }
}
