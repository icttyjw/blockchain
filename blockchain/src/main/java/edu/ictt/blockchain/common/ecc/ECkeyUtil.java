package edu.ictt.blockchain.common.ecc;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.Base64;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import edu.ictt.blockchain.common.util.Tobyte;
import sun.security.ec.ECPrivateKeyImpl;
import sun.security.ec.ECPublicKeyImpl;

/**  
 * 签名和验证函数
 */  
public class ECkeyUtil {

	
	
	/**  
     * 对byte[]数据hash后签名  
     * @param privateKey 私钥
     * @param input  明文
     * @return  密文
     */  
	public static String signDigest(ECPrivateKeyImpl privateKey,byte[] input){
		 byte[] finalResult=new byte[0];
		Signature e;
		try {
			e = Signature.getInstance("SHA1withECDSA","SunEC");
			e.initSign(privateKey);
			e.update(input);
			finalResult=e.sign();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchProviderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidKeyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SignatureException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    String sign=Base64.getEncoder().encodeToString(finalResult);
	   
		return sign;
	}
	
	/**  
     * 对明文和签名进行验证  
     * @param publicKey 公钥
     * @param input  明文
     * @param sign  密文
     * @return  校验结果
     */  
	public static boolean verify(byte[] input,String sign,ECPublicKeyImpl publicKey){
		Signature e;
		byte[] bytesign=Base64.getDecoder().decode(sign);
		boolean state=false;
		try {
			e=Signature.getInstance("SHA1withECDSA","SunEC");
			e.initVerify(publicKey);
			e.update(input);
			state=e.verify(bytesign);
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchProviderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidKeyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SignatureException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		return state;
	}
}
