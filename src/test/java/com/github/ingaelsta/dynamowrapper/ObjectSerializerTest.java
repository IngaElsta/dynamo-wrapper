package com.github.ingaelsta.dynamowrapper;

import lombok.AllArgsConstructor;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectSerializerTest {
    private ObjectSerializer objectSerializer = new ObjectSerializer();

    @AllArgsConstructor
    private class TestClass {
        String s;
        int i;
        InnerTestClass innerTestClass;
    }

    @AllArgsConstructor
    private class InnerTestClass {
        double d;
        String s1;
    }

    @Test
    public void testSerializeSimpleObject() {
        assertEquals( "\"Test\"", objectSerializer.serialize("Test"));
        assertEquals( "17", objectSerializer.serialize(17));
        assertEquals("null", objectSerializer.serialize(null));
        assertEquals("false", objectSerializer.serialize(false));
        assertEquals("true", objectSerializer.serialize(true));
    }

    @Test
    public void testSerializePOJO() {
        TestClass tc = new TestClass("outside", 5, new InnerTestClass(6.15, "inside"));
        assertEquals(
                "{\"s\":\"outside\",\"i\":5,\"innerTestClass\":{\"d\":6.15,\"s1\":\"inside\"}}",
                objectSerializer.serialize(tc));
    }
}