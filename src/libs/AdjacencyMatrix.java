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

    /**
     * Construtor da classe de matriz de adjascencia
     *
     * @param list Lista de itens do grafo (vértices e arestas)
     */
    public AdjacencyMatrix(List<GraphItem> list) {
        adjacencyMatrix = new String[list.size()][list.size()];
        weightMatrix = new String[list.size()][list.size()];
        new Position(list);
        init(list);
    }

    /**
     * Inicializa a matriz de adjascencia
     *
     * @param list Lista de itens do grafo (vértices e arestas)
     */
    private void init(List<GraphItem> list) {
        emptyMatrix(list);
        handleMatrixWithWeight(list);
        executeGraph(list);
    }

    /**
     * Cria a list de matrix com os pesos de cada conecção
     *
     * @param list Lista de itens do grafo (vértices e arestas)
     */
    private void handleMatrixWithWeight(List<GraphItem> list) {
        list.forEach(graphItemModel -> {
            graphItemModel.getEdges().forEach(edge -> {
                String connection = Graph.getConnection(edge);
                String weight = Graph.getWeight(edge);
                weightMatrix[Position.getPosition(graphItemModel.getVertex())][Position.getPosition(connection)] = weight;
            });
        });
    }

    /**
     * Cria a list de matrix adjascente
     *
     * @param list Lista de itens do grafo (vértices e arestas)
     */
    private void executeGraph(List<GraphItem> list) {
        list.forEach(graphItemModel -> {
            graphItemModel.getEdges().forEach(edge -> {
                String connection = Graph.getConnection(edge);
                adjacencyMatrix[Position.getPosition(graphItemModel.getVertex())][Position.getPosition(connection)] = EXIST_EDGE;
            });
        });
    }

    /**
     * Usado para criar uma matriz em que todas posições saão 0
     *
     * @param list Lista de itens do grafo (vértices e arestas)
     */
    private void emptyMatrix(List<GraphItem> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int y = 0; y < list.size(); y++) {
                adjacencyMatrix[i][y] = "0";
                weightMatrix[i][y] = "0";
            }
        }
    }

    /**
     * Insere um vértice na matriz de adjasncencia
     *
     * @param graphItem Lista de itens do grafo (vértices e arestas)
     */
    @Override
    public void insert(GraphItem graphItem) {
        if (existGraphItem(graphItem.getVertex()) && connectionIsValid(graphItem)) {
            insertNewConnection(graphItem);
            insertNewWeight(graphItem);
        }
    }

    /**
     * inserir uma nova aresta existente com 1
     *
     * @param graphItem Lista de itens do grafo (vértices e arestas)
     */
    private void insertNewConnection(GraphItem graphItem) {
        graphItem.getEdges().forEach(edge -> {
            String connection = Graph.getConnection(edge);
            adjacencyMatrix[Position.getPosition(graphItem.getVertex())][Position.getPosition(connection)] = EXIST_EDGE;
        });
    }

    /**
     * inserir um novo peso na matriz de pesos
     *
     * @param graphItem Lista de itens do grafo (vértices e arestas)
     */
    private void insertNewWeight(GraphItem graphItem) {
        graphItem.getEdges().forEach(edge -> {
            String connection = Graph.getConnection(edge);
            String weight = Graph.getWeight(edge);
            weightMatrix[Position.getPosition(graphItem.getVertex())][Position.getPosition(connection)] = weight;
        });
    }

    /**
     * Confere se existe aquele vertice.
     *
     * @param vertex vertice
     */
    private Boolean existGraphItem(String vertex) {
        int position = Position.getPosition(vertex);
        return position != Position.NOT_EXIST_POSITION;
    }

    /**
     * Valida se aquela connecção é valida, se esta preenchida com 1
     *
     * @param graphItem Lista de itens do grafo (vértices e arestas)
     */
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

    /**
     * Usado para remover um vertice na matrix, colocando em vez de 1 para 0
     *
     * @param vertex vértices
     * @param edge arestas
     */
    @Override
    public void remove(String vertex, String edge) {
        String connection = Graph.getConnection(edge);
        adjacencyMatrix[Position.getPosition(vertex)][Position.getPosition(connection)] =
                NOT_EXIST_EDGE;
    }

    /**
     * Verifica se o vertice é ponderado
     *
     * @param vertex Vértice a ser verificado
     */
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

    /**
     * Verifica se o vertice é rotulado
     *
     * @param vertex Vértice a ser verificado
     */
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

    /**
     * Verifica se a aresta é ponderada
     *
     * @param edge Aresta a ser verificada
     */
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

    /**
     * Verifica se a aresta é rotulada
     *
     * @param edge Aresta a ser verificada
     */
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

    /**
     * Verifica se o vértice é adjascente ao outro vértice
     *
     * @param vertexOne vertice um para comparação
     * @param vertexTwo  vertice dois para comparação
     */
    @Override
    public Boolean isAdjacentVertex(String vertexOne, String vertexTwo) {
        int positionVertexOne = Position.getPosition(vertexOne);
        int positionVertexTwo = Position.getPosition(vertexTwo);

        return adjacencyMatrix[positionVertexOne][positionVertexTwo].equals(EXIST_EDGE)
                || adjacencyMatrix[positionVertexTwo][positionVertexOne].equals(EXIST_EDGE);
    }

    /**
     * Verifica se o vértice é adjascente ao outro vértice
     *
     * @param connectionOne aresta um para comparação
     * @param connectionTwo  aresta dois para comparação
     */
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

    /**
     * Valida se é possiível calcular se saão arestas adjascentes
     * pois, caso as arestas comparadas sejasm com os mesmos vertices,
     * não tem o que comparar.
     *
     * @param splitConnectionOne aresta um para comparação
     * @param splitConnectionTwo  aresta dois para comparação
     */
    private Boolean validateAdjacentEdges(String[] splitConnectionOne, String[] splitConnectionTwo) {
        Boolean firstValidation = isEqualsVertex(splitConnectionOne[0], splitConnectionTwo[0]) || isEqualsVertex(splitConnectionOne[0], splitConnectionTwo[1]);
        Boolean secondValidate = isEqualsVertex(splitConnectionOne[1], splitConnectionTwo[0]) || isEqualsVertex(splitConnectionOne[1], splitConnectionTwo[1]);
        return secondValidate || firstValidation;
    }

    private Boolean isEqualsVertex(String vertexOne, String vertexTwo) {
        return vertexOne.equals(vertexTwo);
    }

    /**
     * Verifica se existe arestas na lista adjascente
     *
     * @return Boolean se existir arestas na lista adjascente
     */
    @Override
    public Boolean existEdge() {
        boolean existEdge = false;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int y = 0; y < adjacencyMatrix.length; y++) {
                if (adjacencyMatrix[i][y].equals(EXIST_EDGE)) {
                    existEdge = true;
                }
            }
        }
        return existEdge;
    }

    /**
     * Retorna a quantidade de arestas na lista adjascente
     *
     * @return Quantidade de arestas na lista adjascente
     */
    @Override
    public Integer quantityEdge() {
        int quantityEdge = 0;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int y = 0; y < adjacencyMatrix.length; y++) {
                if (adjacencyMatrix[i][y].equals(EXIST_EDGE)) {
                    quantityEdge++;
                }
            }
        }
        return quantityEdge;
    }


    /**
     * Retorna a quantidade de vértices na lista adjascente
     *
     * @return Quantidade de vértices na lista adjascente
     */
    @Override
    public Integer quantityVertex() {
        return adjacencyMatrix.length;
    }

    /**
     * Retorna se a lista adjascente está vazia
     *
     * @return Boolean se a lista adjascente está vazia
     */
    @Override
    public Boolean isEmpty() {
        return adjacencyMatrix.length == 0;
    }

    /**
     * Retorna se a lista adjascente é completa
     *
     * @return Boolean se a lista adjascente é completa
     */
    @Override
    public Boolean isComplet() {
        boolean isComplet = true;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int y = 0; y < adjacencyMatrix.length; y++) {
                if (i != y && adjacencyMatrix[i][y].equals(NOT_EXIST_EDGE)) {
                    isComplet = false;
                }
            }
        }
        return isComplet;
    }
}
