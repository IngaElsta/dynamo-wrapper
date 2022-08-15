package com.github.ingaelsta.dynamowrapper;

import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
public class ObjectSerializer {
    public String serialize (Object object) {
        String json = "";
        Class clazz = object.getClass();
        List<Field> fieldList = Arrays.asList(clazz.getDeclaredFields());
        json = fieldList.stream()
                .map(field -> {
                    Class fieldClass = field.getClass();
                    try {
                        return getFieldJson(object, field);
                    } catch (Exception e) {
                        System.out.println("field " + field.getName() + " not found in object");
                        throw new RuntimeException("oi!");
                    }
                })
                .reduce("{", (partialString, fieldJson) ->
                {
                    return partialString + fieldJson;
                });
        return fixJsonEnding(json);
    }

    private static String fixJsonEnding (String json) {
        return (json.charAt(json.length() - 1) == ',')
                ?  json.substring(0, json.length() - 1) + "}"
                : json + "}";
    }

    private String getFieldJson(Object object, Field field) throws IllegalAccessException {
        String fieldContentAsText = "";
        Object fieldContent = field.get(object);
        //todo: ignore this$0:{}
        //if string or number
        if ((fieldContent instanceof String) || (fieldContent instanceof Number)) {
            fieldContentAsText = fieldContent.toString();
        }
        //todo: if a collection
        //todo: if a map
        //if another pojo
        else {
            fieldContentAsText = serialize(fieldContent);
        }
        //todo: check if there other options
        String fieldJson = field.getName() + ":" + fieldContentAsText + ",";
        return fieldJson;
    }
}
