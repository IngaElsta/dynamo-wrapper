package com.github.ingaelsta.dynamowrapper.testclasses;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TestClass {
    String s;
    int i;
    InnerTestClass innerTestClass;
}
