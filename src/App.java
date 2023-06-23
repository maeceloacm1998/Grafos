
import libs.AdjacencyList;
import libs.AdjacencyMatrix;
import models.GraphItem;
import utils.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {
    public static void init() {
        List<GraphItem> graphList = new ArrayList<GraphItem>(Collections.emptyList());
        graphList.add(Graph.getGraph("A/A-B_a/A-C_a"));
        graphList.add(Graph.getGraph("B/B-E_4/B-D_3"));
        graphList.add(Graph.getGraph("C/C-D_3/C-B_5"));
        graphList.add(Graph.getGraph("D/D-E_3"));
        graphList.add(Graph.getGraph("E/"));

        AdjacencyMatrix graph = new AdjacencyMatrix(graphList);
        AdjacencyList graph2 = new AdjacencyList(graphList);

        graph.exportCsv("matriz-teste.csv");
        graph.importCSV("matriz-teste2.csv");

        graph2.exportToCSV("lista-teste.csv");
        graph2.importFromCSV("lista-teste2.csv");
    }

    public static void main(String[] args) throws Exception {
        init();
    }
}
