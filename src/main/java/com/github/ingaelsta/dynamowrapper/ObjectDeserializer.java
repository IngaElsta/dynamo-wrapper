package com.github.ingaelsta.dynamowrapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectDeserializer {

    public static class WrongTypeException extends RuntimeException {
        WrongTypeException (String message) {
            super(message);
        }
    }

    public static class WrongSyntaxException extends RuntimeException {
        WrongSyntaxException (String message) {
            super(message);
        }
    }

    public <T> T deserialize (String json, Class<T> clazz) {
        if ((json.length() == 0) || json.equals("null")) {
            return null;
        }
        //possible string
        if (json.charAt(0) == '\"') {
            return parseString(json, clazz);
        }
        //possible number
        if (Character.isDigit(json.charAt(0))) {
            return parseNumber(json, clazz);
        }
        //possible list
        if (json.charAt(0) == '[') {
            return parseList(json, clazz);

        }
        throw new FeatureNotImplementedException(String.format("Deserializing %s has not been implemented", clazz.getName()));
    }

    private static <T> T parseList(String json, Class<T> clazz) {
        if (!(List.class.isAssignableFrom(clazz))) {
            throw new WrongTypeException (String.format("Expected %s but got List", clazz.getName()));
        }
        if (json.charAt(json.length() - 1) != ']') {
            throw new  WrongSyntaxException(String.format("List %s not finished properly", json));
        }
        String listJson = json.substring(1, json.length() - 1);
        //each element must be either number

        //or string

        //or list

        //or something within {}

        //or something else - either corrupt or unsupported.
        //todo: implement all the others so something can be returned
        throw new FeatureNotImplementedException(String.format("The contents of json are either corrupt or of unsupported type: \n%s", json));
    }

    private static <T> T parseNumber(String json, Class<T> clazz) {
        if (!(Number.class.isAssignableFrom(clazz))) {
            throw new WrongTypeException (String.format("Expected %s but got Number", clazz.getName()));
        }
        if (clazz == Integer.class) {
            return (T) Integer.valueOf(json);
        }
        if (clazz == Long.class) {
            return (T) Long.valueOf(json);
        }
        if (clazz == Float.class) {
            return (T) Float.valueOf(json);
        }
        if (clazz == Double.class) {
            return (T) Double.valueOf(json);
        }
        throw new FeatureNotImplementedException("%s is a number that can't be parsed");
    }

    private static <T> T parseString(String json, Class<T> clazz) {
        if (clazz != String.class) {
            throw new WrongTypeException (String.format("Expected %s but got String", clazz.getName()));
        }
        if (json.charAt(json.length() - 1) != '\"') {
            throw new  WrongSyntaxException(String.format("String %s not finished properly", json));
        }
        return (T) json.substring(1, json.length() - 1);
    }
}
