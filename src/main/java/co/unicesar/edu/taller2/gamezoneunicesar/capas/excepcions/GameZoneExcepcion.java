package co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions;

public class GameZoneExcepcion extends RuntimeException{
    private final String errorCode;

    public GameZoneExcepcion(String message, String errorCode){
        super(message);
        this.errorCode = errorCode;
    }
    public GameZoneExcepcion(String message){
        super(message);
        this.errorCode = "Generic_Error";
    }
    public String getErrorCode(){
        return errorCode;
    }
}
