package co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions;

public class InvalidDataException extends GameZoneExcepcion{
    public InvalidDataException(String fieldName, String description){
        super("El campo '" + fieldName + "'nno es valido: " + description, "INVALID_DATA");
    }
}

