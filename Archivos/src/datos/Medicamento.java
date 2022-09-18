package datos;

import entradaDatos.*;
import customException.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.*;

public class Medicamento implements Grabable {

    private int codigoMedicamento;
    private String descripcion;
    private float precio;
    private int cantStock;
    private String fechaVenc;
    private char tipo;

    private static int TAMAREG = 130;
    private static int TAMARCHIVO = 100;

    /**
     * Constructor por defecto de la clase
     */
    public Medicamento() {
        this.codigoMedicamento = 0;
        this.descripcion = "";
        this.precio = 0;
        this.cantStock = 0;
        this.fechaVenc = "";
        this.tipo = ' ';
    }

    /**
     * El constructor solicita el ingreso de un Codigo de un Medicamento para
     * ser almacenado, mientras que las demas variables miembro en valores por
     * default.
     *
     * @param codigoMedicamento
     */
    public Medicamento(int codigoMedicamento) {
        this.codigoMedicamento = codigoMedicamento;
        this.descripcion = "";
        this.precio = 0;
        this.cantStock = 0;
        this.fechaVenc = "";
        this.tipo = ' ';
    }

    public Medicamento(int codigoMedicamento, String descripcion, float precio, int cantStock, String fechaVencimiento, char tipo, boolean estado) {
        this.codigoMedicamento = codigoMedicamento;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantStock = cantStock;
        this.fechaVenc = fechaVencimiento;
        this.tipo = tipo;
    }

    /**
     * @param codMed
     */
    public void setCodigoMedicamento(int codMed) throws NumeroNegativoExcepcion {
        if (codMed < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.codigoMedicamento = codMed;
    }

    public int getCodigoMedicamento() {
        return codigoMedicamento;
    }

    public void setDescripcion(String descripcion) throws CadenaVaciaExcepcion, CadenaLargaExcepcion {
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

    public void setPrecio(float precio) throws NumeroNegativoExcepcion {
        if (precio < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.precio = precio;
    }

    public float getPrecio() {
        return precio;
    }

    public void setCantidadStock(int cantStock) throws NumeroNegativoExcepcion {
        if (cantStock < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.cantStock = cantStock;
    }

    public int getCantidadStock() {
        return cantStock;
    }

    public void setFechaVencimiento(String fechaVencimiento) throws CadenaLargaExcepcion, CadenaVaciaExcepcion {
        if (fechaVencimiento.length() < 0) {
            throw new CadenaVaciaExcepcion();
        } else {
            if (fechaVencimiento.length() > 8) {
                throw new CadenaLargaExcepcion();
            }
        }
        this.fechaVenc = fechaVencimiento;
    }

    public String getFechaVencimiento() {
        return fechaVenc;
    }

    public void setTipo(char tipo) throws CadenaVaciaExcepcion, CadenaLargaExcepcion, TipoIncorrectoExcepcion {
        String ax = String.valueOf(tipo);
        if (ax.length() < 0) {
            throw new CadenaVaciaExcepcion();
        } else {
            if (ax.length() > 1) {
                throw new CadenaLargaExcepcion();
            }
        }
        this.tipo = tipo;
    }

    public char getTipo() {
        return tipo;
    }

    private void cargarDescripcion() {
        boolean flag = false;
        while (!flag) {
            try {
                System.out.print("Descripcion:");
                String axDes = Consola.readLine();
                setDescripcion(axDes);
                flag = true;
            } catch (CadenaVaciaExcepcion | CadenaLargaExcepcion e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
    }

    private void cargarPrecio() {
        boolean flag = false;
        while (!flag) {
            try {
                System.out.print("Precio:");
                float axPre = Consola.readFloat();
                setPrecio(axPre);
                flag = true;
            } catch (NumberFormatException | NumeroNegativoExcepcion e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
    }

    private void cargarStock() {
        boolean flag = false;
        while (!flag) {
            try {
                System.out.print("Stock:");
                int axStk = Consola.readInt();
                setCantidadStock(axStk);
                flag = true;
            } catch (NumberFormatException | NumeroNegativoExcepcion e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
    }

    private void cargarFechaVencimiento() {
        boolean flag = false;
        while (!flag) {
            try {
                System.out.println("Fecha de Vencimiento:");
                String axFec = Consola.readLine();
                setFechaVencimiento(axFec);
                flag = true;
            } catch (CadenaLargaExcepcion | CadenaVaciaExcepcion e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
    }

    private void cargarTipo() {
        boolean flag = false;
        while (!flag) {
            try {
                System.out.print("Tipo:");
                String axTipo = Consola.readLine();
                Character.toUpperCase(axTipo.charAt(0));
                if (validarTipoMedicamento(axTipo.charAt(0))) {
                    setTipo(axTipo.charAt(0));
                    flag = true;
                } else {
                    System.out.println("Tipo invalido");
                    cargarTipo();
                }
            } catch (CadenaVaciaExcepcion | CadenaLargaExcepcion | TipoIncorrectoExcepcion e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
    }

    public void modificarDatos() {
        int op = -1;

        do {
            menuModificacion();
            op = Consola.readInt();

            switch (op) {
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
                case 0:
                    System.out.println("Operacion Finalizada");
                    break;
                default:
                    System.out.println("Operacion Invalida");
                    break;
            }
        } while (op != 0);
    }

    private void menuModificacion() {
        System.out.println("          Menu de Modificacion");
        System.out.println("1) Descripcion");
        System.out.println("2) Precio");
        System.out.println("3) Stock");
        System.out.println("4) Fecha de Vencimiento");
        System.out.println("5) Tipo");
        System.out.println("0) Salir");
        System.out.print("Operacion:");
    }

    private boolean validarTipoMedicamento(char tipoV) {
        return (Character.compare(tipoV, 'P') != 0 || Character.compare(tipoV, 'J') != 0);
    }

    @Override
    public int tamRegistro() {
        return TAMAREG;
    }

    @Override
    public int tamArchivo() {
        return TAMARCHIVO;
    }

    @Override
    public void grabar(RandomAccessFile a) {
        try {
            a.writeInt(this.codigoMedicamento);
            Registro.writeString(a, this.descripcion, 50);
            Registro.readString(a, 8);
            a.writeFloat(this.precio);
            a.writeInt(this.cantStock);
            a.writeChar(this.tipo);
        } catch (IOException ex) {
            System.out.println("Error al grabar los datos del medicamento:" + ex.getMessage());
            Logger.getLogger(Medicamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void leer(RandomAccessFile a) {
        try {
            this.codigoMedicamento = a.readInt();
            this.descripcion = Registro.readString(a, 50);
            this.fechaVenc = Registro.readString(a, 8);
            this.precio = a.readFloat();
            this.cantStock = a.readInt();
            this.tipo = a.readChar();
        } catch (IOException e) {
            System.out.println("Error al leer los datos del Medicamento:" + e.getMessage());
        }
    }

    @Override
    public void mostrarRegistro() {
        System.out.println(String.format("| %6d | %9d | %50s | %8s | $%8.2f | %3c |", this.codigoMedicamento, this.cantStock, this.descripcion, this.fechaVenc,this.precio, this.tipo));
    }

    @Override
    public void cargarDatos() {
        cargarDescripcion();
        cargarFechaVencimiento();
        cargarPrecio();
        cargarStock();
        cargarTipo();
    }

}
