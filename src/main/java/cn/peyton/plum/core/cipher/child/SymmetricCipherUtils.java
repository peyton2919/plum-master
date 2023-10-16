package cn.peyton.plum.core.cipher.child;

import cn.peyton.plum.core.cipher.enums.SymmetricCipher;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * <h3>相称性的密码,加密解密工具</h3>
 * <pre>
 * 如果密钥大于128,
 * 会抛出java.security.InvalidKeyException: Illegal key size 异常.
 * 因为密钥长度是受限制的, java运行时环境读到的是受限的policy文件.
 * 文件位于${java_home}/jre/lib/security, 这种限制是因为美国对软件出口的控制.
 * 处理办法: 在官方网站下载JCE无限制权限策略文件
 * </pre>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @create date: 2018/11/16 14:43
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
public class SymmetricCipherUtils {
	/**
	 * <h4>相称性的密码 加密方式</h4>
	 * @param content 要加密内容
	 * @param key 字符串
	 * @param symmetricCipher 加密方式{有5个可以选择枚举里的任何一种}
	 * @return 加密后的字符串
	 */
	public static String  encoder(String content , String key, SymmetricCipher symmetricCipher) {
		switch (symmetricCipher) {
		case DES:
			return _encoder(content, key, KEY_SIZE_DES, symmetricCipher.getValue());
		case DESEDE:
			return _encoder(content, key, KEY_SIZE_DESEDE, symmetricCipher.getValue());
		case AES:
			return _encoder(content, key, KEY_SIZE_AES, symmetricCipher.getValue());
		case BLOWFISH:
			return _encoder(content, key, KEY_SIZE_BLOWFISH, symmetricCipher.getValue());
		case RC2:
			return _encoder(content, key, KEY_SIZE_RC2, symmetricCipher.getValue());
		default:
			return null;
		}
		
	}

	/**
	 * <h4>相称性的密码 解密方式</h4>
	 * @param content 要解密内容
	 * @param key 字符串
	 * @param symmetricCipher cipherUtils 解密方式{在枚举里选择加密相同的一种}
	 * @return 解密后的字符串
	 */
	public static String decoder(String content , String key , SymmetricCipher symmetricCipher) {
		switch (symmetricCipher) {
		case DES:
			return _decoder(content, key, KEY_SIZE_DES, symmetricCipher.getValue());
		case DESEDE:
			return _decoder(content, key, KEY_SIZE_DESEDE, symmetricCipher.getValue());
		case AES:
			return _decoder(content, key, KEY_SIZE_AES, symmetricCipher.getValue());
		case BLOWFISH:
			return _decoder(content, key, KEY_SIZE_BLOWFISH, symmetricCipher.getValue());
		case RC2:
			return _decoder(content, key, KEY_SIZE_RC2, symmetricCipher.getValue());
		default:
			return null;
		}
	}

	/**
	 * <h4>BASE64解密</h4>
	 * @param content 解密内容
	 * @return 解密后内容
	 */
	public static String decryptBASE64(String content) {
		if (null == content || "".equals(content)) {
			return content;
		}
		try {
			return new String(Base64.getDecoder().decode(content), ICipher.Code.CHARACTER);
		} catch (UnsupportedEncodingException e) { }
		return null;
	}
	
	/**
	 * <h4>BASE64 加密</h4>
	 * @param content 加密内容
	 * @return 加密后内容
	 */
	public static String encryptBASE64(String content){
		if (null == content || "".equals(content)) {
			return content;
		}
		try {
			return Base64.getEncoder().encodeToString(content.getBytes(ICipher.Code.CHARACTER));
		} catch (UnsupportedEncodingException e) { }
		return null;
	}

