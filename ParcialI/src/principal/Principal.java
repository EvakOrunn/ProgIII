/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal;

import entradaDatos.Consola;

/**
 *
 * @author mayocca
 */
public class Principal {

    public static void main(String[] args) {
        int op;

        // Inicializar los archivos
        CategoriaMenu.inicializarArchivo();
        VehiculoMenu.inicializarArchivo();

        do {
            System.out.println("----------------------------------");
            System.out.println("          MENU PRINCIPAL          ");
            System.out.println("----------------------------------");
            System.out.println("-1. Menu de Categoria            -");
            System.out.println("-2. Menu de Vehiculo             -");
            System.out.println("-0. Salir                        -");
            System.out.println("----------------------------------");
            System.out.print("--> ");
            op = Consola.readInt();
            switch (op) {
                case 1:
                    CategoriaMenu.menu();
                    break;
                case 2:
                    VehiculoMenu.menu();
                    break;
            }
        } while (op != 0);
    }
}
