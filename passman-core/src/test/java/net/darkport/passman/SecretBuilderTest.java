package net.darkport.passman;

import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SecretBuilderTest {

    @Test
    void testHappy() throws Exception {
        char[] pw = "happy".toCharArray();
        byte[] salt = new Hasher().hash("path");
        assertEquals(SecretBuilder.KEY_LENGTH_BYTES, salt.length);
        // key length is expressed in bits, but the salt is expressed in bytes
        assertEquals(SecretBuilder.KEY_LENGTH_BITS, salt.length * 8);

        SecretKey key = new SecretBuilder(pw, salt).buildKey();
        assertNotNull(key);
    }
}
