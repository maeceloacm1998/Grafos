package libs;

import models.GrafoModels;

public class AdjacentList implements GrafoModels {
    final Integer EXIST_EDGE = 1;
    final Integer NOT_EXIST_EDGE = 0;

    public AdjacentList() {

    }

    @Override
    public void insert() {

    }

    @Override
    public void remove() {

    }

    @Override
    public void vertexPonderation() {

    }

    @Override
    public void vertexRotualtion() {

    }

    @Override
    public void edgePonderation() {

    }

    @Override
    public void edgeRotualtion() {

    }

    @Override
    public Boolean isAdjacentVertex() {
        return true;
    }

    @Override
    public Boolean isAdjacentEdge() {
        return true;
    }

    @Override
    public Boolean isVertexIncidency() {
        return true;
    }

    @Override
    public Boolean isEdgeIncidency() {
        return true;
    }

    @Override
    public Boolean existEdge() {
        return true;
    }

    @Override
    public Integer quantityEdge() {
        return 1;
    }

    @Override
    public Integer quantityVertex() {
        return 1;
    }

    @Override
    public Boolean isEmpty() {
        return true;
    }

    @Override
    public Boolean isComplet() {
        return true;
    }
}
