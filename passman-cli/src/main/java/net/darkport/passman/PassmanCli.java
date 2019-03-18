package net.darkport.passman;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.darkport.passman.model.EncryptionData;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

public class PassmanCli {

    static String decryptFile(String fileName, char[] password) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        EncryptionData ed = mapper.readValue(new File(fileName), EncryptionData.class);
        byte[] decrypted = new Decryption(ed).decryptUnchecked(password);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    static String encryptFile(String fileName, char[] password) throws IOException {
        byte[] plainText = Files.readAllBytes(new File(fileName).toPath());
        EncryptionData ed = new Encryption().withPassword(password).encryptUnchecked(plainText);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ed);
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: (encrypt|decrypt) <filename>");
            System.exit(1);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            if (Objects.equals(args[0], "decrypt")) {
                System.err.print("Password: ");
                char[] password = reader.readLine().toCharArray();
                System.out.println(decryptFile(args[1], password));
            }
            if (Objects.equals(args[0], "encrypt")) {
                System.err.print("Password: ");
                char[] password = reader.readLine().toCharArray();
                System.out.println(encryptFile(args[1], password));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
