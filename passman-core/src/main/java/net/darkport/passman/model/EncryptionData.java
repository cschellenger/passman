package net.darkport.passman.model;

import net.darkport.passman.model.CipherMeta;
import net.darkport.passman.model.SecretMeta;

public class EncryptionData {

    private byte[] data;
    private byte[] iv;
    private byte[] aad;

    private CipherMeta cipherMeta;
    private SecretMeta secretMeta;

    public EncryptionData() {
        // exists for serialization
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    public byte[] getAad() {
        return aad;
    }

    public void setAad(byte[] aad) {
        this.aad = aad;
    }

    public CipherMeta getCipherMeta() {
        return cipherMeta;
    }

    public void setCipherMeta(CipherMeta cipherMeta) {
        this.cipherMeta = cipherMeta;
    }

    public SecretMeta getSecretMeta() {
        return secretMeta;
    }

    public void setSecretMeta(SecretMeta secretMeta) {
        this.secretMeta = secretMeta;
    }
}
