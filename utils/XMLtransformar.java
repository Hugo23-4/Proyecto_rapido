package utils;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.*;

public class XMLtransformar {

    public static List<LinkedHashMap<String, String>> transformar(File archivo) throws Exception {
        List<LinkedHashMap<String, String>> lista = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documento = builder.parse(archivo);
        documento.getDocumentElement().normalize(); 
        NodeList nodos = documento.getDocumentElement().getChildNodes();

        for (int i = 0; i < nodos.getLength(); i++) {
            Node nodo = nodos.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element elemento = (Element) nodo;
                LinkedHashMap<String, String> mapa = new LinkedHashMap<>();

                NodeList hijos = elemento.getChildNodes();
                for (int j = 0; j < hijos.getLength(); j++) {
                    Node hijo = hijos.item(j);
                    if (hijo.getNodeType() == Node.ELEMENT_NODE) {
                        mapa.put(hijo.getNodeName(), hijo.getTextContent());	
                    }
                }
                lista.add(mapa);
            }
        }
        return lista;
    }

    public static void escribir(List<LinkedHashMap<String, String>> datos, File archivo) throws Exception {
        if (datos.isEmpty()) {
            throw new Exception("datos is empty");
        }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.newDocument();
            Element raiz = documento.createElement("elementos");
            documento.appendChild(raiz);

            for (LinkedHashMap<String, String> mapa : datos) {
                Element items = documento.createElement("elementos");
                for (Map.Entry<String, String> entrada : mapa.entrySet()) {
                    Element campo = documento.createElement(entrada.getKey());
                    campo.appendChild(documento.createTextNode(entrada.getValue()));
                    items.appendChild(campo);
                }
                raiz.appendChild(items);
            }

            Transformer transformador = TransformerFactory.newInstance().newTransformer();
            transformador.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource fuente = new DOMSource(documento);
            StreamResult resultado = new StreamResult(archivo);
            transformador.transform(fuente, resultado);
        }
    }