package utils;

import java.io.*;
import java.util.*;

public class CSVtransformar {

    public static List<LinkedHashMap<String, String>> leerCSV(File archivo) throws IOException {
        List<LinkedHashMap<String, String>> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String cabecera = br.readLine();
            if (cabecera == null) return lista;

            String[] campos = cabecera.split(",");
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] valores = linea.split(",");
                LinkedHashMap<String, String> mapa = new LinkedHashMap<>();
                for (int i = 0; i < campos.length; i++) {
                    mapa.put(campos[i].trim(), i < valores.length ? valores[i].trim() : "");
                }
                lista.add(mapa);
            }
        }

        return lista;
    }

    public static void escribirCSV(File archivo, List<LinkedHashMap<String, String>> datos) throws IOException {
        if (datos.isEmpty()) return;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            Set<String> campos = datos.get(0).keySet();
            bw.write(String.join(",", campos));
            bw.newLine();

            for (Map<String, String> fila : datos) {
                List<String> valores = new ArrayList<>();
                for (String clave : campos) {
                    valores.add(fila.getOrDefault(clave, ""));
                }
                bw.write(String.join(",", valores));
                bw.newLine();
            }
        }
    }
}