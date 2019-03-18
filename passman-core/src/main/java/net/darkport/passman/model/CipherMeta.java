package net.darkport.passman.model;

public class CipherMeta {

    private String algorithm;
    private int blockLength;

    public CipherMeta() {
        // exists for serialization
    }

    public CipherMeta(String algorithm, int blockLength) {
        this.algorithm = algorithm;
        this.blockLength = blockLength;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public int getBlockLength() {
        return blockLength;
    }

    public void setBlockLength(int blockLength) {
        this.blockLength = blockLength;
    }
}
