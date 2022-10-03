package com.github.ingaelsta.dynamowrapper;

import org.junit.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ObjectDeserializerTest {
    private ObjectDeserializer deserializer = new ObjectDeserializer();

    private class TestClass {
        String s;
        int i;
        InnerTestClass innerTestClass;

        public TestClass(String s, int i, InnerTestClass innerTestClass) {
            this.s = s;
            this.i = i;
            this.innerTestClass = innerTestClass;
        }
    }

    private class InnerTestClass {
        double d;
        String s1;

        public InnerTestClass(double d, String s1) {
            this.d = d;
            this.s1 = s1;
        }
    }

    @Test
    public void testDeserializeSimpleValues () {
        assertEquals(null, deserializer.deserialize("null",  Object.class));
        assertEquals("string", deserializer.deserialize("\"string\"", String.class));
        assertEquals(5, deserializer.deserialize("5", Integer.class));
        assertEquals(5.3, deserializer.deserialize("5.3", Double.class));
        assertThrows(NumberFormatException.class, () -> deserializer.deserialize("5a", Integer.class));
        assertThrows(ObjectDeserializer.WrongSyntaxException.class, () -> deserializer.deserialize("\"5a", String.class));
    }

    public void testDeserialize() {
            String json = "{d:6.15,s1:inside}";
//            InnerTestClass inner = deserializer.deserialize(json, InnerTestClass.class);
    }
}