package edu.ictt.blockchain.Block.block;

/**
 * @Author:zoe
 * @Description:
 * @Date:
 * @Modified By:
 */
public class Test {

    public static void main(String[] args){
        BlockHeader testBlockHeader = new BlockHeader();
        BlockBody testBlockBody = new BlockBody();
        Block testBlock = new Block(testBlockHeader,testBlockBody);

        System.out.println(testBlock.toString());



    }



}
