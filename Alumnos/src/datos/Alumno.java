package datos;

import entradaDatos.Consola;
import java.io.*;
import persistencia.*;
import excepciones.*;

public class Alumno implements Grabable{

	private int dni;
	private String nombreAlumno;
	private float saldo;
	private char estadoCuenta;
	private int codigoCurso;

	private static int TAMAREG = 114;
	private static int TAMARCHIVO = 100;

	public Alumno() {
		this.dni = -1;
		this.nombreAlumno = "";
		this.saldo = 0;
		this.estadoCuenta = 'A';
		this.codigoCurso = -1;
	}

	public Alumno(int dni, String nombreAlumno, float saldo, char estadoCuenta, int codigoCurso) {
		this.dni = dni;
		this.nombreAlumno = nombreAlumno;
		this.saldo = saldo;
		this.estadoCuenta = estadoCuenta;
		this.codigoCurso = codigoCurso;
	}

	public void setDNI(int dni) {
		this.dni = dni;
	}

	public void setNombreAlumno(String nombreAlumno) {
		this.nombreAlumno = nombreAlumno;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public void setEstadoCuenta(char estadoCuenta) {
		this.estadoCuenta = estadoCuenta;
	}

	public void setCodigoCurso(int codigoCurso) {
		this.codigoCurso = codigoCurso;
	}

	public int getDNI() {
		return dni;
	}

	public String getNombreAlumno() {
		return nombreAlumno;
	}

	public float getSaldo() {
		return saldo;
	}

	public char getEstadoCuenta() {
		return estadoCuenta;
	}

	public int codigoCurso() {
		return codigoCurso;
	}

	private void cargarNombreAlumno() {
		boolean flag = false;
		while(!flag) {
			try {
				System.out.print("Apellido y Nombre:");
				String axNom = Consola.readLine();

			} 
		}
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
     * Devuelve la cantidad de registros que debe haber en el archivo. Pedido por
     * Grabable
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
            file.writeInt(dni);
            file.writeInt(codigoCurso);
            Registro.writeString(file, nombreAlumno, 50);
            file.writeFloat(saldo);
            file.writeChar(estadoCuenta);
        } catch (IOException e) {
            System.out.println("Error al grabar el Alumno: " + e.getMessage());
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
            dni = file.readInt();
            codigoCurso = file.readInt();
            nombreAlumno = Registro.readString(file, 50).trim();
            saldo = file.readFloat();
            estadoCuenta = file.readChar();
        } catch (IOException e) {
            System.out.println("Error al leer el Alumno: " + e.getMessage());
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

        Vuelo file = (Vuelo) x;
        return (codigo == file.codigo);
    }

    /**
     * Redefinición del heredado desde Object. La convención es si equals() dice
     * que dos objetos son iguales, entonces hashCode() debería retornar el
     * mismo valor para ambos.
     *
     * @return el hashCode del Vuelo. Se eligió el código para ese valor.
     */
    @Override
    public int hashCode() {
        return codigo;
    }

    /**
     * Prints the attributes in a table format using String.format()
     */
    @Override
    public void mostrarRegistro() {
        // https://www.javatpoint.com/java-string-format
        // Los numeros entre el % y la mascara de tipo indican los caracteres que ocupa
        // el dato en la salida
        // En el caso del float, el que esta antes del punto indica la cantidad total de
        // digitos del float
        // y el que esta despues del punto, cuantos de esos digitos van a ser decimales
        System.out.println(String.format("| %6d | %9d | %8s | %6d | $%8.2f | %10d |", codigo, codigoDestino, fecha, hora,
                tarifa, cantidadDias));
    }
    
    @Override
    public void cargarDatos() {
        cargarCodigoDestino();
        cargarFecha();
        cargarHora();
        cargarTarifa();
        cargarCantidadDias();
    }

}