package datos;

import java.util.Objects;

import entradaDatos.Consola;
import persistencia.*;
import java.io.*;
import excepciones.*;

/**
 *
 * @author Alumnos
 */
public class Categoria implements Grabable{
    
    private int codigoCategoria;
    private String descripcion;
    
    private static int TAMAREG = 64;
    private static int TAMARCHIVO = 100;

    public Categoria() {
        this.codigoCategoria = -1;
        this.descripcion = "";
    }

    public Categoria(int codigoCategoria, String descripcion) {
        this.codigoCategoria = codigoCategoria;
        this.descripcion = descripcion;
    }

    public int getCodigoCategoria() {
        return codigoCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setCodigoCategoria(int codigoCategoria) throws NumeroNegativoExcepcion {
        if (codigoCategoria < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.codigoCategoria = codigoCategoria;
    }

    public void setDescripcion(String descripcion) throws CadenaLargaExcepcion, CadenaVaciaExcepcion {
        if (descripcion.trim().length() <= 0) {
            throw new CadenaVaciaExcepcion();
        } else {
            if (descripcion.trim().length() > 30) {
                throw new CadenaLargaExcepcion();
            }
        }
        this.descripcion = descripcion;
    }
    
    private void cargarDescripcion() {
        boolean flag = false;
        while (!flag) {            
            try {
                System.out.print("Descripcion:");
                String axDes = Consola.readLine();
                setDescripcion(axDes);
                flag = true;
            } catch (CadenaLargaExcepcion | CadenaVaciaExcepcion e) {
                System.out.println("Error al setear la descripcion:" + e.getMessage());
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.codigoCategoria;
        hash = 29 * hash + Objects.hashCode(this.descripcion);
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
        final Categoria other = (Categoria) obj;
        if (this.codigoCategoria != other.codigoCategoria) {
            return false;
        }
        return Objects.equals(this.descripcion, other.descripcion);
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
            a.writeInt(this.codigoCategoria);
            Registro.writeString(a, this.descripcion.trim(), 30);
        } catch (IOException e) {
            System.out.println("Error la grabar los datos de la Categoria:" + e.getMessage());
        }
    }

    @Override
    public void leer(RandomAccessFile a) {
        try {
            this.codigoCategoria = a.readInt();
            this.descripcion = Registro.readString(a, 30).trim();
        } catch (IOException e) {
            System.out.println("Error al leer los datos de la Categoria:" + e.getMessage());
        }
    }

    @Override
    public void mostrarRegistro() {
        System.out.println(String.format("| %6d | %30s |", this.codigoCategoria, this.descripcion));
    }

    @Override
    public void cargarDatos() {
        cargarDescripcion();
    }
    
}
