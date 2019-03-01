package edu.ictt.blockchain.Block.merkle;

import java.util.List;

/**
 * @author wuweifeng wrote on 2018/3/6.
 */
public class Test {
    public static void main(String[] args) {

        MerkleTree merkleTree = new MerkleTree();
        MerkleNode merkleNode0 = new MerkleNode(MerkleHash.create("a"));
        MerkleNode merkleNode1 = new MerkleNode(MerkleHash.create("b"));
        MerkleNode merkleNode2 = new MerkleNode(MerkleHash.create("c"));
        MerkleNode merkleNode3 = new MerkleNode(MerkleHash.create("d"));

        merkleTree.appendLeaf(merkleNode0);
        merkleTree.appendLeaf(merkleNode1);
        merkleTree.appendLeaf(merkleNode2);
        merkleTree.appendLeaf(merkleNode3);
        merkleTree.buildTree();
        System.out.println(merkleTree.getRoot().getHash());
        //merkle校验“a”是否在路径中
        List<MerkleProofHash> hashes = merkleTree.auditProof(MerkleHash.create("a"));
        System.out.println("a是否是叶子节点" + merkleNode0.isLeaf());
    }
}
