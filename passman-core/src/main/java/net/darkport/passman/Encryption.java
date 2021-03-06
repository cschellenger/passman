package net.darkport.passman;

import net.darkport.passman.model.CipherMeta;
import net.darkport.passman.model.EncryptionData;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;

public class Encryption {

    private static final String AES_GCM = "AES/GCM/NoPadding";
    private static final int BLOCK_BYTES = 16;

    private String algorithm;
    private int blockSize;

    private SecretBuilder secretBuilder;

    public Encryption() {
        this.algorithm = AES_GCM;
        this.blockSize = BLOCK_BYTES;
    }

    public Encryption withPassword(char[] password) {
        this.secretBuilder = new SecretBuilder(password);
        return this;
    }

    public Encryption withSecretBuilder(SecretBuilder secretBuilder) {
        this.secretBuilder = secretBuilder;
        return this;
    }

    public EncryptionData encrypt(byte[] plainText) throws NoSuchAlgorithmException,
                                                           NoSuchPaddingException,
                                                           InvalidKeySpecException,
                                                           InvalidKeyException,
                                                           InvalidAlgorithmParameterException,
                                                           IllegalBlockSizeException,
                                                           BadPaddingException {
        return encrypt(plainText, null);
    }

    public EncryptionData encrypt(byte[] plainText, byte[] aad)
            throws NoSuchAlgorithmException,
                   NoSuchPaddingException,
                   InvalidKeySpecException,
                   InvalidKeyException,
                   InvalidAlgorithmParameterException,
                   IllegalBlockSizeException,
                   BadPaddingException {
        Cipher cipher = Cipher.getInstance(AES_GCM);
        SecureRandom secureRandom = new SecureRandom();

        byte[] iv = new byte[blockSize];
        secureRandom.nextBytes(iv);

        AlgorithmParameterSpec spec = new GCMParameterSpec(blockSize * 8, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretBuilder.buildKey(), spec);

        if (aad != null) {
            cipher.updateAAD(aad);
        }

        byte[] encrypted = cipher.doFinal(plainText);

        EncryptionData ed = new EncryptionData();
        ed.setSecretMeta(secretBuilder.buildMeta());
        ed.setAad(aad);
        ed.setCipherMeta(buildMeta());
        ed.setData(encrypted);
        ed.setIv(iv);

        return ed;
    }

    public EncryptionData encryptUnchecked(byte[] plainText, byte[] aad) {
        try {
            return encrypt(plainText, aad);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException | InvalidKeyException
                | InvalidAlgorithmParameterException | IllegalBlockSizeException |BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public EncryptionData encryptUnchecked(byte[] plainText) {
        return encryptUnchecked(plainText, null);
    }

    CipherMeta buildMeta() {
        return new CipherMeta(algorithm, blockSize);
    }
}
