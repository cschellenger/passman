package net.darkport.passman.model;

public class SecretMeta {

    private String algorithm;
    private int iterations;
    private int length;
    private byte[] salt;

    public SecretMeta() {
        // exists for serialization
    }

    public SecretMeta(String algorithm, int iterations, int length, byte[] salt) {
        this.algorithm = algorithm;
        this.iterations = iterations;
        this.length = length;
        this.salt = salt;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
