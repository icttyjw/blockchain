package edu.ictt.blockchain.Block.merkle;

/**
 * Merkle校验路径上的节点
 */
public class MerkleProofHash {
    public enum Branch {
        LEFT,
        RIGHT,
    }

    public MerkleHash hash;
    public Branch direction;

    public MerkleProofHash(MerkleHash hash, Branch direction) {
        this.hash = hash;
        this.direction = direction;
    }

    public MerkleHash getHash() {
        return hash;
    }

    public Branch getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        String hash = this.hash.toString();
        String direction = this.direction.toString();
        return hash.concat("  is ".concat(direction).concat(" Child"));
    }
}
