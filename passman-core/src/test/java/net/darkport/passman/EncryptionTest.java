package net.darkport.passman;

import net.darkport.passman.model.EncryptionData;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EncryptionTest {

    @Test
    void testEncryptionAndDecryption() throws Exception {
        final String plain = "is this working?";
        EncryptionData data = new Encryption().withPassword("hello".toCharArray())
                                              .encrypt(plain.getBytes(StandardCharsets.UTF_8));
        byte[] decryptedBytes = new Decryption(data).decrypt("hello".toCharArray());
        String decrypted = new String(decryptedBytes, StandardCharsets.UTF_8);
        assertEquals(plain, decrypted);
    }

    @Test
    void testBadPassword() throws Exception {
        final String plain = "i forgot the password";
        EncryptionData data = new Encryption().withPassword("hello".toCharArray())
                                              .encrypt(plain.getBytes(StandardCharsets.UTF_8));
        assertThrows(BadPaddingException.class, () -> new Decryption(data).decrypt("hello2".toCharArray()));
    }

    @Test
    void testWithAAD() throws Exception {
        byte[] plain = "some sample data".getBytes(StandardCharsets.UTF_8);
        byte[] aad = "extra data".getBytes(StandardCharsets.UTF_8);
        EncryptionData data = new Encryption().withPassword("hello".toCharArray())
                                              .encrypt(plain, aad);
        byte[] decryptedBytes = new Decryption(data).decrypt("hello".toCharArray());
        String decrypted = new String(decryptedBytes, StandardCharsets.UTF_8);
        assertEquals("some sample data", decrypted);
    }

    @Test
    void testManipulatedAAD() throws Exception {
        byte[] plain = "some sample data".getBytes(StandardCharsets.UTF_8);
        byte[] aad = "extra data".getBytes(StandardCharsets.UTF_8);
        EncryptionData data = new Encryption().withPassword("hello".toCharArray())
                                              .encrypt(plain, aad);
        // manipulated AAD
        data.setAad("extra datb".getBytes(StandardCharsets.UTF_8));
        assertThrows(BadPaddingException.class, () -> new Decryption(data).decrypt("hello".toCharArray()));
    }
}
