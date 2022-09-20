package datos;

import entradaDatos.Consola;
import excepciones.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Objects;
import persistencia.*;
import principal.CategoriaMenu;

/**
 *
 * @author Alumnos
 */
public class Vehiculo implements Grabable {

    private int numVehiculo;
    private int codigoCategoria;
    private String denominacion;
    private long precio;

    private static int TAMAREG = 76;
    private static int TAMARCHIVO = 100;

    public Vehiculo() {
        this.numVehiculo = -1;
        this.codigoCategoria = -1;
        this.denominacion = "";
        this.precio = -1;
    }

    public Vehiculo(int numVehiculo, int codigoCategoria, String denominacion, long precio) {
        this.numVehiculo = numVehiculo;
        this.codigoCategoria = codigoCategoria;
        this.denominacion = denominacion;
        this.precio = precio;
    }

    public int getNumVehiculo() {
        return numVehiculo;
    }

    public int getCodigoCategoria() {
        return codigoCategoria;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public long getPrecio() {
        return precio;
    }
    
    public void setNumVehiculo(int numVehiculo) throws NumeroNegativoExcepcion {
        if (numVehiculo < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.numVehiculo = numVehiculo;
    }
    
    public void setCodigoCategoria(int codigoCategoria) throws NumeroNegativoExcepcion {
        if (codigoCategoria < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.codigoCategoria = codigoCategoria;
    }

    public void setDenominacion(String denominacion) throws CadenaLargaExcepcion, CadenaVaciaExcepcion {
        if (denominacion.trim().length() <= 0) {
            throw new CadenaVaciaExcepcion();
        } else {
            if (denominacion.trim().length() > 30) {
                throw new CadenaLargaExcepcion();
            }
        }
        this.denominacion = denominacion;
    }

    public void setPrecio(long precio) throws NumeroNegativoExcepcion {
        if (precio < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.precio = precio;
    }
    
    private void cargarDenominacion() {
        boolean flag = false;
        while (!flag) {            
            try {
                System.out.print("Denominacion:");
                String axDenom = Consola.readLine();
                setDenominacion(axDenom);
                flag = true;
            } catch (CadenaLargaExcepcion | CadenaVaciaExcepcion e) {
                System.out.println("Error al setear la Denominacion:" + e.getMessage());
            }
        }
    }
    
    private void cargarPrecio() {
        boolean flag = false;
        while (!flag) {            
            try {
                System.out.print("Precio:");
                long axPre = Consola.readLong();
                setPrecio(axPre);
                flag = true;
            } catch (NumeroNegativoExcepcion | NumberFormatException e) {
                System.out.println("Error al setear el Precio:" + e.getMessage());
            }
        }
    }
    
    private void cargarCategoria() {
        int axCod;
        do {            
            System.out.print("Codigo de Categoria:");
            axCod = Consola.readInt();
        } while (CategoriaMenu.obtener(axCod) == null);
        this.codigoCategoria = axCod;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.numVehiculo;
        hash = 47 * hash + this.codigoCategoria;
        hash = 47 * hash + Objects.hashCode(this.denominacion);
        hash = 47 * hash + (int) (this.precio ^ (this.precio >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vehiculo other = (Vehiculo) obj;
        if (this.numVehiculo != other.numVehiculo) {
            return false;
        }
        if (this.codigoCategoria != other.codigoCategoria) {
            return false;
        }
        if (this.precio != other.precio) {
            return false;
        }
        return Objects.equals(this.denominacion, other.denominacion);
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
            a.writeInt(this.numVehiculo);
            a.writeInt(this.codigoCategoria);
            Registro.writeString(a, this.denominacion, 30);
            a.writeLong(this.precio);
        } catch (IOException e) {
            System.out.println("Error al grabar los datos del Vehiculo:" + e.getMessage());
        }
    }

    @Override
    public void leer(RandomAccessFile a) {
        try {
            this.numVehiculo = a.readInt();
            this.codigoCategoria = a.readInt();
            this.denominacion = Registro.readString(a, 30).trim();
            this.precio = a.readLong();
        } catch (IOException e) {
            System.out.println("Error al leer los datos del Vehivulo:" + e.getMessage());
        }
    }

    @Override
    public void mostrarRegistro() {
        System.out.println(String.format("| %6d | %6d | %30s | %8d |", this.numVehiculo, this.codigoCategoria, this.denominacion, this.precio));
    }

    @Override
    public void cargarDatos() {
        cargarDenominacion();
        cargarPrecio();
        cargarCategoria();
    }

}
