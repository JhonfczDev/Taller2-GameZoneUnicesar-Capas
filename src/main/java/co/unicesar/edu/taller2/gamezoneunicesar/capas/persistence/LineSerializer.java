package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

/**
 * Knows how one concrete type of {@code T} is written to, and rebuilt from, one
 * line of a comma separated text file. The first column of every line is the
 * tag of the type.
 * <p>
 * Each type has its own serializer, so adding a new type only needs a new
 * serializer registered in a {@link SerializerRegistry}, without editing the
 * code of the repository that reads and writes the file.
 * </p>
 *
 * @param <T> base type handled by the repository (for example, a person)
 */
public interface LineSerializer<T> {

    /**
     * Gets the concrete class that this serializer writes.
     *
     * @return the class handled by this serializer
     */
    Class<? extends T> getHandledClass();

    /**
     * Gets the tag written in the first column of the line.
     *
     * @return the tag of the type
     */
    String getTag();

    /**
     * Converts an item of the handled class into a complete line, including the tag.
     *
     * @param item the item to write; it is always an instance of the handled class
     * @return the line to write
     */
    String toLine(T item);

    /**
     * Rebuilds an item from the fields of a line.
     *
     * @param data the fields of the line; {@code data[0]} is the tag
     * @return the rebuilt item
     */
    T fromFields(String[] data);
}
