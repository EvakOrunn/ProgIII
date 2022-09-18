package customException;

/**
 *
 * @author luis
 */
public class TipoIncorrectoExcepcion extends Exception{
    
    public TipoIncorrectoExcepcion(){
        super("El tipo de medicamento es invalido");
    }
    
    public TipoIncorrectoExcepcion(String message){
        super(message);
    }
    
}
