package models;

public interface GrafoModels {
    public void insert(String vertex, String edge);
    public void remove(String vertex, String edge) ;
    public Boolean isVertexThoughtful(String vertex);
    public Boolean isVertexLabeled(String vertex);
    public Boolean isEdgeThoughtful(String edge);
    public Boolean isEdgeLabeled(String edge);

//    public Boolean isAdjacentVertex();
//    public Boolean isAdjacentEdge();
//    public Boolean isVertexIncidency();
//    public Boolean isEdgeIncidency();
//    public Boolean existEdge();
//    public Integer quantityEdge();
//    public Integer quantityVertex();
//    public Boolean isEmpty();
//    public Boolean isComplet();
}
