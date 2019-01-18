package edu.ictt.blockchain.common.ecc;

import static edu.ictt.blockchain.common.DataInfo.ECNAME;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;

import sun.security.ec.ECPrivateKeyImpl;
import sun.security.ec.ECPublicKeyImpl;

/**  
 * 这方法应该是不用了
 * 1.8以后sun.security.ec里的两个接口名可以使用但是构造方法不能用
 * 我修改的sunec里的构造方法能用但是初始化时java.security不接受我的方法名
 * 所以两者需要分开，这里是生成了共识钥，返回的是他们的参数，可以直接使用也可以重新生成一下
 */  
public class ECkeygen {

	/**  
     * 生成BigIntreger格式的公私钥  
     * @return  公私钥的数组[0]私钥参数[1]公钥X参数[2]公钥Y参数
     */  
	public ArrayList<BigInteger> keygen(){
		ArrayList<BigInteger> result=new ArrayList<BigInteger>();
		KeyPairGenerator kpg = null;
        try {
			kpg=KeyPairGenerator.getInstance("EC","SunEC");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ECGenParameterSpec ecsp;
        ecsp=new ECGenParameterSpec(ECNAME);
        try {
			kpg.initialize(ecsp);
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        KeyPair keyPair=kpg.generateKeyPair();
        ECPrivateKeyImpl priKey= (ECPrivateKeyImpl) keyPair.getPrivate();
        ECPublicKeyImpl pubKey= (ECPublicKeyImpl) keyPair.getPublic();
        result.add(priKey.getS());
        result.add(pubKey.getW().getAffineX());
        result.add(pubKey.getW().getAffineY());
		return result;
	}
}
