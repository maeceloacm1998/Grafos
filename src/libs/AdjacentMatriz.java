package libs;

import models.GrafoItemModel;
import models.GrafoModels;
import utils.GrafoUtils;
import utils.PositionUtils;

import java.util.List;


public class AdjacentMatriz implements GrafoModels {
    private final String EXIST_EDGE = "1";
    private final String NOT_EXIST_EDGE = "0";

    String[][] adjascentMatriz;
    String[][] weightMatriz;

    public AdjacentMatriz(List<GrafoItemModel> list) {
        adjascentMatriz = new String[list.size()][list.size()];
        weightMatriz = new String[list.size()][list.size()];
        new PositionUtils(list);
        init(list);
    }

    private void init(List<GrafoItemModel> list) {
        emptyMatriz(list);
        handleMatrizWithWeight(list);
    }

    private void handleMatrizWithWeight(List<GrafoItemModel> list) {
        list.forEach(grafoItemModel -> {
            grafoItemModel.getEdges().forEach(edge -> {

                String connection = GrafoUtils.getConnection(edge);
                String weight = GrafoUtils.getWeight(edge);
                weightMatriz[PositionUtils.getPosition(grafoItemModel.getVertex())][PositionUtils.getPosition(connection)] = weight;
            });
        });
    }

//    private void executeGrafo(List<GrafoItemModel> list) {
//        list.forEach(grafoItemModel -> {
//            grafoItemModel.getEdges().forEach(edge -> {
//                String connection = GrafoUtils.getConnection(edge);
//                adjascentMatriz[PositionUtils.getPosition(grafoItemModel.getVertex())][PositionUtils.getPosition(connection)] = EXIST_EDGE;
//            });
//        });
//    }

    private void emptyMatriz(List<GrafoItemModel> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int y = 0; y < list.size(); y++) {
                adjascentMatriz[i][y] = "0";
                weightMatriz[i][y] = "0";
            }
        }
    }

    @Override
    public void insert(String vertex, String edge) {
        String connection = GrafoUtils.getConnection(edge);
        adjascentMatriz[PositionUtils.getPosition(vertex)][PositionUtils.getPosition(connection)] = EXIST_EDGE;
    }

    @Override
    public void remove(String vertex, String edge) {
        String connection = GrafoUtils.getConnection(edge);
        adjascentMatriz[PositionUtils.getPosition(vertex)][PositionUtils.getPosition(connection)] = NOT_EXIST_EDGE;
    }

    @Override
    public Boolean isVertexThoughtful(String vertex) {
        boolean isThoughtful = false;
        for (int i = 0; i < weightMatriz.length; i++) {
            try {
                int weight = Integer.parseInt(weightMatriz[PositionUtils.getPosition(vertex)][i]);
                if (weight != 0) {
                    isThoughtful = true;
                }
            } catch (Exception error) {
                System.out.println("Erro ao verificar se o vertice é poderado." + error);
            }
        }

        return isThoughtful;
    }

    @Override
    public Boolean isVertexLabeled(String vertex) {
        boolean isLabeled = false;
        for (int i = 0; i < weightMatriz.length; i++) {
            try {
                Integer.parseInt(weightMatriz[PositionUtils.getPosition(vertex)][i]);
            } catch (Exception error) {
                isLabeled = true;
            }
        }

        return isLabeled;
    }

    @Override
    public Boolean isEdgeThoughtful(String edge) {
        boolean isThoughtful = false;

        try {
            int weight = Integer.parseInt(GrafoUtils.getWeight(edge));
            if (weight != 0) {
                isThoughtful = true;
            }
        } catch (Exception error) {
            System.out.println("Erro ao verificar se a aresta é poderada." + error);
        }

        return isThoughtful;
    }

    @Override
    public Boolean isEdgeLabeled(String edge) {
        boolean isLabeled = false;

        try {
            Integer.parseInt(GrafoUtils.getWeight(edge));
        } catch (Exception error) {
            isLabeled = true;
        }

        return isLabeled;
    }

}
