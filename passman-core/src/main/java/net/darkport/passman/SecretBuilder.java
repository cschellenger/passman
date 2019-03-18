package net.darkport.passman;

import net.darkport.passman.model.SecretMeta;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class SecretBuilder {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final String PBKDF_2_WITH_HMAC_SHA_512 = "PBKDF2WithHmacSHA512";
    private static final String AES = "AES";

    private static final int ITERATIONS_DEFAULT = 100_000;

    static final int KEY_LENGTH_BYTES = 32;
    static final int KEY_LENGTH_BITS = KEY_LENGTH_BYTES * 8;

    private String algorithm;
    private char[] password;
    private byte[] salt;
    private int iterations;
    private int keyLength;

    public SecretBuilder(char[] password) {
        this.algorithm = PBKDF_2_WITH_HMAC_SHA_512;
        this.iterations = ITERATIONS_DEFAULT;
        this.keyLength = KEY_LENGTH_BITS;
        this.password = password;
        this.salt = new byte[KEY_LENGTH_BYTES];
        SECURE_RANDOM.nextBytes(salt);
    }

    public SecretBuilder(char[] password, byte[] salt) {
        this.algorithm = PBKDF_2_WITH_HMAC_SHA_512;
        this.iterations = ITERATIONS_DEFAULT;
        this.keyLength = KEY_LENGTH_BITS;
        this.password = password;
        this.salt = salt;
    }

    public SecretBuilder(char[] password, SecretMeta meta) {
        this.algorithm = meta.getAlgorithm();
        this.iterations = meta.getIterations();
        this.keyLength = meta.getLength();
        this.password = password;
        this.salt = meta.getSalt();
    }

    public SecretBuilder withPassword(char[] password) {
        this.password = password;
        return this;
    }

    public SecretBuilder withSalt(byte[] salt) {
        this.salt = salt;
        return this;
    }

    public SecretBuilder withIterations(int iterations) {
        this.iterations = iterations;
        return this;
    }

    public SecretBuilder withKeyLength(int keyLength) {
        this.keyLength = keyLength;
        return this;
    }

    public SecretKey buildKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
        final PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterations, keyLength);
        final SecretKey key = keyFactory.generateSecret(keySpec);
        return new SecretKeySpec(key.getEncoded(), AES);
    }

    public SecretMeta buildMeta() {
        return new SecretMeta(algorithm, iterations, keyLength, salt);
    }
}
