package utils;

import models.GraphItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Position {
    private static Map<String,Integer> vertices;
    public static final Integer NOT_EXIST_POSITION = -1;

    public Position(List<GraphItem> list) {
        vertices = new HashMap<>();
        list.forEach(graphItem -> {
            vertices.put(graphItem.getVertex(), list.indexOf(graphItem));
        });
    }

    public static int getPosition(String vertex) {
        try {
            return vertices.get(vertex);
        } catch (Exception error) {
            System.out.println(error + " - A posição "+vertex+" não existe.");
        }
        return NOT_EXIST_POSITION;
    }


}
