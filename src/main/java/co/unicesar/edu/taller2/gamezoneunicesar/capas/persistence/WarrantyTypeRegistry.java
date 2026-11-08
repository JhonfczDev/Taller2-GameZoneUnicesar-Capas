package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Warranty;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Keeps the {@link WarrantyType} of every kind of warranty that can be stored,
 * and finds the right one by the class of a warranty or by the tag of a line.
 */
public class WarrantyTypeRegistry {

    private final Map<Class<?>, WarrantyType> byClass = new LinkedHashMap<>();
    private final Map<String, WarrantyType> byTag = new LinkedHashMap<>();

    /**
     * Registers a type of warranty.
     *
     * @param type the type to register
     * @return this registry, to chain several registrations
     */
    public WarrantyTypeRegistry register(WarrantyType type) {
        byClass.put(type.getWarrantyClass(), type);
        byTag.put(type.getTag(), type);
        return this;
    }

    /**
     * Finds the type of a warranty by its class.
     *
     * @param warranty the warranty to write
     * @return the type, or {@code null} if the class is not registered
     */
    public WarrantyType findByWarranty(Warranty warranty) {
        return byClass.get(warranty.getClass());
    }

    /**
     * Finds the type of a line by its tag.
     *
     * @param tag the tag in the first column of the line
     * @return the type, or {@code null} if the tag is unknown
     */
    public WarrantyType findByTag(String tag) {
        return byTag.get(tag);
    }
}
