package weaversj.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.CodeSource;
import java.security.spec.AlgorithmParameterSpec;
import org.apache.commons.codec.binary.Base64;

public class AES256Util {
    /**
     * 加密
     * @param str 需要加密的明文
     * @param key 加密(解密)的密钥
     * @param iv 盐值
     * @return
     * @throws Exception
     */
    public static String AES_Encode(String str, String key,String iv) throws Exception {
        byte[] textBytes = str.getBytes("UTF-8");
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
        SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
        return Base64.encodeBase64String(cipher.doFinal(textBytes));
    }
    /**
     * 解密
     * @param str 需要解密的密文
     * @param key 解密(加密)的密钥
     * @param iv 盐值
     * @return
     * @throws Exception
     */
    public static String AES_Decode(String str, String key,String iv) throws Exception {
//        CodeSource classpage = Base64.class.getProtectionDomain().getCodeSource();
//        String s = classpage.toString();
        byte[] textBytes = Base64.decodeBase64(str);
        //byte[] textBytes = str.getBytes("UTF-8");
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
        SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
        return new String(cipher.doFinal(textBytes), "UTF-8");
        }

}
