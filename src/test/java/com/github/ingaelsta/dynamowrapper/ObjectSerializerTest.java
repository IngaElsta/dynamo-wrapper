package com.github.ingaelsta.dynamowrapper;

import lombok.AllArgsConstructor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @AllArgsConstructor
    private class TestClassList {
        String s;
        int i;
        List<String> stringList;

    }

    @Test
    public void testSerializeSimpleObject() {
        assertEquals( "\"Test\"", objectSerializer.serialize("Test"));
        assertEquals( "17", objectSerializer.serialize(17));
        assertEquals("null", objectSerializer.serialize(null));
        assertEquals("false", objectSerializer.serialize(false));
        assertEquals("true", objectSerializer.serialize(true));
        assertThrows(FeatureNotImplementedException.class, () -> objectSerializer.serialize(new HashMap<String, Integer>()));
    }

    @Test
    public void testSerializePOJO() {
        TestClass tc = new TestClass("outside", 5, new InnerTestClass(6.15, "inside"));
        assertEquals(
                "{\"s\":\"outside\",\"i\":5,\"innerTestClass\":{\"d\":6.15,\"s1\":\"inside\"}}",
                objectSerializer.serialize(tc));


        List<String> stringList = new ArrayList<>();
        stringList.add("first");
        stringList.add("second");
        TestClassList tcl = new TestClassList("outside", 5, stringList);
        assertEquals("{\"s\":\"outside\",\"i\":5,\"stringList\":[\"first\",\"second\"]}", objectSerializer.serialize(tcl));
    }

    @Test
    public void testSerializeList() {
        List<String> stringList = new ArrayList<>();
        stringList.add("first");
        stringList.add("second");
        assertEquals("[\"first\",\"second\"]", objectSerializer.serialize(stringList));
        assertEquals("[]", objectSerializer.serialize(new ArrayList<>()));
    }
}