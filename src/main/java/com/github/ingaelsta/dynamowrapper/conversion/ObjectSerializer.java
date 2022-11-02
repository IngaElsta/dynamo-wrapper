package com.github.ingaelsta.dynamowrapper.conversion;

import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class ObjectSerializer {

    public String serialize (Object object) {
        if (object instanceof String) {
            return String.format("\"%s\"", object);
        }
        if ((object instanceof Number) || (object instanceof Boolean)) {
            return object.toString();
        }
        //todo: if a collection
        //list or can it be some other collection?
        if (object instanceof Collection) {
            return processCollection((Collection) object);
        }
        //todo: implement actual processing if a map; for now throws exception
        if (object instanceof Map) {
            throw new FeatureNotImplementedException("Map processing currently not implemented");
        }
        //todo: implement actual processing if a date; for now throws exception
        if (object instanceof Date) {
            throw new FeatureNotImplementedException("Date processing currently not implemented");
        }
        //todo: check if there other options
        if (object == null) {
            return "null";
        }
        //default for pojo
        return processPOJO(object);
    }

    private String processPOJO(Object object) {
        Class clazz = object.getClass();
        List<Field> fieldList = Arrays.asList(clazz.getDeclaredFields());
        String json = fieldList.stream()
                .map(field -> {
                    field.setAccessible(true);
                    return processPOJOField(object, field);
                })
                .reduce("{", (partialString, fieldJson) -> {
                    return partialString + fieldJson;
                });
        return (json.charAt(json.length() - 1) == ',')
                ?  json.substring(0, json.length() - 1) + "}"
                : json + "}";
    }

    private String processPOJOField(Object object, Field field) {
        String fieldName = field.getName();
        //ignore references to containing objects
        if (fieldName.contains("this$")) {
            return "";
        }
        try {
            Object fieldContent = field.get(object);
            String fieldContentAsText = serialize(fieldContent);
            return String.format("\"%s\":%s,", field.getName(), fieldContentAsText);
        } catch (Exception e) {
            System.out.println("Field " + fieldName + " not found in object");
            throw new RuntimeException("Field " + fieldName + " not found in object");
        }
    }

    private String processCollection(Collection<?> objectAsCollection) {
        if ((objectAsCollection.size() == 0)) {
            return "[]";
        }
        String listAsText = objectAsCollection.stream()
                .map(this::serialize)
                .reduce("", (partialString, fieldJson) -> partialString + "," + fieldJson);
        return String.format("[%s]", listAsText.substring(1));
    }
}
