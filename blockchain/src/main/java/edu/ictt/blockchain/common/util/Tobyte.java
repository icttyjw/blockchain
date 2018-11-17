package edu.ictt.blockchain.common.util;

public class Tobyte {

	public static byte[] hexStringToByteArray(String hexString){
        hexString=hexString.toLowerCase();
        byte [] result=new byte[hexString.length()/2];
        for (int i=0;i<hexString.length();i+=2){
            result[i/2]= (byte) (((Character.digit(hexString.charAt(i),16))<<4)+Character.digit(hexString.charAt(i+1),16));
        }
        return result;

    }
}
