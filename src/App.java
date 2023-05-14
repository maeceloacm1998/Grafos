
import libs.AdjacentMatriz;
import models.GrafoItemModel;
import utils.GrafoUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {
    public static void init() {
        List<GrafoItemModel> grafoList = new ArrayList(Collections.emptyList());
        grafoList.add(GrafoUtils.getGrafo("A/A-B_a/A-C_a"));
        grafoList.add(GrafoUtils.getGrafo("B/B-E_4/B-D_3"));
        grafoList.add(GrafoUtils.getGrafo("C/C-D_3/C-B_5"));
        grafoList.add(GrafoUtils.getGrafo("D/D-E_3"));
        grafoList.add(GrafoUtils.getGrafo("E/"));

        AdjacentMatriz grafo =  new AdjacentMatriz(grafoList);

        Boolean x = grafo.isVertexLabeled("A");
        String a = "";
    }

    public static void main(String[] args) throws Exception {
        init();
    }
}
