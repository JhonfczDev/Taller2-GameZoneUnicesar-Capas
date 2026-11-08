package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Person;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Seller;

/**
 * Writes and rebuilds {@link Seller} objects in the persons file.
 */
public class SellerSerializer implements LineSerializer<Person> {

    @Override
    public Class<? extends Person> getHandledClass() {
        return Seller.class;
    }

    @Override
    public String getTag() {
        return "SELLER";
    }

    @Override
    public String toLine(Person person) {
        // The registry only gives this serializer the objects of its own class
        Seller seller = (Seller) person;

        return getTag() + ","
                + seller.getId() + ","
                + seller.getName() + ","
                + seller.getPhone() + ","
                + seller.getEmployeeCode() + ","
                + seller.getWorkShift();
    }

    @Override
    public Person fromFields(String[] data) {
        // data[1]=id, data[2]=name, data[3]=phone, data[4]=employeeCode, data[5]=workShift
        return new Seller(data[1], data[2], data[3], data[4], data[5]);
    }
}
