package datos;

import persistencia.*;
import customException.*;
import customExcpetion.CadenaLargaExcepcion;
import java.io.IOException;
import java.io.RandomAccessFile;
import entradaDatos.Consola;

public class Medicamento implements Grabable {

    // Variables miembro de la clase 	Tamaño total    Descripcion de las Variables 		Validacion
    private int codigoMedicamento;      // 4 bits		Codigo de Medicamento               Numerico > 0
    private String descripcion;         // 100 bits		Descripcion del medicamento 		Distinto de vacio
    private float precio;               // 4 bits 		Precio del medicamento 		       	Distinto de vacio
    private int cantStock;              // 4 bits 		Cantidad de Stock disponible 		Distinto de vacio
    private String fechaVencimiento;	// 16 bits 		Fecha de vencimiento 			    Distinto de vacio
    private char tipo;                  // 2 bits  		Tipo de medicamento 			    Distinto de P o J
                                        // 130 bits

    private static int TAMAREG = 131;
    private static int TAMARCHIVO = 100;

    /**
     * Constructor por defecto de la clase
     */
    public Medicamento() {
        this.codigoMedicamento = 0;
        this.descripcion = "";
        this.precio = 0;
        this.cantStock = 0;
        this.fechaVencimiento = "";
        this.tipo = ' ';
    }

    /**
     * El constructor solicita el ingreso de un Codigo de un Medicamento para ser almacenado, mientras
     * que las demas variables miembro en valores por default.
     */
    public Medicamento(int codigoMedicamento){
        this.codigoMedicamento = codigoMedicamento;
        this.descripcion = "";
        this.precio = 0;
        this.cantStock = 0;
        this.fechaVencimiento = "";
        this.tipo = ' ';
    }

    public Medicamento(int codigoMedicamento, String descripcion, float precio, int cantStock, String fechaVencimiento, char tipo, boolean estado) {
        this.codigoMedicamento = codigoMedicamento;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantStock = cantStock;
        this.fechaVencimiento = fechaVencimiento;
        this.tipo = tipo;
    }

