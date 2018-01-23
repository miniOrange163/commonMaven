package priv.linjb.common.util.encrypt;


//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;生成密串会有换行
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
* AES对称加密
*/
public class AESUtils {
  static String IvParameter = "cfbsdfgsdfxccvd1";
  public static void test(){

      /**
       * 设备备传输
       */
      String sn = "STCAM-236-LS15032026";
      String mac="0C:F4:05:36:C7:EA";
      //String  minute =new SimpleDateFormat("yyyyMMddHHmm").format(new Date());//车载端时间，用于每分钟生成一次新的签名
      String  minute ="2017-07-28 11:22:33";
      String key = "signalway.com.cn";
      //传输内容
      String data = "{ \"sn\": \"STCAM-236-LS15032026\", \"mac\": \"0C:F4:05:36:C7:EA\", \"deviceTime\": \"2017-06-21 12:54:13\" }";
      //生成签名
      String signature = null;
      try {
          signature = AESUtils.encrypt(sn+mac+minute,key);
      } catch (Exception e) {
          e.printStackTrace();
      }
      //生成内容加密key
      String mixKey = "signalway.com.cn";//混淆值
      String DataEncryptKey = null;
      try {
          DataEncryptKey = AESUtils.encrypt(signature+mixKey,key);
      } catch (Exception e) {
          e.printStackTrace();
      }
      //加密内容
      String bodyEncrypt = null;
      try {
          bodyEncrypt = AESUtils.encrypt(data,DataEncryptKey);
      } catch (Exception e) {
          e.printStackTrace();
      }

      //打印加密传输内容
      System.out.println(bodyEncrypt);


      /**
       * 服务端接收
       */
      //接收到内容
      String clientSignature = signature;
      String clientBody = bodyEncrypt;

      //生成服务端签名
      String serverSn = "STCAM-236-LS15032026";
      String serveMac="0C:F4:05:36:C7:EA";
      //String  serveMinute =new SimpleDateFormat("yyyyMMddHHmm").format(new Date());//服务端时间，用于每分钟生成一次新的签名
      String  serveMinute = "2017-07-28 11:22:33";
      String serveKey = "signalway.com.cn";
      String serveSignature = null;

      try {
          serveSignature = AESUtils.encrypt(serverSn+serveMac+serveMinute,serveKey);
      } catch (Exception e) {
          e.printStackTrace();
      }
      //判断签名是否符合
      if(clientSignature == null || !clientSignature.equals(serveSignature)){
          System.out.println( "签名验证无法通过");
          return;
      }
      //生成内容加密key
      String serviceMixKey = "signalway.com.cn";//混淆值
      String serviceDataEncryptKey = null;
      try {
          serviceDataEncryptKey = AESUtils.encrypt(serveSignature+serviceMixKey,serveKey);
      } catch (Exception e) {
          e.printStackTrace();
      }
      //解密内容
      try {
          byte[] dataByte=new Base64().decode(clientBody);
          byte[] oo = decrypt1(dataByte, serviceDataEncryptKey);
          String p = new String(oo, "UTF-8");
          System.out.println(p);
      } catch (IOException e) {
          e.printStackTrace();
      }


      String body = null;
      try {
          body = AESUtils.decrypt(clientBody,serviceDataEncryptKey);
      } catch (Exception e) {
          e.printStackTrace();
      }
      //打印解密结果
      System.out.println(body);

  }
  /**
   * 加密
   *
   * @param content 需要加密的内容
   * @param password  加密密码
   * @return
   */
  public static byte[] encrypt1(String content, String password) {
      try {
          KeyGenerator kgen = KeyGenerator.getInstance("AES");
          kgen.init(256, new SecureRandom(password.getBytes()));
          SecretKey secretKey = kgen.generateKey();
          byte[] enCodeFormat = secretKey.getEncoded();
          SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");


          Cipher cipher = Cipher.getInstance("AES");// 创建密码器
          byte[] byteContent = content.getBytes("utf-8");
          cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
          byte[] result = cipher.doFinal(byteContent);
          return result; // 加密
      } catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
      } catch (NoSuchPaddingException e) {
          e.printStackTrace();
      } catch (InvalidKeyException e) {
          e.printStackTrace();
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      } catch (IllegalBlockSizeException e) {
          e.printStackTrace();
      } catch (BadPaddingException e) {
          e.printStackTrace();
      }
      return null;
  }
  /**解密
   * @param content  待解密内容
   * @param password 解密密钥
   * @return
   */
  public static byte[] decrypt1(byte[] content, String password) {
      try {
          KeyGenerator kgen = KeyGenerator.getInstance("AES");
          kgen.init(128, new SecureRandom(password.getBytes()));
          SecretKey secretKey = kgen.generateKey();
          byte[] enCodeFormat = secretKey.getEncoded();
          SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");


          Cipher cipher = Cipher.getInstance("AES");// 创建密码器
          cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
          byte[] result = cipher.doFinal(content);
          return result; // 加密
      } catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
      } catch (NoSuchPaddingException e) {
          e.printStackTrace();
      } catch (InvalidKeyException e) {
          e.printStackTrace();
      } catch (IllegalBlockSizeException e) {
          e.printStackTrace();
      } catch (BadPaddingException e) {
          e.printStackTrace();
      }
      return null;
  }
  private AESUtils() {
      throw new UnsupportedOperationException("cannot be instantiated");
  }

  /**
   * AES 加密
   * @return
   * @throws NoSuchAlgorithmException
   */
  public static byte[] initKey() throws NoSuchAlgorithmException {
      KeyGenerator keyGen = KeyGenerator.getInstance("AES");
      keyGen.init(256);  //192 256
      SecretKey secretKey = keyGen.generateKey();
      return secretKey.getEncoded();
  }
  public static byte[] initKey(String password) throws NoSuchAlgorithmException {
      KeyGenerator keyGen = KeyGenerator.getInstance("AES");
      SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
      secureRandom.setSeed(password.getBytes());
      keyGen.init(128,secureRandom);  //192 256
      SecretKey secretKey = keyGen.generateKey();
      return secretKey.getEncoded();
  }
  public static byte[] initKey2(String password) throws NoSuchAlgorithmException {
      KeyGenerator keyGen = KeyGenerator.getInstance("AES");
      SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
      secureRandom.setSeed(password.getBytes());
      keyGen.init(128,secureRandom);  //192 256
      SecretKey secretKey = keyGen.generateKey();
      return secretKey.getEncoded();
  }
  /**
   * AES 加密
   * @return
   * @throws NoSuchAlgorithmException
   */
  public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
      SecretKey secretKey = new SecretKeySpec(key, "AES");

      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
      byte[] cipherBytes = cipher.doFinal(data);
      return cipherBytes;
  }
  /**
   * AES 加密
   * @return
   * @throws NoSuchAlgorithmException
   */
  public static byte[] encrypt(byte[] data, String key) throws Exception {
      byte[] keyByte= initKey(key);
      return  encrypt(data,keyByte);
  }
  /**
   * AES 加密
   * @return
   * @throws NoSuchAlgorithmException
   */
  public static String encrypt(String data, String key) throws Exception {
      byte[] keyByte= initKey(key);
      byte[] dataByte=data.getBytes("UTF-8");
      return  new Base64().encodeAsString(encrypt(dataByte,keyByte));
  }

  /**
   * AES 解密
   * @param data
   * @param key
   * @return
   * @throws Exception
   */
  public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
