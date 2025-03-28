/**@author Hugo Pérez */
import utils.*;

import java.io.File;
import java.util.List;
import java.util.LinkedHashMap;

public class Main {

    public static void main(String[] args) {
        Menu menu = new Menu();
        GestorArchivos gestor = new GestorArchivos();

        List<LinkedHashMap<String, String>> datos = null;
        File archivoActual = null;

        boolean salir = false;
        while (!salir) {
            int opcion = menu.mostrarMenu(
                gestor.getCarpetaSeleccionada() != null ?
                            gestor.getCarpetaSeleccionada().getAbsolutePath() : null,
                    gestor.listarContenidoCarpeta(),
                    archivoActual != null ? archivoActual.getName() : null
            );

            switch (opcion) {
                case 1 -> {
                    String rutaCarpeta = menu.pedirRutaCarpeta();
                    if (gestor.seleccionarCarpeta(rutaCarpeta)) {
                        System.out.println("Carpeta seleccionada correctamente");
                        archivoActual = null;
                        datos = null;
                    }else {
                        System.out.println("Error al seleccionar la carpeta");
                    }
                }

                case 2 -> {
                String nombreFichero = menu.pedirNombreFichero();
                File archivo = gestor.obtenerArchivo(nombreFichero);
                if (archivo != null) {
                    try {
                        if (nombreFichero.endsWith(".csv")) {
                            datos = CSVtransformar.leerCSV(archivo);
                        } else if (nombreFichero.endsWith(".xml")) {
                            datos = XMLtransformar.transformar(archivo);
                        } else if (nombreFichero.endsWith(".json")) {
                            datos = JSONtransformar.leerJSON(archivo);
                        } else {
                            System.out.println("Formato de archivo no soportado");
                        break;
                        }
                        archivoActual = archivo;
                        System.out.println("Fichero seleccionado correctamente");
                    } catch (Exception e) {
                        System.out.println("Error al seleccionar el fichero" + e.getMessage());
                    }
                } else {
                    System.out.println("Fichero no encontrado");
                }
            }

            case 3 -> {
                if (datos == null || archivoActual == null) {
                    System.out.println("No se ha seleccionado un fichero");
                    break;
                }

                String formatoDestino = menu.pedirFormatoDestino();
                String nombreArchivoSalida = menu.pedirNombreArchivoSalida();
                File salida = new File (gestor.getCarpetaSeleccionada(), nombreArchivoSalida + "." + formatoDestino);
            
                try {
                    switch (formatoDestino) {
                        case "csv" -> CSVtransformar.escribirCSV(salida, datos);
                        case "xml" -> XMLtransformar.escribir(datos, salida);
                        case "json" -> JSONtransformar.escribirJSON(salida, datos);
                        default -> { System.out.println("Formato de salida no soportado");
                    continue;
                }
            }
            System.out.println("Fichero generado correctamente en " + salida.getAbsolutePath());
        } 
        
        catch (Exception e) {
            System.out.println("Error al generar el fichero" + e.getMessage());
        }
    }

        case 4 -> salir = true;
        default -> System.out.println("Opción no válida");
    }   
}
}
}
