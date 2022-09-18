package customException;

public class FechaInvalidaExcepcion extends Exception{
    
    public FechaInvalidaExcepcion(){
        super("La fecha ingresada es invalida");
    }

    public FechaInvalidaExcepcion(String message){
        super(message);
    }

}
