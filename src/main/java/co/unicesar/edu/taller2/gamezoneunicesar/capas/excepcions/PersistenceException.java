package co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions;

public class PersistenceException extends GameZoneExcepcion{
    public PersistenceException(String message, Throwable cause){
        super(message, "PERSISTENCE_ERROR");

        if (cause != null){
            initCause(cause);
        }
    }
}
