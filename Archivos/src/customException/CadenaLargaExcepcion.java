package customException;

/**
 * CadenaLargaExcepcion
 */
public class CadenaLargaExcepcion extends Exception{

    public CadenaLargaExcepcion(){
        super("La cadena supera el limite de caracteres");
    }

    public CadenaLargaExcepcion(String message){
        super(message);
    }
    
}