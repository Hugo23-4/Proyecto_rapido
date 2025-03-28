package utils;

import java.io.*;
import java.util.*;

public class JSONtransformar {

    public static List<LinkedHashMap<String, String>> leerJSON(File archivo) throws IOException {
        List<LinkedHashMap<String, String>> lista = new ArrayList<>();
        StringBuilder contenido = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea.trim());
            }
        }

        String data = contenido.toString().trim();
        if (data.startsWith("[")) data = data.substring(1);
        if (data.endsWith("]")) data = data.substring(0, data.length() - 1);

        String[] objetos = data.split("\\},\\s*\\{");

        for (String obj : objetos) {
            obj = obj.trim();
            if (!obj.startsWith("{")) obj = "{" + obj;
            if (!obj.endsWith("}")) obj = obj + "}";

            LinkedHashMap<String, String> mapa = new LinkedHashMap<>();
            String[] pares = obj.replaceAll("[{}\"]", "").split(",");

            for (String par : pares) {
                String[] kv = par.split(":", 2);
                if (kv.length == 2) {
                    mapa.put(kv[0].trim(), kv[1].trim());
                }
            }
            lista.add(mapa);
        }

        return lista;
    }

    public static void escribirJSON(File archivo, List<LinkedHashMap<String, String>> datos) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            bw.write("[\n");
            for (int i = 0; i < datos.size(); i++) {
                LinkedHashMap<String, String> mapa = datos.get(i);
                bw.write("  {\n");
                int j = 0;
                for (Map.Entry<String, String> entrada : mapa.entrySet()) {
                    bw.write("    \"" + entrada.getKey() + "\": \"" + entrada.getValue() + "\"");
                    bw.write(j < mapa.size() - 1 ? ",\n" : "\n");
                    j++;
                }
                bw.write("  }" + (i < datos.size() - 1 ? "," : "") + "\n");
            }
            bw.write("]");
        }
    }
}