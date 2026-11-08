package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Customer;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Person;

/**
 * Writes and rebuilds {@link Customer} objects in the persons file.
 */
public class CustomerSerializer implements LineSerializer<Person> {

    @Override
    public Class<? extends Person> getHandledClass() {
        return Customer.class;
    }

    @Override
    public String getTag() {
        return "CUSTOMER";
    }

    @Override
    public String toLine(Person person) {
        // The registry only gives this serializer the objects of its own class
        Customer customer = (Customer) person;

        return getTag() + ","
                + customer.getId() + ","
                + customer.getName() + ","
                + customer.getPhone() + ","
                + customer.getEmail();
    }

    @Override
    public Person fromFields(String[] data) {
        // data[1]=id, data[2]=name, data[3]=phone, data[4]=email
        return new Customer(data[1], data[2], data[3], data[4]);
    }
}
