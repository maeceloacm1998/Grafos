
import libs.AdjacencyList;
import libs.AdjacencyMatrix;
import models.GraphItem;
import utils.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {
    public static void init() {
        List<GraphItem> grafoList = new ArrayList<GraphItem>(Collections.emptyList());
        grafoList.add(Graph.getGrafo("A/A-B_a/A-C_a"));
        grafoList.add(Graph.getGrafo("B/B-E_4/B-D_3"));
        grafoList.add(Graph.getGrafo("C/C-D_3/C-B_5"));
        grafoList.add(Graph.getGrafo("D/D-E_3"));
        grafoList.add(Graph.getGrafo("E/"));
        
        AdjacencyMatrix grafo =  new AdjacencyMatrix(grafoList);
        AdjacencyList lista = new AdjacencyList(grafoList);
        
        
        Boolean adjacentEdge = lista.isAdjacentEdge("A-B", "A-C");
        // boolean adjacentVertex = lista.isAdjacentVertex("A", "C");
        System.out.println(adjacentEdge);
        Boolean x = grafo.isVertexLabeled("A");
        Boolean y = lista.isVertexLabeled("A");
        // System.out.println(y);
        String a = "";
    }

    public static void main(String[] args) throws Exception {
        init();
    }
}
