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
