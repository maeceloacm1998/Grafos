package models;

import java.util.Collections;
import java.util.List;

public class GraphItem {
    private String vertex = "";
    private List<String> edges = Collections.emptyList();

    // A linha vai ser "(Vertice)/(CAMINHO)/(CAMINHO)/(CAMINHO)/...
    // Exemplo: "1/1-2/1-4/1-3"
    public GraphItem(String vertex, List<String> edges) {
        this.vertex = vertex;
        this.edges = edges;
    }

    public String getVertex() {
        return vertex; 
    }

    public List<String> getEdges() {
        return edges;
    }

    public void setVertex(String vertex) {
        this.vertex = vertex;
    }

    public void setEdges(List<String> edges) {
        this.edges = edges;
    }
}
