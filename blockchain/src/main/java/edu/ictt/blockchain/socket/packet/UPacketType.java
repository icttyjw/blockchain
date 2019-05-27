package edu.ictt.blockchain.socket.packet;

/**
 * packetType大于0时是请求类型，小于0时为响应类型
 */
public interface UPacketType {

	/**
     * 心跳包
     */
    byte HEART_BEAT = 00;
    /**
     * 已生成新的区块
     */
    byte Connect_Request = 18;
    byte GENERATE_COMPLETE_REQUEST=11;
    /**
     * 已生成新的区块回应
     */
    byte GENERATE_COMPLETE_RESPONSE = -11;
    /**
     * 请求生成block
     */
    byte GENERATE_BLOCK_REQUEST = 12;
    /**
     * 同意、拒绝生成
     */
    byte GENERATE_BLOCK_RESPONSE = -12;
    /**
     * 获取所有block信息
     */
    byte TOTAL_BLOCK_INFO_REQUEST = 13;
    /**
     * 我的所有块信息
     */
    byte TOTAL_BLOCK_INFO_RESPONSE = -13;
    /**
     * 获取一个block信息
     */
   byte FETCH_BLOCK_INFO_REQUEST = 14;
    /**
     * 获取一块信息响应
     */
    byte FETCH_BLOCK_INFO_RESPONSE = -14;
    /**
     * 获取下一个区块的信息
     */
    byte NEXT_BLOCK_INFO_REQUEST = 15;
    /**
     * 获取下一个区块的信息
     */
    byte NEXT_BLOCK_INFO_RESPONSE = -15;
    /*
     * 登录
     */
    byte LOGIN_REQUEST = 16;
    /*
     * 登录
     */
    byte LOGIN_REAPONSE = -16;
    /**
     * pbft投票
     */
    byte PBFT_VOTE = 20;
	byte RECEIVE_BLOCK = 17;
	byte U_RECEIVE_BLOCK = 19;
}
