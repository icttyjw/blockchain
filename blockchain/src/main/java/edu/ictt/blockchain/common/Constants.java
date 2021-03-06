package edu.ictt.blockchain.common;

import java.math.BigInteger;

/**
 * ClassName:Constants <br/>
 * @author   wuweifeng
 */
public interface Constants {
	
	int PUBKEY_DIGEST_LENGTH = 90; // public key length
	int PRVKEY_DIGEST_LENGTH = 45; //private key length
	int ADDR_DIGEST_LENGTH = 35;   // address length
	int SIGN_DIGEST_LENGTH = 98;   // signature length
	int KEY_DES3_DIGEST_LENGTH = 24;  // max size of key for DES3 encrypt
	int KEY_AES128_DIGEST_LENGTH = 16; // max size of key for AES128 encrypt
	int TRANSSQL_DIGEST_LENGTH = 8192; // max size of trans sql for TrustSQL
	
	String RANDOM_NUMBER_ALGORITHM = "SHA1PRNG";
	String RANDOM_NUMBER_ALGORITHM_PROVIDER = "SUN";
	BigInteger MAXPRIVATEKEY = new BigInteger("00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364140", 16);

	String INFO_SHARE_PUBKEY = "BC8s/4qEAvVl4Sv0LwQOWJcVU6Q5hBd+7LlJeEivVmUbdtwP4RTfN8x/G+muMhN8SrweyyVVMIcIrnMWoFqGfIA=";

	/**
	 * 校内级别的创世块
	 */
	String GENESIS_BLOCK = "genesis_block";
	/**
	 * 校间级别的创世块
	 */
	String U_GENESIS_BLOCK = "u_genesis_block";
	/**
	 * 最后一个区块hash的key，value就是最后一个区块的hash
	 */
	String KEY_LAST_BLOCK = "key_last_block";
    /**
     * 第一个区块hash的key，value就是第一个区块的hash
     */
    String KEY_FIRST_BLOCK = "key_first_block";
	/**
	 * 区块hash与区块本身的key value映射，key的前缀，如{key_block_xxxxxxx -> blockJson}
	 */
	String KEY_BLOCK_HASH_PREFIX = "key_block_";

	String KEY_REQUEST_PREFIX = "key_request_";
    /**
     * 保存区块的hash和下一区块hash，key为hash，value为下一区块hash
     */
	String KEY_BLOCK_NEXT_PREFIX = "key_next_";
	/**
	 * 每个表的权限存储key
	 */
	String KEY_PERMISSION = "key_permission_";
	
	/**
	 * 最后一个区块hash的key，value就是最后一个区块的hash
	 */
	String U_KEY_LAST_BLOCK = "u_key_last_block";
    /**
     * 第一个区块hash的key，value就是第一个区块的hash
     */
    String U_KEY_FIRST_BLOCK = "u_key_first_block";
	/**
	 * 区块hash与区块本身的key value映射，key的前缀，如{key_block_xxxxxxx -> blockJson}
	 */
	String U_KEY_BLOCK_HASH_PREFIX = "u_key_block_";
	/**
	 *保存区块的bhash和uhash的映射
	 */
	String U_KEY_IN_BLOCK_PREFIX = "key_in_";

	String U_KEY_REQUEST_PREFIX = "u_key_request_";
    /**
     * 保存区块的hash和下一区块hash，key为hash，value为下一区块hash
     */
	String U_KEY_BLOCK_NEXT_PREFIX = "u_key_next_";
}
