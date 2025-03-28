/**@author Hugo Pérez */
package utils;

import java.util.Scanner;

public class Menu {
    
    private final Scanner sc = new Scanner(System.in);
    
    public int mostrarMenu(String rutaCarpeta, String[] contenidoCarpeta, String ficheroActual) {
        System.out.println("Conversor de archivos");
        System.out.println("Carpeta seleccionada: " +
        (rutaCarpeta != null ? rutaCarpeta : "Ninguna"));
        System.out.println("Contenido de la carpeta");
        if (contenidoCarpeta != null  && contenidoCarpeta.length > 0) {
            for (String fichero : contenidoCarpeta) {
                System.out.println(" - " + fichero);
            }
        } else {
            System.out.println("La carpeta está vacía");
        }
        System.out.println("Fichero seleccionado: " +
           (ficheroActual != null ? ficheroActual : "Ninguno"));
        System.out.println("Opciones:");
        System.out.println("1. Seleccionar carpeta");
        System.out.println("2. Seleccionar fichero");
        System.out.println("3. Convertir");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
        return leerEnteroSeguro();
        }

    public String pedirRutaCarpeta() {    
        System.out.print("Introduzca la ruta de la carpeta: ");
        return sc.nextLine();
    }

    public String pedirNombreFichero() {
        System.out.print("Introduzca el nombre del fichero: ");
        return sc.nextLine();
    }
    
    public String pedirFormatoDestino() {
        System.out.print("Selecciona el formato de salida (csv/json/xml): ");
        return sc.nextLine().trim().toLowerCase();
    }

    public String pedirNombreArchivoSalida() {
        System.out.print("Introduce el nombre del archivo de salida (sin extensión): ");
        return sc.nextLine().trim();
    }

    public int leerEnteroSeguro() {
        int opcion;
        do {
            try {
                opcion = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida. Introduzca un número válido.");
            }
        } while (true);
        return opcion;
    }
}
