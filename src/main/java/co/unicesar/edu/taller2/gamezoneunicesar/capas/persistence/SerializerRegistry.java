package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Keeps the {@link LineSerializer} of every type that a repository can read
 * and write, and finds the right one by the class of an item or by the tag of
 * a line.
 *
 * @param <T> base type handled by the repository
 */
public class SerializerRegistry<T> {

    private final Map<Class<?>, LineSerializer<T>> byClass = new LinkedHashMap<>();
    private final Map<String, LineSerializer<T>> byTag = new LinkedHashMap<>();

    /**
     * Registers a serializer.
     *
     * @param serializer the serializer to register
     * @return this registry, to chain several registrations
     */
    public SerializerRegistry<T> register(LineSerializer<T> serializer) {
        byClass.put(serializer.getHandledClass(), serializer);
        byTag.put(serializer.getTag(), serializer);
        return this;
    }

    /**
     * Finds the serializer of an item by its class.
     *
     * @param item the item to write
     * @return the serializer, or {@code null} if the type is not registered
     */
    public LineSerializer<T> findByItem(T item) {
        return byClass.get(item.getClass());
    }

    /**
     * Finds the serializer of a line by its tag.
     *
     * @param tag the tag in the first column of the line
     * @return the serializer, or {@code null} if the tag is unknown
     */
    public LineSerializer<T> findByTag(String tag) {
        return byTag.get(tag);
    }
}
