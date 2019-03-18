package net.darkport.passman;

import net.darkport.passman.model.EncryptionData;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
