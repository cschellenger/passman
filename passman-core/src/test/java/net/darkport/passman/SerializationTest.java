package net.darkport.passman;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.darkport.passman.model.EncryptionData;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SerializationTest {

    @Test
    void testSerializeAndDecrypt() throws IOException {
        EncryptionData ed = new Encryption().withPassword("json2".toCharArray())
                                            .encryptUnchecked("some message".getBytes(StandardCharsets.UTF_8));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ed);
        assertNotNull(json);

        EncryptionData fromJson = objectMapper.readValue(json, EncryptionData.class);
        byte[] decrypted = new Decryption(fromJson).decryptUnchecked("json2".toCharArray());
        assertEquals("some message", new String(decrypted, StandardCharsets.UTF_8));
    }

    @Test
    public void testExistingData() throws IOException {
        String json = "{\n" +
                "  \"data\" : \"d2+SJ8SFrAvYTx1JQgcOGRvIbs17tNRNjNX4Uw==\",\n" +
                "  \"iv\" : \"awkXV+2tVjXAUvw1T8E2Jg==\",\n" +
                "  \"aad\" : \"51MgmYp+Goj7JCrtSDaxWw==\",\n" +
                "  \"cipherMeta\" : {\n" +
                "    \"algorithm\" : \"AES/GCM/NoPadding\",\n" +
                "    \"blockLength\" : 16\n" +
                "  },\n" +
                "  \"secretMeta\" : {\n" +
                "    \"algorithm\" : \"PBKDF2WithHmacSHA512\",\n" +
                "    \"iterations\" : 100000,\n" +
                "    \"length\" : 256,\n" +
                "    \"salt\" : \"fHRoEqXYb229Y8SMy8s/zlWyY6Kvun04n/3wjQ5Uyy0=\"\n" +
                "  }\n" +
                "}";
        ObjectMapper objectMapper = new ObjectMapper();
        EncryptionData fromJson = objectMapper.readValue(json, EncryptionData.class);
        byte[] decrypted = new Decryption(fromJson).decryptUnchecked("json".toCharArray());
        assertEquals("some message", new String(decrypted, StandardCharsets.UTF_8));
    }
}
