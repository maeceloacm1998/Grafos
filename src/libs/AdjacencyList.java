package libs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import models.GraphItem;
import models.IAdjacencyList;
import models.Edge;
import utils.Graph;

public class AdjacencyList implements IAdjacencyList {
    private final String WITHOUT_WEIGHT = "0";

    Map<String, List<Edge>> adjacentList;

    /**
     * Construtor da classe AdjacencyList
     *
     * @param list Lista de itens do grafo (vértices e arestas)
     */
    public AdjacencyList(List<GraphItem> list) {
        this.adjacentList = new HashMap<>();
        init(list);
    }


    /**
     * Inicializa a lista adjascente
     *
     * @param list Lista de itens do grafo (vértices e arestas)
     */
    private void init(List<GraphItem> list) {
        handleAdjacencyList(list);
    }

    /**
     * Cria a lista adjascente
     *
     * @param list Lista de itens do grafo (vértices e arestas)
     */
    private void handleAdjacencyList(List<GraphItem> list) {
        list.forEach(GraphItemModel -> {
            String vertex = GraphItemModel.getVertex();
            List<Edge> edges = new ArrayList<>();
            GraphItemModel.getEdges().forEach(edge -> {
                String connection = Graph.getConnection(edge);
                String weight = Graph.getWeight(edge);
                edges.add(new Edge(connection, weight));
            });
            this.adjacentList.put(vertex, edges);
        });
    }

    /**
     * Retorna a lista adjascente
     * 
     * @return Lista adjascente
     */
    public Map<String, List<Edge>> getAdjacentList() {
        return adjacentList;
    }


    /**
     * Insere um vértice na lista adjascente
     * 
     * @param vertex Vértice a ser inserido
     * @param edge Aresta a ser inserida
     */
    @Override
    public void insert(String vertex, String edge) {
        String connection = Graph.getConnection(edge);
        String weight = Graph.getWeight(edge);

        List<Edge> edges = this.adjacentList.getOrDefault(vertex, new ArrayList<>());
        edges.add(new Edge(connection, weight));
        this.adjacentList.put(vertex, edges);
    }

    /**
     * Remove um vértice da lista adjascente
     * 
     * @param vertex Vértice a ser removido
     * @param edge Aresta a ser removida
     */
    @Override
    public void remove(String vertex, String edge) {
        String connection = Graph.getConnection(edge);
        List<Edge> edges = this.adjacentList.getOrDefault(vertex, new ArrayList<>());
        edges.removeIf(e -> e.getConnection().equals(connection));
        this.adjacentList.put(vertex, edges);
    }

    /**
     * Verifica se o vertice é ponderado
     * 
     * @param vertex Vértice a ser verificado
     */
    @Override
    public Boolean isVertexThoughtful(String vertex) {
        List<Edge> edges = this.adjacentList.getOrDefault(vertex, new ArrayList<>());
        return edges.stream().anyMatch(e -> !e.getWeight().equals(WITHOUT_WEIGHT));
    }



    /**
     * Verifica se o vertice é rotulado
     * 
     * @param vertex Vértice a ser verificado
     */
    @Override
    public Boolean isVertexLabeled(String vertex) {
        List<Edge> edges = this.adjacentList.getOrDefault(vertex, new ArrayList<>());
        return edges.stream().anyMatch(e -> !Graph.isWeightNumeric(e.getWeight()));
    }

    /**
     * Verifica se a aresta é ponderada
     * 
     * @param edge Aresta a ser verificada
     */
    @Override
    public Boolean isEdgeThoughtful(String edge) {
        String weight = Graph.getWeight(edge);
        return !weight.equals(WITHOUT_WEIGHT) && Graph.isWeightNumeric(weight);
    }

    /**
     * Verifica se a aresta é rotulada
     * 
     * @param edge Aresta a ser verificada
     */
    @Override
    public Boolean isEdgeLabeled(String edge) {
        String weight = Graph.getWeight(edge);
        return !Graph.isWeightNumeric(weight);
    }