	// ==================================================== private method ==================================================== //
	/**
	 * <h4>加密{AES}</h4>
	 * <pre>
	 * 1.构造密钥生成器 
	 * 2.根据ecnodeRules规则初始化密钥生成器 
	 * 3.产生密钥 
	 * 4.创建和初始化密码器 
	 * 5.内容加密 
	 * 6.返回字符串
	 * </pre>
	 * @param key
	 * @param content
	 * @return
	 */
	private static String _encoder(String content,String key, int length,String division) {
		if (null == content || "".equals(content)) {
			return content;
		}
		try {
			// 1.构造密钥生成器，指定为AES算法,不区分大小写
			KeyGenerator keygen = KeyGenerator.getInstance(division);
			// 2.根据ecnodeRules规则初始化密钥生成器
			// 生成一个128位的随机源,根据传入的字节数组
			keygen.init(length, new SecureRandom(key.getBytes()));
			// 3.产生原始对称密钥
			SecretKey original_key = keygen.generateKey();
			// 4.获得原始对称密钥的字节数组
			byte[] raw = original_key.getEncoded();
			// 5.根据字节数组生成AES密钥
			SecretKey sercreKey = new SecretKeySpec(raw, division);
			// 6.根据指定算法AES自成密码器
			Cipher cipher = Cipher.getInstance(division);
			// 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
			cipher.init(Cipher.ENCRYPT_MODE, sercreKey);
			// 8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
			byte[] byte_encode = content.getBytes(ICipher.Code.CHARACTER);
			// 9.根据密码器的初始化方式--加密：将数据加密
			byte[] bytes = cipher.doFinal(byte_encode);
			// 10.将加密后的数据转换为字符串
			// 这里用Base64Encoder中会找不到包
			// 解决办法：
			// 在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
			String encode = Base64.getEncoder().encodeToString(bytes);
			// 11.将字符串返回
			return encode;
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 如果有错就返加nulll
		return null;
	}

	/**
	 * <h4>解密</h4>
	 * <pre>
	 * 解密过程： 
	 * 1.同加密1-4步 
	 * 2.将加密后的字符串反纺成byte[]数组 
	 * 3.将加密内容解密
	 * </pre>
	 * @param key
	 * @param content
	 * @return
	 */
	private static String _decoder(String content,String key, int length,String division) {
		if (null == content || "".equals(content)) {
			return content;
		}
		try {
			// 1.构造密钥生成器，指定为AES算法,不区分大小写
			KeyGenerator keygen = KeyGenerator.getInstance(division);
			// 2.根据ecnodeRules规则初始化密钥生成器
			// 生成一个128位的随机源,根据传入的字节数组
			keygen.init(length, new SecureRandom(key.getBytes()));
			// 3.产生原始对称密钥
			SecretKey original_key = keygen.generateKey();
			// 4.获得原始对称密钥的字节数组
			byte[] raw = original_key.getEncoded();
			// 5.根据字节数组生成AES密钥
			SecretKey secretKey = new SecretKeySpec(raw, division);
			// 6.根据指定算法AES自成密码器
			Cipher cipher = Cipher.getInstance(division);
			// 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，
			// 第二个参数为使用的KEY
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			// 8.将加密并编码后的内容解码成字节数组
			byte[] byte_content = Base64.getDecoder().decode(content);
			/*
			 * 解密
			 */
			byte[] byte_decode = cipher.doFinal(byte_content);
			String decode = new String(byte_decode, ICipher.Code.CHARACTER);
			return decode;
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 如果有错就返加nulll
		return null;
	}

	/** DES 键大小必须等于56 */
	private final static int KEY_SIZE_DES = 56;
	/** DESede(TripleDES) 键大小必须等于112或168 */
	private final static int KEY_SIZE_DESEDE = 112;
	/** AES 键大小必须等于128, 192或256，但192和256位可能不可用 */
	private final static int KEY_SIZE_AES = 128;
	/** Blowfish 键大小必须是8的倍数，只能从32到448（包括）  */
	private final static int KEY_SIZE_BLOWFISH = 96;
	/** RC2 密钥大小必须介于40到1024位之间 */
	private final static int KEY_SIZE_RC2 = 96;
}
