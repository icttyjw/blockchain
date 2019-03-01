package edu.ictt.blockchain.Block.merkle;

import java.security.InvalidParameterException;
import java.util.Objects;

public class MerkleNode {
    private MerkleHash hash;
    private MerkleNode leftNode;
    private MerkleNode rightNode;
    private MerkleNode parent;


    public MerkleNode() {
    }

    /**
     * 用给定哈希值构造Merkle Node
     * @param hash
     */
    public MerkleNode(MerkleHash hash) {
        this.hash = hash;
    }

    /**
     * 用给定左右子节点构造Merkle Node
     * @param left
     * @param right
     */
    public MerkleNode(MerkleNode left, MerkleNode right) {

        this.leftNode = left;
        this.rightNode = right;

        this.leftNode.parent = this;
        if (this.rightNode != null) this.rightNode.parent = this;

        this.computeHash();
    }

    /**
     * 判断某节点是否是叶子节点
     * @return
     */
    public boolean isLeaf() {
        return this.leftNode == null && this.rightNode == null;
    }

    @Override
    public String toString() {
        return hash.toString();
    }

    /**
     * 将左节点设置给定节点
     * @param node
     */
    public void setLeftNode(MerkleNode node) {
        if (node.hash == null) {
            throw new InvalidParameterException("Node hash must be initialized!");
        }

        this.leftNode = node;
        this.leftNode.parent = this;

        this.computeHash();
    }

    /**
     * 将右节点设置给定节点
     * @param node
     */
    public void setRightNode(MerkleNode node) {
        if (node.hash == null) {
            throw new InvalidParameterException("Node hash must be initialized!");
        }

        this.rightNode = node;
        this.rightNode.parent = this;

        if (this.leftNode != null) {
           this.computeHash();
        }
    }

    /**
     * 哈希值能否被验证
     * @return
     */
    public boolean canVerifyHash() {
        return (this.leftNode != null && this.rightNode != null) || (this.leftNode != null);
    }

    /**
     * 验证哈希值是否正确
     * @return
     */
    public boolean verifyHash() {
        if (this.leftNode == null && this.rightNode == null) return true;
        if (this.rightNode == null) return hash.equals(leftNode.hash);

        if (this.leftNode == null) {
            throw new InvalidParameterException("Left branch must be a node if right branch is a node!");
        }

        MerkleHash leftRightHash = MerkleHash.create(this.leftNode.hash, this.rightNode.hash);
        return hash.equals(leftRightHash);
    }

    /**
     * 判断是否有其他节点的哈希值与自己相同
     * @param other
     * @return
     */
    public boolean equals(MerkleNode other) {
        return this.hash.equals(other.hash);
    }

    public MerkleHash getHash() {
        return hash;
    }

    public MerkleNode getParent() {
        return parent;
    }

    public MerkleNode getLeftNode() {
        return leftNode;
    }

    public MerkleNode getRightNode() {
        return rightNode;
    }

    /**
     * 计算哈希值
     */
    public void computeHash() {

        if (this.rightNode == null) {
            this.hash = this.leftNode.hash;
        } else {
            this.hash = MerkleHash.create(MerkleHash.concatenate(
                    this.leftNode.hash.getValue(), this.rightNode.hash.getValue()));
        }

        if (this.parent != null) {
            this.parent.computeHash();
        }
    }

    @Override
    public int hashCode() {

        return Objects.hash(hash, leftNode, rightNode, parent);
    }
}