    /**
     * Verifica se o vértice é adjascente ao outro vértice
     * 
     * @param vertex Vértice a ser verificado
     * @param adjacentVertex Vértice adjascente
     */
    @Override
    public Boolean isAdjacentVertex(String vertex, String adjacentVertex) {
        List<Edge> edges = this.adjacentList.getOrDefault(vertex, new ArrayList<>());
        return edges.stream().anyMatch(e -> e.getConnection().equals(adjacentVertex));
    }

    /**
     * Verifica se a aresta é adjascente a um vértice
     * 
     * @param vertex Vértice a ser verificado
     * @param adjacentEdge Aresta adjascente
     */
    @Override
    public Boolean isAdjacentEdge(String vertex, String adjacentEdge) {
        List<Edge> edges = this.adjacentList.getOrDefault(vertex, new ArrayList<>());
        return edges.stream().anyMatch(e -> {
            String connection = Graph.getConnection(adjacentEdge);
            String weight = Graph.getWeight(adjacentEdge);
            return e.getConnection().equals(connection) && e.getWeight().equals(weight);
        });
    }

    /**
     * Verifica se existe arestas na lista adjascente
     * 
     * @return Boolean se existir arestas na lista adjascente
     */
    @Override
    public Boolean existEdge() {
        return this.adjacentList.values().stream().anyMatch(e -> !e.isEmpty());
    }


    /**
     * Retorna a quantidade de arestas na lista adjascente
     * 
     * @return Quantidade de arestas na lista adjascente
     */
    @Override
    public Integer quantityEdge() {
        return this.adjacentList.values().stream().mapToInt(List::size).sum();
    }


    /**
     * Retorna a quantidade de vértices na lista adjascente
     * 
     * @return Quantidade de vértices na lista adjascente
     */
    @Override
    public Integer quantityVertex() {
        return this.adjacentList.size();
    }

    /**
     * Retorna se a lista adjascente está vazia
     * 
     * @return Boolean se a lista adjascente está vazia
     */
    @Override
    public Boolean isEmpty() {
        return this.adjacentList.isEmpty();
    }

    /**
     * Retorna se a lista adjascente é completa
     * 
     * @return Boolean se a lista adjascente é completa
     */
    @Override
    public Boolean isComplet() {
        List<String> vertices = new ArrayList<>(this.adjacentList.keySet());
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
    @Override
    public void exportToCSV(String filePath) {
        try (PrintWriter writer = new PrintWriter(new File(filePath))) {
            for (Map.Entry<String, List<Edge>> entry : adjacentList.entrySet()) {
                String vertex = entry.getKey();
                List<Edge> edges = entry.getValue();
                StringBuilder line = new StringBuilder(vertex);
                for (Edge edge : edges) {
                    line.append(";").append(edge.getConnection());
                }
                writer.println(line.toString());
            }
            System.out.println("Lista adjascente exportada com sucesso.");
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao exportar lista adjascente para CSV: " + e.getMessage());
        }
    }

    /**
     * Importa a lista adjascente de um arquivo CSV em formato suportado pelo Gephi
     * 
     * @param filePath Caminho do arquivo CSV
     */
    @Override
    public void importFromCSV(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            adjacentList.clear();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(";");
                String vertex = tokens[0];
                List<Edge> edges = new ArrayList<>();
                for (int i = 1; i < tokens.length; i++) {
                    edges.add(new Edge(tokens[i], WITHOUT_WEIGHT));
                }
                adjacentList.put(vertex, edges);
            }
            System.out.println("Lista adjascente importada com sucesso.");
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao exportar lista adjascente para CSV: " + e.getMessage());
        }
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, List<Edge>> entry : this.adjacentList.entrySet()) {
            String vertex = entry.getKey();
            List<Edge> edges = entry.getValue();
            builder.append(vertex).append(": ");
            for (Edge edge : edges) {
                String connection = edge.getConnection();
                String weight = edge.getWeight();
                builder.append("(").append(connection).append(", ").append(weight).append(") ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }


}
