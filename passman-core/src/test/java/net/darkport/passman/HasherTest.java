package net.darkport.passman;

import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HasherTest {

    @Test
    void testHappy() throws Exception {
        byte[] hash = new Hasher().hash("happy path");
        String actual = Base64.getEncoder().encodeToString(hash);
        assertEquals("U+K1ffrTE1aYRS7GrxiF7nE8CzyAKGbpZ7geaioxZ28=", actual);
    }
}
