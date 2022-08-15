package com.github.ingaelsta.dynamowrapper;

import junit.framework.TestCase;

public class ObjectSerializerTest extends TestCase {
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

    public void testSerialize() {
        TestClass tc = new TestClass("outside", 5, new InnerTestClass(6.15, "inside"));
        System.out.println(new ObjectSerializer().serialize(tc));
    }
}