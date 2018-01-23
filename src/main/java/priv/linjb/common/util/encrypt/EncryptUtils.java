package priv.linjb.common.util.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtils {
	
	public static final String MD5 = "MD5";
	public static final String SHA_1 = "SHA-1";
	public static final String SHA_256 = "SHA-256";
	
	/**
	 * 
	 * MD5算法加密
	 * 
	 * @param strSrc
	 * @return
	 */
	public static String md5(String strSrc){
		return encrypt(strSrc,MD5);
	}
	
	 /**
     * 对字符串加密,加密算法使用MD5,SHA-1,SHA-256,默认使用MD5
     * 
     * @param strSrc 要加密的字符串
     * @param encName 加密类型
     * @return
     */
    public static String encrypt(String strSrc, String encName) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try {
            if (encName == null || encName.equals("")) {
                encName = MD5;
            }
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
}
