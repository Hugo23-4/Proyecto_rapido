import utils.*;

import java.io.File;
import java.util.List;
import java.util.LinkedHashMap;

public class Main {

    public static void main(String[] args) {
        Menu menu = new Menu();
        File file = new File();

        List<LinkedHashMap<String, String>> datos = null;
        File archivoActual = null;

        boolean salir = false;
        while (!salir) {
            int opcion = menu.mostrarMenu(
                file.getCarpetaSeleccionada() != null ?
                            file.getCarpetaSeleccionada().getAbsolutePath() : null,
                    file.listarContenidoCarpeta(),
                    archivoActual != null ? archivoActual.getName() : null
            );

            switch (opcion) {
                case 1 -> menu.pedirRutaCarpeta();
                if (file.seleccionCarpeta()) {
                    System.out.println("Carpeta seleccionada correctamente");
                    archivoActual = null;
                    datos = null;
                }else {
                    System.out.println("Error al seleccionar la carpeta");
                }

                case 2 -> {
                String nombreFichero = menu.pedirNombreFichero();
                File archivo = file.obtenerArchivo(nombre);
                if (archivo != null) {
                    try {
                        if (nombre.edsWith(".csv")) {
                            datos = CSVtransformar.transformar(archivo);
                        } else if (nombre.endsWith(".xml")) {
                            datos = XMLtransformar.transformar(archivo);
                        } else if (nombre.endsWith(".json")) {
                            datos = JSONtransformar.transformar(archivo);
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

            case 3 -> {
                if (datos == null || archivoActual == null) {
                    System.out.println("No se ha seleccionado un fichero");
                    break;
                }

                String formatoDestino = menu.pedirFormatoDestino();
                String nombreArchivoSalida = menu.pedirNombreArchivoSalida();
                File salida = new File (file.getCarpetaSeleccionada(), nombreSalida + "." + formato);
            
                try {
                    switch (formato) {
                        case "csv" -> CSVtransformar.escribirCSV(datos, salida);
                        case "xml" -> XMLtransformar.escribir(datos, salida);
                        case "json" -> JSONtransformar.escribir(datos, salida);
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