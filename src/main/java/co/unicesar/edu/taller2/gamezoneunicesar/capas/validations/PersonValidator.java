package co.unicesar.edu.taller2.gamezoneunicesar.capas.validations;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.BussinesRuleException;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.InvalidDataException;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions.ResourceNotFoundExcepcion;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.Person;

import java.util.List;

public class PersonValidator {
    public static void validatePersonData(String id, String name, String email, String phone){
        if (id == null || id.trim().isEmpty()){
            throw new InvalidDataException("id", "El identificador no puede estar vacio");
        }
        if (name == null || name.trim().isEmpty()){
            throw new InvalidDataException("nombre", "El nombre no puede estar vacio");
        }
        if (email != null && (!email.contains("@") || !email.contains("."))){
            throw new InvalidDataException("email", "El correo no tiene formato valido");
        }
        if (phone == null || phone.trim().isEmpty()){
            throw new InvalidDataException("telefono", "El telefono no puede estar vacio");
        }
    }
    public static void validatePersonExist(Person person, String personId){
        if (person == null){
            throw new ResourceNotFoundExcepcion("persona", personId);
        }
    }
    public static void validateUniquePerson(List<Person> existingPersons, String newId){
        if (existingPersons != null){
            boolean exists = existingPersons.stream().anyMatch(p -> p.getId() != null && p.getId().equals(newId));

            if (exists){
                throw new BussinesRuleException("Ya existe una persona registrada con el identificador: " + newId);
            }
        }
    }
}
