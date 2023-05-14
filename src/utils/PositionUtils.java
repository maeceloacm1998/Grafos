package utils;

import models.GrafoItemModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionUtils {
    static Map<String,Integer> vertices;

    public PositionUtils(List<GrafoItemModel> list) {
        vertices = new HashMap<>();
        list.forEach(grafoItemModel -> {
            vertices.put(grafoItemModel.getVertex(), list.indexOf(grafoItemModel));
        });
    }

    public static int getPosition(String vertex) {
        return vertices.get(vertex);
    }
}
