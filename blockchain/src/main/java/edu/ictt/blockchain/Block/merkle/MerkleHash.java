package edu.ictt.blockchain.Block.merkle;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class MerkleHash {

    /**
     * Hash value as byte array.
     */
    private byte[] value;

    public MerkleHash() {
    }

    /**
     * 用给定字节数组设置Merkle Hash
     *
     * @param buffer of bytes
     * @return a MerkleHash
     */
    public static MerkleHash create(byte[] buffer) {
        MerkleHash hash = new MerkleHash();
        hash.computeHash(buffer);
        return hash;
    }

    /**
     * Create a MerkleHash from a string. The string needs
     * first to be transformed in a UTF8 sequence of bytes.
     * Used for leaf hashes.
     *
     * @param buffer string
     * @return a MerkleHash
     */
    public static MerkleHash create(String buffer) {
        return create(buffer.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 通过给定的左右子节点，构造其父节点的hash值
     *
     * @param left  subtree hash
     * @param right subtree hash
     * @return a MerkleHash
     */
    public static MerkleHash create(MerkleHash left, MerkleHash right) {
        return create(concatenate(left.getValue(), right.getValue()));
    }

    /**
     * 获得MerkleHash值.
     *
     * @return an array of bytes
     */
    public byte[] getValue() {
        return value;
    }

    /**
     *与给定的hash值数组比较
     *
     * @param hash as byte array
     * @return boolean
     */
    public boolean equals(byte[] hash) {
        return Arrays.equals(this.value, hash);
    }

    /**
     * 与给定的MerkleHash进行比较
     *
     * @param hash as MerkleHash
     * @return boolean
     */
    public boolean equals(MerkleHash hash) {
        boolean result = false;
        if (hash != null) {
            result = Arrays.equals(this.value, hash.getValue());
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }

    /**
     * Encode in Base64 the MerkleHash.
     *
     * @return the string encoding of MerkleHash.
     */
    @Override
    public String toString() {
        return Base64.getEncoder().encodeToString(this.value);
    }

    /**
     * 计算自己数组的Hash值
     *
     * @param buffer of bytes
     */
    private void computeHash(byte[] buffer) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            this.value = digest.digest(buffer);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**连接两个字节数组
     *
     * @param a is the first array
     * @param b is the second array
     * @return a byte array
     */
    public static byte[] concatenate(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }
}
