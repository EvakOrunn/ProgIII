package principal;

import persistencia.*;
import datos.*;
import entradaDatos.*;

public class Principal {
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
        int caracteres = 88; // Caracteres de la tabla. Cambiar para ajustar ancho de lineas
        System.out.println("-".repeat(caracteres)); // Se puede llamar a un metodo sobre el string ya que es un objeto
        System.out.println("| Codigo | Descripcion\t\t\t\t\t      | Cod. Rubro | Precio    |");
        System.out.println("-".repeat(caracteres));
        m2.abrirParaLectura();
        m2.irPrincipioArchivo();
        while (!m2.eof()) {
            Registro reg = m2.leerRegistro();
            if (reg.getActivo()) {
                reg.mostrarRegistro(); // La clase Articulo se encarga de dar el formato de cada articulo
                contador++;
            }
        }
        m2.cerrarArchivo();
        System.out.println("-".repeat(caracteres));
        System.out.print(String.format("| Cantidad: %6d", contador));
        System.out.print(" ".repeat(caracteres - 19));
        System.out.println("|");
        System.out.println("-".repeat(caracteres));
        Consola.pausa();
    }

    /**
     * Carga un registro de Articulo por teclado
     */
    public static Registro leerArticulo() {
        Medicamento art = new Medicamento();
        int codigo;
        do {
            System.out.print("Codigo--> ");
            codigo = Consola.readInt();
            if (obtenerArticulo(codigo) != null) {
                System.out.println("Codigo ya existente.");
                codigo = -1; // Invalidar codigo si ya existe el articulo, para que vuelva a pedir otro
                             // codigo
            }
        } while (codigo < 0 || codigo > art.tamArchivo());
        art.setCodigoMedicamento(codigo);
        art.cargarDatos();
        Registro reg = new Registro(art, art.getCodigo()); // Aqui es donde se indica que la clave principal es "Codigo"
        return reg;
    }

    public static Medicamento obtenerArticulo(int codigo) {
        m2.abrirParaLectura();
        m2.buscarRegistro(codigo);
        if (m2.eof()) {
            return null;
        }
        Registro reg = m2.leerRegistro();
        if (!reg.getActivo()) {
            return null; // El registro no esta activo
        }
        Medicamento art = (Medicamento) reg.getDatos();
        m2.cerrarArchivo();

        return art;
    }

    public static void cargarArticulos() {
        m2.abrirParaLeerEscribir();
        try {
            do {
                Registro reg = leerArticulo();
                m2.cargarUnRegistro(reg);
            } while (Consola.deseaContinuar());
        } catch (Exception e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
            System.exit(1);
        }
        m2.cerrarArchivo();
    }

    public static void bajaArticulos() {
        Registro reg = new Registro(new Medicamento(), 0);
        reg.cargarNroOrden();
        m2.bajaRegistro(reg);
        Consola.pausa();
    }

    public static void modificarArticulo() {
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
        Medicamento art = (Medicamento) reg.getDatos();
        art.modificarDatos();
        reg.setDatos(art);
        m2.cargarUnRegistro(reg); // Sobreescribe el registro ya existente

        m2.cerrarArchivo();

        System.out.println("Registro modificado");
        Consola.pausa();
    }

    public static void main(String[] args) {
        try {
            m2 = new Archivo("Articulos.dat", new Medicamento());
        } catch (ClassNotFoundException e) {
            System.out.println("Error al crear los descriptores de archivos: " + e.getMessage());
            System.exit(1);
        }

        int op;
        Registro reg;

        do {
            menuPrincipal();
            op = Consola.readInt();
            switch (op) {
                case 1:
                    cargarArticulos();
                    break;
                case 2:
                    bajaArticulos();
                    break;
                case 3:
                    modificarArticulo();
                    break;
                case 4:
                    listar();
                    break;
            }
        } while (op != 0);
    }

    private static void menuPrincipal(){
        System.out.println("--------------------------------------------");
        System.out.println("-              Menu Principal              -");
        System.out.println("--------------------------------------------");
        System.out.println("- 1) Alta de Medicamento                   -");
        System.out.println("- 2. Baja de Medicamento (lógica)          -");
        System.out.println("- 3. Modificacion de Medicamento           -");
        System.out.println("- 4. Listar Medicamentos activos           -");
        System.out.println("- 0. Salir                                 -");
        System.out.println("--------------------------------------------");
        System.out.print("--> ");
    }
}