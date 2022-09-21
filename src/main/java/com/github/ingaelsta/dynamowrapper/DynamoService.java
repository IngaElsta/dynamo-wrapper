package com.github.ingaelsta.dynamowrapper;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class DynamoService {

    public ObjectWrapper getObject (String dbName) {
        //todo: implement this
        return new ObjectWrapper();
    }

    public List<ObjectWrapper> getAllObjects (String dbName) {
        //todo: implement this
        return new ArrayList<ObjectWrapper>();
    }

    public String saveObject (String objectId, String object, String dbName) {
        //todo: implement this
        return "hasId placeholder";
    }

    public void deleteObject (String objectId, String dbName) {
        //todo: implement this
    }
}
