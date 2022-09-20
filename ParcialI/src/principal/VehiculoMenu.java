package principal;

import persistencia.*;
import datos.*;
import entradaDatos.*;
import excepciones.NumeroNegativoExcepcion;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alumnos
 */
public class VehiculoMenu {
    private static Archivo m2;

    /**
     * Muestra el contenido de un archivo (incluidos los registros marcados como
     * borrados) es de utilidad para verificar el contenido del archivo a medida
     * que vamos avanzando en la resolución de modo de controlar como estan
     * cargados los registros incluyendo los vacios o los borrados
     */
    public static void mostrarTodo() {
        Registro reg;
        m2.abrirParaLeerEscribir();
        m2.irPrincipioArchivo();
        while (!m2.eof()) {
            reg = m2.leerRegistro();
            reg.mostrarRegistro();
        }
        m2.cerrarArchivo();
    }

    public static void listar() {
        int contador = 0;
        int caracteres = 63; // Caracteres de la tabla. Cambiar para ajustar ancho de lineas
        System.out.println(Consola.repeat("-", caracteres)); // Se puede llamar a un metodo sobre el string ya que es un objeto
        System.out.println("| Num.V  | Cat.   | Denominacion                   | Precio   |");
        System.out.println(Consola.repeat("-", caracteres));
        m2.abrirParaLectura();
        
        m2.irPrincipioArchivo();
        while (!m2.eof()) {
            Registro reg = m2.leerRegistro();
            if (reg.getActivo()) {
                reg.mostrarRegistro(); // La clase Vuelo se encarga de dar el formato de cada articulo
                contador++;
            }
        }
        m2.cerrarArchivo();
        System.out.println(Consola.repeat("-", caracteres));
        System.out.print(String.format("| Cantidad: %6d", contador));
        System.out.print(Consola.repeat(" ", caracteres - 19));
        System.out.println("|");
        System.out.println(Consola.repeat("-", caracteres));
        Consola.pausa();
    }
    
    public static void listarPorCategoria() {
        
        if (CategoriaMenu.cantidad() == 0) {
            System.out.println("No hay destinos disponibles.");
            Consola.pausa();
            return;
        }
        m2.abrirParaLectura();
        m2.irPrincipioArchivo();
        
        int codigoCategoria;
        do {
            System.out.print("Codigo de destino--> ");
            codigoCategoria = Consola.readInt();
        } while (DestinosMenu.obtener(codigoCategoria) == null); // Repetir hasta que se encuentre destino
        
        int contador = 0;
        int caracteres = 67; // Caracteres de la tabla. Cambiar para ajustar ancho de lineas
        System.out.println(Consola.repeat("-", caracteres)); // Se puede llamar a un metodo sobre el string ya que es un objeto
        System.out.println("| Codigo | Cod. Dst. |  Fecha   |  Hora  |  Tarifa   | Cant. Dias |");
        System.out.println(Consola.repeat("-", caracteres));
        m2.abrirParaLectura();
        m2.irPrincipioArchivo();
        while (!m2.eof()) {
            Registro reg = m2.leerRegistro(); // Leo del archivo
            if (reg.getActivo()) { // Pregunto si esta activo
                Vuelo v = (Vuelo) reg.getDatos(); // Obtengo los datos
                if (v.getCodigoDestino() == codigoCategoria) { // Pregunto si es hacia el destino que quiero
                    reg.mostrarRegistro(); // La clase Vuelo se encarga de dar el formato
                    contador++;
                }
            }
        }
        m2.cerrarArchivo();
        System.out.println(Consola.repeat("-", caracteres));
        System.out.print(String.format("| Cantidad: %6d", contador));
        System.out.print(Consola.repeat(" ", caracteres - 19));
        System.out.println("|");
        System.out.println(Consola.repeat("-", caracteres));
        Consola.pausa();
    }

