package co.unicesar.edu.taller2.gamezoneunicesar.capas.excepcions;

public class ResourceNotFoundExcepcion extends GameZoneExcepcion{
    public ResourceNotFoundExcepcion(String resourceType, String identifier){
        super("El " + resourceType + " con identificador " + identifier + " no fue encontrado", "RESOURCE_nOT_FOUND");
    }
}
