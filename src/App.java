
import libs.AdjacentMatriz;
import models.GrafoItemModel;
import utils.GrafoUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class App {
    public static void init() {
        List<GrafoItemModel> grafoList = new ArrayList(Collections.emptyList());
        grafoList.add(GrafoUtils.getGrafo("A/A-B_5/A-C_2"));
        grafoList.add(GrafoUtils.getGrafo("B/B-E_4/B-D_3"));
        grafoList.add(GrafoUtils.getGrafo("C/C-D_3/C-B_5"));
        grafoList.add(GrafoUtils.getGrafo("D/D-E_3"));
        grafoList.add(GrafoUtils.getGrafo("E/"));

        AdjacentMatriz grafo =  new AdjacentMatriz(grafoList);

        grafo.insert(requireNonNull(GrafoUtils.getGrafo("E/E-D_4")));
        grafo.insert(requireNonNull(GrafoUtils.getGrafo("E/E-F_4")));
        grafo.insert(requireNonNull(GrafoUtils.getGrafo("D/D-E_3/D-C_5")));

        Boolean x = grafo.isAdjacentVertex("A", "D");
        Boolean y = grafo.isAdjacentEdge("A-B", "C-B");
        String a = "";
    }

    public static void main(String[] args) throws Exception {
        init();
    }
}