    /**
     */
    public void setCodigoMedicamento(int codMed) throws NumeroNegativoExcepcion{
        if (codMed < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.codigoMedicamento = codMed;
    }

    public int getCodigoMedicamento() {
        return codigoMedicamento;
    }
    
    public void setDescripcion(String descripcion) throws CadenaVaciaExcepcion, CadenaLargaExcepcion{
        if (descripcion.length() > 50) {
            throw new CadenaLargaExcepcion();
        } else {
            if (descripcion.length() < 1) {
                throw new CadenaVaciaExcepcion();
            }
        }
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    public void setPrecio(float precio) throws NumeroNegativoExcepcion{
        if (precio < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.precio = precio;
    }
    
    public float getPrecio(){
        return precio;
    }
    
    public void setCantidadStock(int cantStock) throws NumeroNegativoExcepcion{
        if (cantStock < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.cantStock = cantStock;
    }
    
    public int getCantidadStock(){
        return cantStock;
    }
    
    public void setFechaVencimiento(String fechaVencimiento) throws CadenaVaciaExcepcion, CadenaLargaExcepcion{
        if (fechaVencimiento.length() < 0) {
            throw new CadenaVaciaExcepcion();
        } else {
            if (fechaVencimiento.length() > 8) {
                throw new CadenaVaciaExcepcion();
            }
        }
        this.fechaVencimiento = fechaVencimiento;
    }
    
    public String getFechaVencimiento(){
        return fechaVencimiento;
    }
    
    public void setTipo(char tipo) throws CadenaVaciaExcepcion, CadenaLargaExcepcion{
        String ax = tipo;
        if (ax.length() < 0) {
            throw new CadenaVaciaExcepcion();
        } else {
            if (ax.length() > 1) {
                throw new CadenaLargaExcepcion();
            }
        }
        this.tipo = tipo;
    }

    public char getTipo(){
        return tipo;
    }

    private void cargarCodigoMedicamento(){
        boolean flag = false;
        while(!flag){
            try {
                System.out.print("Codigo de Medicamento:");
                int axCod = Consola.readInt();
                setCodigoMedicamento(axCod);
                falg = true;
            } catch (NumberFormatException | NumeroNegativoExcepcion e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
    }

    private void cargarDescripcion(){
        boolean flag = false;
        while(!flag){
            try {
                System.out.print("Descripcion:");
                String axDes = Consola.readLine();
                setDescripcion(axDes);
                falg = true;
            } catch (CadenaVaciaExcepcion | CadenaLargaExcepcion e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
    }

    private void cargarPrecio(){
        boolean flag = false;
        while(!flag){
            try {
                System.out.print("Precio:");
                String axPre = Consola.readFloat();
                setPrecio(axPre);
                falg = true;
            } catch (NumberFormatException | NumeroNegativoExcepcion e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
    }

    private void cargarStock(){
        boolean flag = false;
        while(!flag){
            try {
                System.out.print("Stock:");
                String axStk = Consola.readInt();
                setCantidadStock(axStk);
                falg = true;
            } catch (NumberFormatException | NumeroNegativoExcepcion e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
    }

    private void cargarFechaVencimiento(){
        boolean flag = false;
        while(!flag){
            try {
                System.out.print("Fecha de Vencimiento:");
                String axFec = Consola.readLine();
                setFechaVencimiento(axFec);
                falg = true;
            } catch (CadenaVaciaExcepcion | CadenaLargaExcepcion e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
    }

    private void cargarTipo(){
        boolean flag = false;
        while(!flag){
            try {
                System.out.print("Tipo:");
                String axTipo = Consola.readLine();
                setTipo(axTipo);
                falg = true;
            } catch (CadenaVaciaExcepcion | CadenaLargaExcepcion e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
    }

    public void modificarDatos(){
        int op = -1;

        while(op != 0) {
            menuModificacion();
            int opChange = Consola.readInt();

            switch (opChange) {
                case 1: //Descripcion
                    cargarDescripcion();
                    break;
                case 2: //Precio
                    cargarPrecio();
                    break;
                case 3: //Stock
                    cargarStock();
                    break;
                case 4: //Fecha de Vencimiento
                    cargarFechaVencimiento();
                    break;
                case 5: //Tipo
                    cargarTipo();
                    break;
                default:
                    System.out.println("Operacion Invalida");
                    break;
            }
        }
    }

    private void menuModificacion(){
        Syste.out.println("          Menu de Modificacion");
        Syste.out.println("1) Descripcion");
        Syste.out.println("2) Precio");
        Syste.out.println("3) Stock");
        Syste.out.println("4) Fecha de Vencimiento");
        Syste.out.println("5) Tipo");
        Syste.out.println("6) Salir");
        Syste.out.print("Operacion:");
    }

    /**
     * Calcula el tamaño en bytes del objeto, tal como será grabado. Pedido por
     * Grabable
     *
     * @return el tamaño en bytes del objeto
     */
    @Override
    public int tamRegistro() {
        return TAMAREG;
    }

    /**
     * Devuelve la cantidad de registros que debe haber en el archivo. Pedido
     * por Grabable
     *
     * @return cantidad de registros
     */
    @Override
    public int tamArchivo() {
        return TAMARCHIVO;
    }

    /**
     * Indica cómo grabar un objeto. Pedido por Grabable.
     *
     * @param file el archivo donde será grabado el objeto
     */
    @Override
    public void grabar(RandomAccessFile file) {
        try {
            file.writeInt(codigoMedicamento);
            Registro.writeString(file, descripcion, 50);
            file.writeFloat(precio);
            file.writeInt(cantStock);
            Registro.writeString(file, fechaVencimiento, 8);
            file.writeChar(tipo);
        } catch (IOException e) {
            System.out.println("Error al grabar el registro: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Indica cómo leer un objeto. Pedido por Grabable.
     *
     * @param file el archivo donde se hará la lectura
     */
    @Override
    public void leer(RandomAccessFile file) {
        try {
            codigoMedicamento = file.readInt();
            descripcion = Registro.readString(file, 50).trim();
            precio = file.readFloat();
            cantStock = file.readInt();
            fechaVencimiento = Registro.readString(file, 8).trim();
            tipo = file.readChar();
        } catch (IOException e) {
            System.out.println("Error al leer el registro: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Redefinición del heredado desde Object. Considera que dos Articulos son
     * iguales si sus códigos lo son
     *
     * @param x el objeto contra el cual se compara
     * @return true si los códigos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object x) {
        if (x == null) {
            return false;
        }
        Medicamento file = (Medicamento) x;
        return (codigoMedicamento == file.getCodigoMedicamento());
    }

    /**
     * Redefinición del heredado desde Object. La convención es si equals() dice
     * que dos objetos son iguales, entonces hashCode() debería retornar el
     * mismo valor para ambos.
     *
     * @return el hashCode del Articulo. Se eligió el código para ese valor.
     */
    @Override
    public int hashCode() {
        return codigoMedicamento;
    }

    /**
     * Prints the attributes in a table format using String.format()
     */
    @Override
    public void mostrarRegistro() {
        // https://www.javatpoint.com/java-string-format
        // Los numeros entre el % y la mascara de tipo indican los caracteres que ocupa el dato en la salida
        // En el caso del float, el que esta antes del punto indica la cantidad total de digitos del float
        // y el que esta despues del punto, cuantos de esos digitos van a ser decimales
        System.out.println(String.format("| %6d | %-52s | %10d | $%8.2f | %6d | %-10s | %3c |", codigo, descripcion, precio, cantStock, fechaVencimiento, tipo));
    }

    @Override
    public void cargarDatos() {
        cargarDescripcion();
        cargarPrecio();
        cargarStock();
        cargarFechaVencimiento();
        cargarTipo();
    }
    
}
