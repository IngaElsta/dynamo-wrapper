package com.github.ingaelsta.dynamowrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ObjectWrapper <T> {
    //todo: add dynamodb annotations
    private int hashID;
    private int sortID;
    @Getter
    private T t;
    private int ttl;

    public ObjectWrapper (T t) {
        this.t = t;
    }
}
