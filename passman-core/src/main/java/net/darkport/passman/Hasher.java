package net.darkport.passman;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

    private static final String SHA_256 = "SHA-256";

    private String algorithm;

    public Hasher() {
        this.algorithm = SHA_256;
    }

    public Hasher withAlgorithm(String algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public byte[] hash(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        return digest.digest(input.getBytes(StandardCharsets.UTF_8));
    }
}
