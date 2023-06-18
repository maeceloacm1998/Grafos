package models;

public class Edge {
    private String connection;
    private String weight;

    public Edge(String connection, String weight) {
        this.connection = connection;
        this.weight = weight;
    }

    public String getConnection() {
        return connection;
    }

    public String getWeight() {
        return weight;
    }
}