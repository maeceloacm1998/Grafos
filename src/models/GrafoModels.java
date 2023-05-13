package models;

public interface GrafoModels {
    public void insert();
    public void remove();
    public void vertexPonderation();
    public void vertexRotualtion();
    public void edgePonderation();
    public void edgeRotualtion();
    public Boolean isAdjacentVertex();
    public Boolean isAdjacentEdge();
    public Boolean isVertexIncidency();
    public Boolean isEdgeIncidency();
    public Boolean existEdge();
    public Integer quantityEdge();
    public Integer quantityVertex();
    public Boolean isEmpty();
    public Boolean isComplet();
}
