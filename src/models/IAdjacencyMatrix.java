package models;


public interface IAdjacencyMatrix {
    public void insert(GraphItem grafoitem);

    public void remove(String vertex, String edge);

    public Boolean isVertexThoughtful(String vertex);

    public Boolean isVertexLabeled(String vertex);

    public Boolean isEdgeThoughtful(String edge);

    public Boolean isEdgeLabeled(String edge);

    public Boolean isAdjacentVertex(String vertexOne, String vertexTwo);

    public Boolean isAdjacentEdge(String connectionOne, String connectionTwo);
//    public Boolean isVertexIncidency();
//    public Boolean isEdgeIncidency();
//    public Boolean existEdge();
//    public Integer quantityEdge();
//    public Integer quantityVertex();
//    public Boolean isEmpty();
//    public Boolean isComplet();
}
