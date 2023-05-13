
import models.GrafoItemModel;
import utils.GrafoUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {
    public static void init() {
        List<GrafoItemModel> grafoList = new ArrayList(Collections.emptyList());
        grafoList.add(GrafoUtils.getGrafo("A/A-B/A-C"));
        grafoList.add(GrafoUtils.getGrafo("B/B-E/B-D"));
        grafoList.add(GrafoUtils.getGrafo("C/C-D/C-B"));
        grafoList.add(GrafoUtils.getGrafo("D/D-E"));
        grafoList.add(GrafoUtils.getGrafo("E/"));

        String x = "";
    }

    public static void main(String[] args) throws Exception {
        init();
    }
}
