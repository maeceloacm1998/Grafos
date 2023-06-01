package utils;

import models.GrafoItemModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionUtils {
    private static Map<String,Integer> vertices;
    public static final Integer NOT_EXIST_POSITION = -1;

    public PositionUtils(List<GrafoItemModel> list) {
        vertices = new HashMap<>();
        list.forEach(grafoItemModel -> {
            vertices.put(grafoItemModel.getVertex(), list.indexOf(grafoItemModel));
        });
    }

    public static int getPosition(String vertex) {
        try {
            return vertices.get(vertex);
        } catch (Exception error) {
            System.out.println(error + " - A posicao "+vertex+" nao existe");
        }
        return NOT_EXIST_POSITION;
    }


}
