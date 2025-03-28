package utils;

import java.io.File;

public class File {

    private File carpetaSeleccionada;

    
    public boolean seleccionarCarpeta(String ruta) {
        File carpeta = new File(ruta);
        if (carpeta.exists() && carpeta.isDirectory()) {
            this.carpetaSeleccionada = carpeta;
            return true;
        }
        return false;
    }

   
    public File getCarpetaSeleccionada() {
        return carpetaSeleccionada;
    }

    
    public String[] listarContenidoCarpeta() {
        if (carpetaSeleccionada != null) {
            return carpetaSeleccionada.list();
        }
        return new String[0];
    }

    public File obtenerArchivo(String nombreArchivo) {
        if (carpetaSeleccionada == null) return null;
        File archivo = new File(carpetaSeleccionada, nombreArchivo);
        return archivo.exists() ? archivo : null;
    }
}