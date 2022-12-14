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
public class CategoriaMenu {
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

    public static int cantidad() {
        m2.abrirParaLectura();
        m2.irPrincipioArchivo();
        int contador = 0;
        while (!m2.eof()) {
            Registro reg = m2.leerRegistro();
            if (reg.getActivo()) {
                contador++;
            }
        }
        m2.cerrarArchivo();
        return contador;
    }

    public static void listar() {
        int contador = 0;
        int caracteres = 63; // Caracteres de la tabla. Cambiar para ajustar ancho de lineas
        System.out.println(Consola.repeat("-", caracteres)); // Se puede llamar a un metodo sobre el string ya que es un objeto
        System.out.println("| Codigo | Descripcion\t\t\t\t\t      |");
        System.out.println(Consola.repeat("-", caracteres));
        m2.abrirParaLectura();
        m2.irPrincipioArchivo();
        while (!m2.eof()) {
            Registro reg = m2.leerRegistro();
            if (reg.getActivo()) {
                reg.mostrarRegistro(); // La clase Destino se encarga de dar el formato de cada articulo
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

    /**
     * Carga un registro de Destino por teclado
     */
    public static Registro leer() {
        Categoria art = new Categoria();
        int codigo;
        do {
            System.out.print("Codigo Categoria--> ");
            codigo = Consola.readInt();
            if (obtener(codigo) != null) {
                System.out.println("Codigo ya existente.");
                codigo = -1; // Invalidar codigo si ya existe el articulo, para que vuelva a pedir otro
                             // codigo
            }
        } while (codigo < 0 || codigo > art.tamArchivo());
        try {
            art.setCodigoCategoria(codigo);
        } catch (NumeroNegativoExcepcion ex) {
            Logger.getLogger(CategoriaMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        art.cargarDatos();
        Registro reg = new Registro(art, art.getCodigoCategoria()); // Aqui es donde se indica que la clave principal es "Codigo"
        return reg;
    }

    public static Categoria obtener(int codigo) {
        m2.abrirParaLectura();
        m2.buscarRegistro(codigo);
        if (m2.eof()) {
            return null;
        }
        Registro reg = m2.leerRegistro();
        if (!reg.getActivo()) {
            return null; // El registro no esta activo
        }
        Categoria art = (Categoria) reg.getDatos();
        m2.cerrarArchivo();

        return art;
    }
    
    public static Categoria[] obtenerCategorias() {
        Categoria[] carArr = new Categoria[cantidad()];
        int index = 0;
        m2.abrirParaLectura();
        while (!m2.eof()) {            
            Registro reg = m2.leerRegistro();
            if (reg.getActivo()) {
                Categoria cat = (Categoria) reg.getDatos();
                carArr[index] = cat;
                index++;
            }
        }
        return carArr;
    }

    public static void cargar() {
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
        Registro reg = new Registro(new Categoria(), 0);
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
        Categoria art = (Categoria) reg.getDatos();
        art.cargarDatos();
        reg.setDatos(art);
        m2.cargarUnRegistro(reg); // Sobreescribe el registro ya existente

        m2.cerrarArchivo();

        System.out.println("Registro modificado");
        Consola.pausa();
    }

    public static void inicializarArchivo() {
        try {
            m2 = new Archivo("Categoria.dat", new Categoria());
            if (!m2.getFd().exists()){
                m2.crearArchivoVacio(new Registro(new Categoria(), 0));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error al crear los descriptores de archivos: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void menu() {
        int op;
        Registro reg;
        
        inicializarArchivo();
        
        do {
            System.out.println(Consola.repeat("-", 33));
            System.out.println("-" + Consola.repeat(" ", 9) + "MENU DE DESTINOS" + Consola.repeat(" ", 9) + "-");
            System.out.println(Consola.repeat("-", 33));
            System.out.println("-1.Alta de categoria" + Consola.repeat(" ", 12) + "-");
            System.out.println("-2.Baja de categoria" + Consola.repeat(" ", 12) + "-");
            System.out.println("-3.Modificacion de categoria" + Consola.repeat(" ", 3) + "-");
            System.out.println("-4.Listar" + Consola.repeat(" ", 23) + "-");
            System.out.println("-0.Salir" + Consola.repeat(" ", 24) + "-");
            System.out.println(Consola.repeat("-", 33));
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
            }
        } while (op != 0);
    }
}