//  KeyGenerator kgen = KeyGenerator.getInstance("AES");
//      kgen.init(128, new SecureRandom(key));
//      SecretKey secretKey = kgen.generateKey();
//      byte[] enCodeFormat = secretKey.getEncoded();
//      SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
      SecretKey secretKey = new SecretKeySpec(key, "AES");

      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);

      byte[] plainBytes = cipher.doFinal(data);




/**
      KeyGenerator kgen = KeyGenerator.getInstance("AES");
      kgen.init(128, new SecureRandom(password.getBytes()));
      SecretKey secretKey = kgen.generateKey();
      byte[] enCodeFormat = secretKey.getEncoded();
      SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");


      Cipher cipher = Cipher.getInstance("AES");// 创建密码器
      cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
      byte[] result = cipher.doFinal(content);
**/

      return plainBytes;
  }
  /**
   * AES 解密
   * @param data
   * @param key
   * @return
   * @throws Exception
   */
  public static byte[] decrypt(byte[] data, String key) throws Exception {
      byte[] keyByte=initKey(key);
      return decrypt(keyByte, data);
  }
  /**
   * AES 解密
   * @param data
   * @param key
   * @return
   * @throws Exception
   */
  public static String decrypt(String data, String key) throws Exception {
      byte[] keyByte= initKey(key);
      byte[] dataByte=new Base64().decode(data);
      byte[] decodeByte=decrypt(dataByte,keyByte);
      return new String(decodeByte, "UTF-8");
  }

  // 加密
  public static String encryptCBC(String sSrc, String sKey) throws Exception {
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      byte[] raw = new byte[16];
      if(sKey.length() <16) {
          raw = (sKey+IvParameter.substring(sKey.length())).getBytes();
      } else {
          sKey = sKey.substring(0,16);
          raw = sKey.getBytes();
      }
      SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
      IvParameterSpec iv = new IvParameterSpec(IvParameter.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
      cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
      byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
      return new Base64().encodeAsString(encrypted);//此处使用BASE64做转码。
  }

  // 解密
  public static String decryptCBC(String sSrc, String sKey) throws Exception {
      try {
          byte[] raw = new byte[16];
          if(sKey.length() <16) {
              raw = (sKey+IvParameter.substring(sKey.length())).getBytes();
          } else {
              sKey = sKey.substring(0,16);
              raw = sKey.getBytes("ASCII");
          }
          SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
          Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
          IvParameterSpec iv = new IvParameterSpec(IvParameter.getBytes());
          cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
          byte[] encrypted1 = new Base64().decode(sSrc);//先用base64解密
          byte[] original = cipher.doFinal(encrypted1);
          String originalString = new String(original,"utf-8");
          return originalString;
      } catch (Exception ex) {
          return null;
      }
  }
  // 解密
  public static String decryptCBC(byte[] decrypt, String sKey) throws Exception {
      try {
          byte[] raw = new byte[16];
          if(sKey.length() <16) {
              raw = (sKey+IvParameter.substring(sKey.length())).getBytes();
          } else {
              sKey = sKey.substring(0,16);
              raw = sKey.getBytes("ASCII");
          }
          SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
          Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
          IvParameterSpec iv = new IvParameterSpec(IvParameter.getBytes());
          cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
          byte[] original = cipher.doFinal(decrypt);
          String originalString = new String(original,"utf-8");
          return originalString;
      } catch (Exception ex) {
          return null;
      }
  }
}

