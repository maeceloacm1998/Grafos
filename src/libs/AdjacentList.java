package libs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.GrafoItemModel;
import models.GrafoModelsAdjacentList;
import utils.GrafoUtils;

class Edge {
    private String connection;
    private String weight;

    public Edge(String connection, String weight) {
        this.connection = connection;
        this.weight = weight;
    }

    public String getConnection() {
        return connection;
    }

    public String getWeight() {
        return weight;
    }
}

public class AdjacentList implements GrafoModelsAdjacentList {

    Map<String, List<Edge>> adjacentList;

    public AdjacentList(List<GrafoItemModel> list) {
        this.adjacentList = new HashMap<>();
        init(list);
    }

    private void init(List<GrafoItemModel> list) {
        handleAdjacencyList(list);
    }

    private void handleAdjacencyList(List<GrafoItemModel> list) {
        list.forEach(GrafoItemModel -> {
            String vertex = GrafoItemModel.getVertex();
            List<Edge> edges = new ArrayList<>();
            GrafoItemModel.getEdges().forEach(edge -> {
                String connection = GrafoUtils.getConnection(edge);
                String weight = GrafoUtils.getWeight(edge);
                edges.add(new Edge(connection, weight));
            });
            this.adjacentList.put(vertex, edges);
        });
    }

    @Override
    public void insert(String vertex, String edge) {
        String connection = GrafoUtils.getConnection(edge);
        String weight = GrafoUtils.getWeight(edge);

        List<Edge> edges = this.adjacentList.getOrDefault(vertex, new ArrayList<>());
        edges.add(new Edge(connection, weight));
        this.adjacentList.put(vertex, edges);
    }

    @Override
    public void remove(String vertex, String edge) {
        String connection = GrafoUtils.getConnection(edge);
        List<Edge> edges = this.adjacentList.getOrDefault(vertex, new ArrayList<>());
        edges.removeIf(e -> e.getConnection().equals(connection));
        this.adjacentList.put(vertex, edges);
    }

    @Override
    public Boolean isVertexThoughtful(String vertex) {
        List<Edge> edges = this.adjacentList.getOrDefault(vertex, new ArrayList<>());
        return edges.stream().anyMatch(e -> !e.getWeight().equals("0"));
    }

    @Override
    public Boolean isVertexLabeled(String vertex) {
        List<Edge> edges = this.adjacentList.getOrDefault(vertex, new ArrayList<>());
        return edges.stream().anyMatch(e -> !GrafoUtils.isWeightNumeric(e.getWeight()));
    }

    @Override
    public Boolean isEdgeThoughtful(String edge) {
        String weight = GrafoUtils.getWeight(edge);
        return !weight.equals("0") && GrafoUtils.isWeightNumeric(weight);
    }

    @Override
    public Boolean isEdgeLabeled(String edge) {
        String weight = GrafoUtils.getWeight(edge);
        return !GrafoUtils.isWeightNumeric(weight);
    }

    @Override
    public Boolean isAdjacentVertex(String vertex, String adjacentVertex) {
        List<Edge> edges = this.adjacentList.getOrDefault(vertex, new ArrayList<>());
        return edges.stream().anyMatch(e -> e.getConnection().equals(adjacentVertex));
    }

    @Override
    public Boolean isAdjacentEdge(String vertex, String adjacentEdge) {
        List<Edge> edges = this.adjacentList.getOrDefault(vertex, new ArrayList<>());
    return edges.stream().anyMatch(e -> {
        String connection = GrafoUtils.getConnection(adjacentEdge);
        String weight = GrafoUtils.getWeight(adjacentEdge);
        return e.getConnection().equals(connection) && e.getWeight().equals(weight);
    });
    }
}