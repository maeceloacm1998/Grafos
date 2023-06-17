package libs;

import models.GraphItem;
import models.IAdjacencyMatrix;
import utils.Graph;
import utils.Position;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class AdjacencyMatrix implements IAdjacencyMatrix {
    private final String EXIST_EDGE = "1";
    private final String NOT_EXIST_EDGE = "0";

    String[][] adjascentMatriz;
    String[][] weightMatriz;

    public AdjacencyMatrix(List<GraphItem> list) {
        adjascentMatriz = new String[list.size()][list.size()];
        weightMatriz = new String[list.size()][list.size()];
        new Position(list);
        init(list);
    }

    private void init(List<GraphItem> list) {
        emptyMatriz(list);
        handleMatrizWithWeight(list);
        executeGrafo(list);
    }

    private void handleMatrizWithWeight(List<GraphItem> list) {
        list.forEach(grafoItemModel -> {
            grafoItemModel.getEdges().forEach(edge -> {
                String connection = Graph.getConnection(edge);
                String weight = Graph.getWeight(edge);
                weightMatriz[Position.getPosition(grafoItemModel.getVertex())][Position.getPosition(connection)] = weight;
            });
        });
    }

    private void executeGrafo(List<GraphItem> list) {
        list.forEach(grafoItemModel -> {
            grafoItemModel.getEdges().forEach(edge -> {
                String connection = Graph.getConnection(edge);
                adjascentMatriz[Position.getPosition(grafoItemModel.getVertex())][Position.getPosition(connection)] = EXIST_EDGE;
            });
        });
    }

    private void emptyMatriz(List<GraphItem> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int y = 0; y < list.size(); y++) {
                adjascentMatriz[i][y] = "0";
                weightMatriz[i][y] = "0";
            }
        }
    }

    @Override
    public void insert(GraphItem grafoItem) {
        if (existGrafoItem(grafoItem.getVertex()) && connectionIsValid(grafoItem)) {
            insertNewConnection(grafoItem);
            insertNewWeight(grafoItem);
        }
    }

    private void insertNewConnection(GraphItem grafoItem) {
        grafoItem.getEdges().forEach(edge -> {
            String connection = Graph.getConnection(edge);
            adjascentMatriz[Position.getPosition(grafoItem.getVertex())][Position.getPosition(connection)] = EXIST_EDGE;
        });
    }

    private void insertNewWeight(GraphItem grafoItem) {
        grafoItem.getEdges().forEach(edge -> {
            String connection = Graph.getConnection(edge);
            String weight = Graph.getWeight(edge);
            weightMatriz[Position.getPosition(grafoItem.getVertex())][Position.getPosition(connection)] = weight;
        });
    }

    private Boolean existGrafoItem(String vertex) {
        int position = Position.getPosition(vertex);
        return position != Position.NOT_EXIST_POSITION;
    }

    private Boolean connectionIsValid(GraphItem grafoItem) {
        AtomicReference<Boolean> isValid = new AtomicReference<>(true);
        grafoItem.getEdges().forEach(edge -> {
            String connection = Graph.getConnection(edge);
            if (Position.getPosition(connection) == Position.NOT_EXIST_POSITION) {
                isValid.set(false);
            }
        });
        return isValid.get();
    }

    @Override
    public void remove(String vertex, String edge) {
        String connection = Graph.getConnection(edge);
        adjascentMatriz[Position.getPosition(vertex)][Position.getPosition(connection)] = NOT_EXIST_EDGE;
    }

    @Override
    public Boolean isVertexThoughtful(String vertex) {
        boolean isThoughtful = false;
        for (int i = 0; i < weightMatriz.length; i++) {
            try {
                int weight = Integer.parseInt(weightMatriz[Position.getPosition(vertex)][i]);
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
                Integer.parseInt(weightMatriz[Position.getPosition(vertex)][i]);
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
            int weight = Integer.parseInt(Graph.getWeight(edge));
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
            Integer.parseInt(Graph.getWeight(edge));
        } catch (Exception error) {
            isLabeled = true;
        }

        return isLabeled;
    }

    @Override
    public Boolean isAdjacentVertex(String vertexOne, String vertexTwo) {
        int positionVertexOne = Position.getPosition(vertexOne);
        int positionVertexTwo = Position.getPosition(vertexTwo);

        return adjascentMatriz[positionVertexOne][positionVertexTwo].equals(EXIST_EDGE)
                || adjascentMatriz[positionVertexTwo][positionVertexOne].equals(EXIST_EDGE);
    }

    @Override
    public Boolean isAdjacentEdge(String connectionOne, String connectionTwo) {
        String[] splitConnectionOne = connectionOne.split("-");
        String[] splitConnectionTwo = connectionTwo.split("-");

        if (validateAdjacentEdges(splitConnectionOne, splitConnectionTwo)) {
            return adjascentMatriz[Position.getPosition(splitConnectionOne[0])][Position.getPosition(splitConnectionOne[1])].equals(EXIST_EDGE)
                    && adjascentMatriz[Position.getPosition(splitConnectionTwo[0])][Position.getPosition(splitConnectionOne[1])].equals(EXIST_EDGE);
        }
        return false;
    }

    private Boolean validateAdjacentEdges(String[] splitConnectionOne, String[] splitConnectionTwo) {
        Boolean firstValidation = isEqualsVertex(splitConnectionOne[0], splitConnectionTwo[0]) || isEqualsVertex(splitConnectionOne[0], splitConnectionTwo[1]);
        Boolean secondValidate = isEqualsVertex(splitConnectionOne[1], splitConnectionTwo[0]) || isEqualsVertex(splitConnectionOne[1], splitConnectionTwo[1]);
        return secondValidate || firstValidation;
    }

    private Boolean isEqualsVertex(String vertexOne, String vertexTwo) {
        return vertexOne.equals(vertexTwo);
    }
}
