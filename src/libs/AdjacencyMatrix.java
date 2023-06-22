package libs;

import models.GraphItem;
import models.IAdjacencyMatrix;
import utils.Graph;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class AdjacencyMatrix implements IAdjacencyMatrix {
    private final String WITHOUT_WEIGHT = "0";

    private Map<String, Map<String, String>> adjacencyMatrix;

    public AdjacencyMatrix(List<GraphItem> list) {
        this.adjacencyMatrix = new HashMap<>();
        init(list);
    }

    private void init(List<GraphItem> list) {
        handleAdjacencyMatrix(list);
    }

    private void handleAdjacencyMatrix(List<GraphItem> list) {
        for (GraphItem item : list) {
            String vertex = item.getVertex();
            Map<String, String> edges = new HashMap<>();
            for (String edge : item.getEdges()) {
                String connection = Graph.getConnection(edge);
                String weight = Graph.getWeight(edge);
                edges.put(connection, weight);
            }
            adjacencyMatrix.put(vertex, edges);
        }
    }

    public Map<String, Map<String, String>> getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    @Override
    public void insert(GraphItem graphItem) {
        String vertex = graphItem.getVertex();
        Map<String, String> edges = new HashMap<>();
        for (String edge : graphItem.getEdges()) {
            String connection = Graph.getConnection(edge);
            String weight = Graph.getWeight(edge);
            edges.put(connection, weight);
        }
        adjacencyMatrix.put(vertex, edges);
    }

    @Override
    public void remove(String vertex, String edge) {
        String connection = Graph.getConnection(edge);
        Map<String, String> edges = adjacencyMatrix.getOrDefault(vertex, new HashMap<>());
        edges.remove(connection);
        adjacencyMatrix.put(vertex, edges);
    }

    @Override
    public Boolean isVertexThoughtful(String vertex) {
        Map<String, String> edges = adjacencyMatrix.getOrDefault(vertex, new HashMap<>());
        return edges.values().stream().anyMatch(weight -> !weight.equals(WITHOUT_WEIGHT));
    }

    @Override
    public Boolean isVertexLabeled(String vertex) {
        Map<String, String> edges = adjacencyMatrix.getOrDefault(vertex, new HashMap<>());
        return edges.values().stream().anyMatch(weight -> !Graph.isWeightNumeric(weight));
    }

    @Override
    public Boolean isEdgeThoughtful(String edge) {
        String weight = Graph.getWeight(edge);
        return !weight.equals(WITHOUT_WEIGHT) && Graph.isWeightNumeric(weight);
    }

    @Override
    public Boolean isEdgeLabeled(String edge) {
        String weight = Graph.getWeight(edge);
        return !Graph.isWeightNumeric(weight);
    }

    @Override
    public Boolean isAdjacentVertex(String vertex, String adjacentVertex) {
        Map<String, String> edges = adjacencyMatrix.getOrDefault(vertex, new HashMap<>());
        return edges.containsKey(adjacentVertex);
    }

    @Override
    public Boolean isAdjacentEdge(String vertex, String adjacentEdge) {
        Map<String, String> edges = adjacencyMatrix.getOrDefault(vertex, new HashMap<>());
        String connection = Graph.getConnection(adjacentEdge);
        String weight = Graph.getWeight(adjacentEdge);
        String edgeWeight = edges.get(connection);
        return edgeWeight != null && edgeWeight.equals(weight);
    }

    @Override
    public Boolean existEdge() {
        return adjacencyMatrix.values().stream().anyMatch(edges -> !edges.isEmpty());
    }

    @Override
    public Integer quantityEdge() {
        return adjacencyMatrix.values().stream().mapToInt(Map::size).sum();
    }

    @Override
    public Integer quantityVertex() {
        return adjacencyMatrix.size();
    }

    @Override
    public Boolean isEmpty() {
        return adjacencyMatrix.isEmpty();
    }

    @Override
    public Boolean isComplet() {
        List<String> vertices = List.copyOf(adjacencyMatrix.keySet());
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = i + 1; j < vertices.size(); j++) {
                if (!isAdjacentVertex(vertices.get(i), vertices.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Exporta a lista adjascente para CSV em formato suportado pelo Gephi
     *
     * @param filePath Caminho do arquivo CSV
     */
    public void exportToCSV(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            List<String> vertices = new ArrayList<>(adjacencyMatrix.keySet());
            Collections.sort(vertices);
            writer.print(";");
            for (String vertex : vertices) {
                writer.print(vertex + ";");
            }
            writer.println();

            for (String vertex : vertices) {
                writer.print(vertex + ";");
                Map<String, String> edges = adjacencyMatrix.get(vertex);
                for (String v : vertices) {
                    String edgeValue = edges.getOrDefault(v, WITHOUT_WEIGHT);
                    writer.print(edgeValue + ";");
                }
                writer.println();
            }

            System.out.println("Matriz adjascente exportada com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao exportar matriz adjascente para CSV: " + e.getMessage());
        }
    }

    /**
     * Importa a lista adjascente de um arquivo CSV em formato suportado pelo Gephi
     *
     * @param filePath Caminho do arquivo CSV
     */
    public void importFromCSV(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            adjacencyMatrix.clear();

            if (scanner.hasNextLine()) {
                String headerLine = scanner.nextLine();
                String[] vertices = headerLine.split(";");
                int numVertices = vertices.length - 1;

                for (int i = 1; i <= numVertices; i++) {
                    adjacencyMatrix.put(vertices[i], new HashMap<>());
                }

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] tokens = line.split(";");
                    String vertex = tokens[0];

                    for (int i = 1; i <= numVertices; i++) {
                        String connection = vertices[i];
                        String weight = tokens[i];
                        if (!weight.equals(WITHOUT_WEIGHT)) {
                            adjacencyMatrix.get(vertex).put(connection, weight);
                        }
                    }
                }

                System.out.println("Matriz adjascente importada com sucesso.");
            }
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao importar matriz adjascente de CSV: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Map<String, String>> entry : adjacencyMatrix.entrySet()) {
            String vertex = entry.getKey();
            Map<String, String> edges = entry.getValue();
            builder.append(vertex).append(": ");
            for (Map.Entry<String, String> edge : edges.entrySet()) {
                String connection = edge.getKey();
                String weight = edge.getValue();
                builder.append("(").append(connection).append(", ").append(weight).append(") ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
