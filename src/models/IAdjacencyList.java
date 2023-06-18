package models;


public interface IAdjacencyList {
    public void insert(String vertex, String edge);
    public void remove(String vertex, String edge) ;
    public Boolean isVertexThoughtful(String vertex);
    public Boolean isVertexLabeled(String vertex);
    public Boolean isEdgeThoughtful(String edge);
    public Boolean isEdgeLabeled(String edge);
    public Boolean isAdjacentVertex(String vertex, String adjacentVertex);
    public Boolean isAdjacentEdge(String vertex, String adjacentEdge);
    public Boolean existEdge();
    public Integer quantityEdge();
    public Integer quantityVertex();
    public Boolean isEmpty();
    public Boolean isComplet();
    public void exportToCSV(String path);
    public void importFromCSV(String path);
}
