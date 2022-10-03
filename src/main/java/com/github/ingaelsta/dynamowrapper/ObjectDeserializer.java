package com.github.ingaelsta.dynamowrapper;

public class ObjectDeserializer {

    private static class WrongTypeException extends RuntimeException {
        WrongTypeException (String message) {
            super(message);
        }
    }

    private static class WrongSyntaxException extends RuntimeException {
        WrongSyntaxException (String message) {
            super(message);
        }
    }

    private class FeatureNotImplementedException extends RuntimeException {
        FeatureNotImplementedException(String message) {
            super(message);
        }
    }

    public <T> T deserialize (String json, Class<T> clazz) {
        if ((json.length() == 0) || json.equals("null")) {
            return null;
        }
        //possible string
        if (json.charAt(0) == '\"') {
            if (clazz != String.class) {
                throw new WrongTypeException (String.format("Expected %s but got String", clazz.getName()));
            }
            if (json.charAt(json.length() - 1) != '\"') {
                throw new  WrongSyntaxException("String not finished properly");
            }
            return (T) json.substring(1, json.length() - 1);
        }
        //possible number
        if (Character.isDigit(json.charAt(0))) {
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
        throw new FeatureNotImplementedException("%s maps to a class not implemented yet");
    }
}
