package com.github.ingaelsta.dynamowrapper;

import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
            Collection fieldContentAsCollection = (Collection) object;
            return String.format("[%s]", processCollection((Collection<?>) object, fieldContentAsCollection));
        }
        //todo: if a map
        //todo: check if there other options
        if (object == null) {
            return "null";
        }
        //default for pojo
        Class clazz = object.getClass();
        List<Field> fieldList = Arrays.asList(clazz.getDeclaredFields());
        return processPOJO(object, fieldList);
    }

    private String processPOJO(Object object, List<Field> fieldList) {
        String json = fieldList.stream()
                .map(field -> {
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
                })
                .reduce("{", (partialString, fieldJson) -> {
                    return partialString + fieldJson;
                });
        return fixJsonEnding(json);
    }

    private static String fixJsonEnding (String json) {
        return (json.charAt(json.length() - 1) == ',')
                ?  json.substring(0, json.length() - 1) + "}"
                : json + "}";
    }

    private String processCollection(Collection<?> fieldContent, Collection fieldContentAsCollection) {
        if ((fieldContentAsCollection.size() == 0) || (fieldContentAsCollection == null)) {
            return "";
        }

        String listAsText = fieldContent.stream()
                .map(this::serialize)
                .reduce("", (partialString, fieldJson) -> partialString + "," + fieldJson);

        return listAsText.substring(1);
    }
}
