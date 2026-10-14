package co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions;

public class BussinesRuleException extends GameZoneExcepcion{
    public BussinesRuleException(String message){
        super(message, "BUSSINES_RULE_VALIDATION");
    }
}
