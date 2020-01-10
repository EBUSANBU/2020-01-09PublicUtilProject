package weaversj.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class HashUtil extends WeaverSJUtil{

	
	/**
	 * hash 类型
	 * @author zhangyanzhao
	 *
	 */
	public enum hashEnum{
		MD5("MD5")
		,SHA1("SHA-1")
		,SHA256("SHA-256")
		;
		hashEnum(String hashCode){
			this.hashCode=hashCode;
		}
		public String hashCode;
	}
	
	/**
	 * 获取编码
	 * @param hashCode
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String getHashNumber(String hashCode,String str) throws NoSuchAlgorithmException{
		MessageDigest messDig = MessageDigest.getInstance(hashCode);
		messDig.update(str.getBytes());
		return new BigInteger(1,messDig.digest()).toString(16);
	}
	
	/**
	 * 
	 * @param str
	 * @param e
	 * @return
	 */
	public static String getHashNumber(String str,hashEnum he) {
		return getHashNumber(str, he, "");
	}
	
	/**
	 * 
	 * @param str		字符串
	 * @param he		枚举
	 * @param charset   编码类型 ：Utf-8
	 * @return
	 */
	public static String getHashNumber(String str,hashEnum he,String charset) {
		try {
			MessageDigest messDig = MessageDigest.getInstance(he.hashCode);
			try {
				messDig.update(str.getBytes(charset));
			} catch (UnsupportedEncodingException u) {
				messDig.update(str.getBytes());
			}
			return new BigInteger(1,messDig.digest()).toString(16);
		} catch (Exception e) {
			throw new PublicReturnExceptionUtil(e);
		}
	}
}
