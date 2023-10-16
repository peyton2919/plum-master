package cn.peyton.plum.core.cipher.child;

import cn.peyton.plum.core.cipher.enums.MacCipher;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

/**
 * <h3>单向密码, 加密解密工具</h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @create date: 2018/11/16 14:44
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
public class UnidirectionalCipherUtils {

	private static final String KEY_MD5 = "MD5";
	private static final String KEY_SHA = "SHA";
	
	/**
	 * <h4>HMAC单向加密{散列消息鉴别码}</h4>
	 * @param content 内容
	 * @param key 字符串
	 * @return 加密后的内容
	 */
	public static String HMAC(String content, String key) {
		if (null == content || "".equals(content)) {
			return content;
		}
		return HMAC(content, key, MacCipher.HmacSHA256);
	}
	/**
	 * <h4>HMAC单向加密{散列消息鉴别码}</h4>
	 * @param content  内容
	 * @param key 字符串
	 * @param macCipher MAC算法可选以下多种算法
	 *  <pre>
	 * HmacMD5  
	 * HmacSHA1  
	 * HmacSHA256  
	 * HmacSHA384  
	 * HmacSHA512
	 * </pre>
	 * @return 加密后的内容
	 */
	public static String HMAC(String content, String key , MacCipher macCipher) {
		if (null == content || "".equals(content)) {
			return content;
		}
		try {
			byte[] keys = key.getBytes(ICipher.Code.CHARACTER);
			// 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
			SecretKey secretKey = new SecretKeySpec(keys, macCipher.getValue());
			// 生成一个指定 Mac 算法 的 Mac 对象
			Mac mac = Mac.getInstance(macCipher.getValue());
			// 用给定密钥初始化 Mac 对象
			mac.init(secretKey);
			byte[] text = content.getBytes(ICipher.Code.CHARACTER);
			// 完成 Mac 操作
			return Base64.getEncoder().encodeToString(mac.doFinal(text));
		} catch (Exception e) {		}
		return null;
	}

	/**
	 * <h4>SHA加密</h4>
	 * @param content  内容
	 * @return 加密后的内容
	 */
	public static String SHA(String content) {
		if (null == content || "".equals(content)) {
			return content;
		}
		try {
			MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
			sha.update(content.getBytes(ICipher.Code.CHARACTER));
			return Base64.getEncoder().encodeToString(sha.digest());
		} catch (Exception e) {		}
		return null;
	}
	
	public static String MD5(String content, String key) {
		if (null == content || "".equals(content)) {
			return content;
		}
		String md5 = MD5(content);
		return MD5(md5 + key);
	}

	public static String MD5(String content){	
		if (null == content || "".equals(content)) {
			return content;
		}
		try {
			MessageDigest s = MessageDigest.getInstance(KEY_MD5);
			s.update(content.getBytes());
			byte[] bytes = s.digest();
			return StringBytes.toHexString(bytes);
		} catch (NoSuchAlgorithmException e) { }
		return null;
	}

	public static String createMd5Key() {
		// 生成密码和key
		Random r = new Random();
		String key = String.valueOf(r.nextInt(10000000));
		while (key.length() < 8) {
			key = "0" + key;
		}
		return key;
	}

}
