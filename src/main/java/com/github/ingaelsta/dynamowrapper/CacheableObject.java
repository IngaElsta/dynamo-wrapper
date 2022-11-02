package com.github.ingaelsta.dynamowrapper;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
@DynamoDBTable(tableName="DYNAMO_WRAPPER")
public class CacheableObject <T> {

    private final ObjectMapper objectMapper;

    private String hashKey;
    private String objectClass;
    private long ttl;
    private String cacheableJson;

    @DynamoDBAttribute
    public long getTtl() {
        return ttl;
    }
    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    @DynamoDBAttribute
    public String getCacheableJson() {
        return cacheableJson;
    }
    public void setCacheableJson(String cacheableJson) {
        this.cacheableJson = cacheableJson;
    }

    @DynamoDBRangeKey
    public String getHashKey() {
        return hashKey;
    }
    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    @DynamoDBHashKey
    public String getObjectClass() {
        return objectClass;
    }
    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

    public <T> String serialize (T t) {
        try {
            return objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            log.error("Serializing failed : %s", t.toString());
            throw new  RuntimeException(e);
        }
    }

    public <T> T deserialize (String json, Class<T> clazz) throws JsonProcessingException {
        try {
        return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("Deserializing failed: %s", json);
            throw new  RuntimeException(e);
        }
    }
}
