package customException;

public class CadenaVaciaExcepcion extends Exception{

	public CadenaVaciaExcepcion(){
		super("Ingreso un texto vacio");
	}

	public CadenaVaciaExcepcion(String message){
		super(message);
	}

}