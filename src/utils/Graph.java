package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.GraphItem;

public class Graph {

    /*
    * Essa função serve para transformar a linha que você mandar, que seria do formato
    * "(Vertice)/(CAMINHO)_(peso)/(CAMINHO)_(peso)/(CAMINHO)_(peso)/...
    * em um objeto contendo vertice e uma lista de conexões que ele vai fazer
    * EXEMPLO DE LINHA DO GRAFO = "A/A-B_b/A-C_c"
    * PARA GRAFO SEM LIGAÇÕES, COLOCA APENAS UMA BARRA "Exemplo: "A/"
    * */
    public static GraphItem getGraph(String graphLine) {
        if (!graphLine.isEmpty()) {
            String[] split = graphLine.split("/");
            String vertex = getVertex(split);
            List<String> edgeList = getEdgeList(split);

            return new GraphItem(vertex, edgeList);
        } else {
            return null;
        }
    }

    public static boolean isWeightNumeric(String weight) {
        try {
            Integer.parseInt(weight);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String getConnection(String edge) {
        return edge.split("-")[1].split("_")[0];
    }

    public static String getWeight(String edge) {
        return edge.split("-")[1].split("_")[1];
    }

    private static String getVertex(String[] split) {
        return split[0];
    }

    private static List<String> getEdgeList(String[] split) {
        List<String> edges = new ArrayList<String>(Collections.emptyList());

        if(haveConnections(split)) {
            for (int i = 0; i < split.length; i++) {
                if (i != 0) {
                    edges.add(split[i]);
                }
            }
        }

        return edges;
    }

    /*
    * Verifica se o grafo contem conexões*/
    private static Boolean haveConnections(String[] split) {
        return split.length != 1;
    }
}
