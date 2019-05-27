package edu.ictt.blockchain.socket.body.lowerbody;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.socket.body.common.BaseBody;;

public class RpcBlockBody extends BaseBody{

	 /**
     * blockJson
     */
    private Block block;

    public RpcBlockBody() {
        super();
    }

    public RpcBlockBody(Block block) {
        super();
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    @Override
    public String toString() {
        return "BlockBody{" +
                "block=" + block +
                '}';
    }

}
