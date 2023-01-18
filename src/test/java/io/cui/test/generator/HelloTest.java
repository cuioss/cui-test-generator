package io.cui.test.generator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.cui.test.generator.Hello;


class HelloTest {

    @Test
    void test() {
       assertEquals("Hello cui", new Hello().hello("cui"));
    }

}