    /**
     * Carga un registro de Vuelo por teclado
     */
    public static Registro leer() {
        Vehiculo vuelo = new Vehiculo();
        int codigo;
        do {
            System.out.print("Codigo Vehiculo--> ");
            codigo = Consola.readInt();
            if (obtener(codigo) != null) {
                System.out.println("Codigo ya existente.");
                codigo = -1;
            }
        } while (codigo < 0 || codigo > vuelo.tamArchivo());
        try {
            vuelo.setNumVehiculo(codigo);
        } catch (NumeroNegativoExcepcion ex) {
            Logger.getLogger(VuelosMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        vuelo.cargarDatos();
        Registro reg = new Registro(vuelo, vuelo.getNumVehiculo()); // Aqui es donde se indica que la clave principal es "Codigo"
        return reg;
    }

    public static Vehiculo obtener(int codigo) {
        m2.abrirParaLectura();
        m2.buscarRegistro(codigo);
        if (m2.eof()) {
            return null;
        }
        Registro reg = m2.leerRegistro();
        if (!reg.getActivo()) {
            return null; // El registro no esta activo
        }
        Vehiculo art = (Vehiculo) reg.getDatos();
        m2.cerrarArchivo();

        return art;
    }

    public static void cargar() {
        if (CategoriaMenu.cantidad() == 0) {
            System.out.println("No hay destinos disponibles.");
            Consola.pausa();
            return;
        }

        m2.abrirParaLeerEscribir();
        try {
            do {
                Registro reg = leer();
                m2.cargarUnRegistro(reg);
            } while (Consola.deseaContinuar());
        } catch (Exception e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
            System.exit(1);
        }
        m2.cerrarArchivo();
    }

    public static void baja() {
        Registro reg = new Registro(new Vehiculo(), 0);
        reg.cargarNroOrden();
        m2.bajaRegistro(reg);
        Consola.pausa();
    }

    public static void modificar() {
        System.out.print("Codigo a modificar--> ");
        int codigo = Consola.readInt();

        m2.abrirParaLeerEscribir();
        m2.buscarRegistro(codigo);
        if (m2.eof()) {
            System.out.println("No existe el registro");
            Consola.pausa();
            return;
        }

        Registro reg = m2.leerRegistro(); // Lee el registro existente
        Vehiculo art = (Vehiculo) reg.getDatos();
        art.cargarDatos();
        reg.setDatos(art);
        m2.cargarUnRegistro(reg); // Sobreescribe el registro ya existente

        m2.cerrarArchivo();

        System.out.println("Registro modificado");
        Consola.pausa();
    }

    public static void inicializarArchivo() {
        try {
            m2 = new Archivo("Vehiculo.dat", new Vehiculo());
            if (!m2.getFd().exists()){
                m2.crearArchivoVacio(new Registro(new Vehiculo(), 0));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error al crear los descriptores de archivos: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void menu() {
        int op;

        do {
            System.out.println(Consola.repeat("-", 45));
            System.out.println(Consola.repeat(" ", 12) + "MENU DE VEHICULOS" + Consola.repeat(" ", 12));
            System.out.println(Consola.repeat("-", 45));
            System.out.println("-1. Alta de vehiculo" + Consola.repeat(" ", 24) + "-");
            System.out.println("-2. Baja de vehiculo" + Consola.repeat(" ", 24) + "-");
            System.out.println("-3. Modificacion de vehiculos" + Consola.repeat(" ", 15) + "-");
            System.out.println("-4. Listar vehiculos activos" + Consola.repeat(" ", 16) + "-");
            System.out.println("-5. Listar vehiculos por categoria" + Consola.repeat(" ", 10) + "-");
            System.out.println("-0. Salir" + Consola.repeat(" ", 35) + "-");
            System.out.println(Consola.repeat("-", 45));
            System.out.print("--> ");
            op = Consola.readInt();
            switch (op) {
                case 1:
                    cargar();
                    break;
                case 2:
                    baja();
                    break;
                case 3:
                    modificar();
                    break;
                case 4:
                    listar();
                    break;
                case 5:
                    listarPorCategoria();
                    break;
                case 6:
                    mostrarTodo();
                case 7:
                    
                    break;
            }
        } while (op != 0);
    }
}
