package net.darkport.passman;

import net.darkport.passman.model.EncryptionData;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;

public class Decryption {

    private EncryptionData encryptionData;

    public Decryption(EncryptionData encryptionData) {
        this.encryptionData = encryptionData;
    }

    public byte[] decrypt(char[] password) throws NoSuchAlgorithmException,
                                                  NoSuchPaddingException,
                                                  InvalidKeySpecException,
                                                  InvalidKeyException,
                                                  InvalidAlgorithmParameterException,
                                                  IllegalBlockSizeException,
                                                  BadPaddingException {
        SecretBuilder secretBuilder = new SecretBuilder(password, encryptionData.getSecretMeta());
        Cipher cipher = Cipher.getInstance(encryptionData.getCipherMeta().getAlgorithm());
        AlgorithmParameterSpec spec = new GCMParameterSpec(encryptionData.getCipherMeta().getBlockLength() * 8, encryptionData.getIv());
        cipher.init(Cipher.DECRYPT_MODE, secretBuilder.buildKey(), spec);
        cipher.updateAAD(encryptionData.getAad());
        return cipher.doFinal(encryptionData.getData());
    }

    public byte[] decryptUnchecked(char[] password) {
        try {
            return decrypt(password);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException | InvalidKeyException
                | InvalidAlgorithmParameterException | IllegalBlockSizeException |BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
}
