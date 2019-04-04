package edu.ictt.blockchain.Block.merkle;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class MerkleTree {

    private MerkleNode root;
    private List<MerkleNode> nodes;
    private List<MerkleNode> leaves;

    public MerkleTree() {
        this.nodes = new ArrayList<>();
        this.leaves = new ArrayList<>();
    }
    

    /**
     * 获取叶子节点列表
     * @return
     */
    public List<MerkleNode> getLeaves() {
        return leaves;
    }

    /**
     * 获取节点列表
     * @return
     */
    public List<MerkleNode> getNodes() {
        return nodes;
    }

    /**
     * 获得根节点
     * @return
     */
    public MerkleNode getRoot() {
        return root;
    }

    /**
     * 增加单个叶子节点
     * @param node
     * @return
     */
    public MerkleNode appendLeaf(MerkleNode node) {
        this.nodes.add(node);
        this.leaves.add(node);
        return node;
    }

    /**
     * 通过节点数组增加叶子节点
     * @param nodes
     */
    public void appendLeaves(MerkleNode[] nodes) {
        for (MerkleNode node : nodes) {
            this.appendLeaf(node);
        }
    }

    /**
     * 通过Hash值增加叶子节点
     * @param hash
     * @return
     */
    public MerkleNode appendLeaf(MerkleHash hash) {
        return this.appendLeaf(new MerkleNode(hash));
    }

    /**
     * 通过Hash数组增加叶子节点
     * @param hashes
     * @return
     */
    public List<MerkleNode> appendLeaves(MerkleHash[] hashes) {
        List<MerkleNode> nodes = new ArrayList<>();
        for (MerkleHash hash : hashes) {
            nodes.add(this.appendLeaf(hash));
        }
        return nodes;
    }

    /**
     *将树中节点追加为叶子节点
     * @param tree
     * @return
     */
    public MerkleHash addTree(MerkleTree tree) {
        if (this.leaves.size() <= 0) throw new InvalidParameterException("Cannot add to a tree with no leaves!");
        tree.leaves.forEach(this::appendLeaf);
        return this.buildTree();
    }

    /**
     * 使用叶子节点建树
     * @return
     */
    public MerkleHash buildTree() {
        if (this.leaves.size() <= 0) throw new InvalidParameterException("Cannot add to a tree with no leaves!");
        this.buildTree(this.leaves);
        return this.root.getHash();
    }

    /**
     * 使用叶子节点数组建树
     * @param nodes
     */
    public void buildTree(List<MerkleNode> nodes) {
        if (nodes.size() <= 0) throw new InvalidParameterException("Node list not expected to be empty!");

        if (nodes.size() == 1) {
            this.root = nodes.get(0);
        } else {
            List<MerkleNode> parents = new ArrayList<>();
            for (int i = 0; i < nodes.size(); i += 2) {
                MerkleNode right = (i + 1 < nodes.size()) ? nodes.get(i + 1) : null;
                MerkleNode parent = new MerkleNode(nodes.get(i), right);
                parents.add(parent);
            }
            buildTree(parents);
        }
    }

    /**
     * 检验某个特定哈希值的叶子节点是否在Merkle树中，并给出校验路径
     * @param leafHash
     * @return
     */
    public List<MerkleProofHash> auditProof(MerkleHash leafHash) {
        List<MerkleProofHash> auditTrail = new ArrayList<>();

        MerkleNode leafNode = this.findLeaf(leafHash);

        if (leafNode != null) {
            if (leafNode.getParent() == null) throw new InvalidParameterException("Expected leaf to have a parent!");
            MerkleNode parent = leafNode.getParent();
            this.buildAuditTrail(auditTrail, parent, leafNode);
        }

        return auditTrail;
    }

    /**
     *给定根节点和被校验的叶子节点及校验路径，根据校验路径验证
     * @param rootHash
     * @param leafHash
     * @param auditTrail
     * @return
     */
    public static boolean verifyAudit(MerkleHash rootHash, MerkleHash leafHash, List<MerkleProofHash> auditTrail) {
        if (auditTrail.size() <= 0) throw new InvalidParameterException("Audit trail cannot be empty!");

        MerkleHash testHash = leafHash;

        for (MerkleProofHash auditHash : auditTrail) {
            testHash = auditHash.direction == MerkleProofHash.Branch.RIGHT
                    ? MerkleHash.create(testHash, auditHash.hash)
                    : MerkleHash.create(auditHash.hash, testHash);
        }

        return testHash.equals(rootHash);
    }

    /**
     * 找给定哈希值的叶子节点
     * @param hash
     * @return
     */
    private MerkleNode findLeaf(MerkleHash hash) {
        return this.leaves.stream()
                .filter((leaf) -> leaf.getHash().equals(hash))
                .findFirst()
                .orElse(null);
    }

    /**
     *构建校验路径
     * @param auditTrail
     * @param parent
     * @param child
     */
    private void buildAuditTrail(List<MerkleProofHash> auditTrail, MerkleNode parent, MerkleNode child) {
        if (parent != null) {
            if (child.getParent() != parent) {
                throw new InvalidParameterException("Parent of child is not expected parent!");
            }

            //校验左节点，则路径需要右节点；校验右节点，则路径需要左节点
            MerkleNode nextChild = parent.getLeftNode() == child ? parent.getRightNode() : parent.getLeftNode();
            //自底向上寻找，如果是左节点，则向右分支找；否则则向左分支找
            MerkleProofHash.Branch direction = parent.getLeftNode() == child
                    ? MerkleProofHash.Branch.RIGHT
                    : MerkleProofHash.Branch.LEFT;

            //每找到一个节点，就将其加入到校验路径
            if (nextChild != null) auditTrail.add(new MerkleProofHash(nextChild.getHash(), direction));

            //递归寻找直至找到完整的校验路径
            this.buildAuditTrail(auditTrail, parent.getParent(), child.getParent());
        }
    }
}
