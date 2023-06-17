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

    String[][] adjacencyMatrix;
    String[][] weightMatrix;

    public AdjacencyMatrix(List<GraphItem> list) {
        adjacencyMatrix = new String[list.size()][list.size()];
        weightMatrix = new String[list.size()][list.size()];
        new Position(list);
        init(list);
    }

    private void init(List<GraphItem> list) {
        emptyMatrix(list);
        handleMatrixWithWeight(list);
        executeGraph(list);
    }

    private void handleMatrixWithWeight(List<GraphItem> list) {
        list.forEach(graphItemModel -> {
            graphItemModel.getEdges().forEach(edge -> {
                String connection = Graph.getConnection(edge);
                String weight = Graph.getWeight(edge);
                weightMatrix[Position.getPosition(graphItemModel.getVertex())][Position.getPosition(connection)] = weight;
            });
        });
    }

    private void executeGraph(List<GraphItem> list) {
        list.forEach(graphItemModel -> {
            graphItemModel.getEdges().forEach(edge -> {
                String connection = Graph.getConnection(edge);
                adjacencyMatrix[Position.getPosition(graphItemModel.getVertex())][Position.getPosition(connection)] = EXIST_EDGE;
            });
        });
    }

    private void emptyMatrix(List<GraphItem> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int y = 0; y < list.size(); y++) {
                adjacencyMatrix[i][y] = "0";
                weightMatrix[i][y] = "0";
            }
        }
    }

    @Override
    public void insert(GraphItem graphItem) {
        if (existGraphItem(graphItem.getVertex()) && connectionIsValid(graphItem)) {
            insertNewConnection(graphItem);
            insertNewWeight(graphItem);
        }
    }

    private void insertNewConnection(GraphItem graphItem) {
        graphItem.getEdges().forEach(edge -> {
            String connection = Graph.getConnection(edge);
            adjacencyMatrix[Position.getPosition(graphItem.getVertex())][Position.getPosition(connection)] = EXIST_EDGE;
        });
    }

    private void insertNewWeight(GraphItem graphItem) {
        graphItem.getEdges().forEach(edge -> {
            String connection = Graph.getConnection(edge);
            String weight = Graph.getWeight(edge);
            weightMatrix[Position.getPosition(graphItem.getVertex())][Position.getPosition(connection)] = weight;
        });
    }

    private Boolean existGraphItem(String vertex) {
        int position = Position.getPosition(vertex);
        return position != Position.NOT_EXIST_POSITION;
    }

    private Boolean connectionIsValid(GraphItem graphItem) {
        AtomicReference<Boolean> isValid = new AtomicReference<>(true);
        graphItem.getEdges().forEach(edge -> {
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
        adjacencyMatrix[Position.getPosition(vertex)][Position.getPosition(connection)] = NOT_EXIST_EDGE;
    }

    @Override
    public Boolean isVertexThoughtful(String vertex) {
        boolean isThoughtful = false;
        for (int i = 0; i < weightMatrix.length; i++) {
            try {
                int weight = Integer.parseInt(weightMatrix[Position.getPosition(vertex)][i]);
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
        for (int i = 0; i < weightMatrix.length; i++) {
            try {
                Integer.parseInt(weightMatrix[Position.getPosition(vertex)][i]);
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

        return adjacencyMatrix[positionVertexOne][positionVertexTwo].equals(EXIST_EDGE)
                || adjacencyMatrix[positionVertexTwo][positionVertexOne].equals(EXIST_EDGE);
    }

    @Override
    public Boolean isAdjacentEdge(String connectionOne, String connectionTwo) {
        String[] splitConnectionOne = connectionOne.split("-");
        String[] splitConnectionTwo = connectionTwo.split("-");

        if (validateAdjacentEdges(splitConnectionOne, splitConnectionTwo)) {
            return adjacencyMatrix[Position.getPosition(splitConnectionOne[0])][Position.getPosition(splitConnectionOne[1])].equals(EXIST_EDGE)
                    && adjacencyMatrix[Position.getPosition(splitConnectionTwo[0])][Position.getPosition(splitConnectionOne[1])].equals(EXIST_EDGE);
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
