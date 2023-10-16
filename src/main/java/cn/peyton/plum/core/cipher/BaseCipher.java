package cn.peyton.plum.core.cipher;

import cn.peyton.plum.core.cipher.child.SymmetricCipherUtils;
import cn.peyton.plum.core.cipher.child.UnidirectionalCipherUtils;
import cn.peyton.plum.core.cipher.enums.SymmetricCipher;

import java.io.Serializable;

/**
 * <h3>基础的加密类</h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @create date: 2018/11/16 14:54
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
public abstract class BaseCipher implements Serializable {

	/** @Fields serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 使用hmac AES 内容加密key */
	public static final String CIPHER_CONTENT_KEY = "aRmPlQprlQaqldniAa";
    /** 使用hmac AES 内容加密key  模式 SymmetricCipherUtil.SymmetricCipher.AES */
    public static final SymmetricCipher CIPHER_CONTENT_KEY_MODEL = SymmetricCipher.AES;

	/**
	 * <h4>相称性 加密方式{用在内容加密}</h4>
	 * @param content 内容
	 * @return 加密后的内容
	 */
	public static String encoderHMAC(String content) {
		return SymmetricCipherUtils.encoder(content, CIPHER_CONTENT_KEY, CIPHER_CONTENT_KEY_MODEL);
	}
	/**
	 * <h4>相称性 解密方式{用在内容解密}</h4>
	 * @param content 密码内容
	 * @return 解密后内容
	 */
	public static String decoderHMAC(String content) {
		return SymmetricCipherUtils.decoder(content, CIPHER_CONTENT_KEY, CIPHER_CONTENT_KEY_MODEL);
	}
	/**
	 * <h4>单向加密{用在密码上}</h4>
	 * @param content 内容
	 * @param key 加密key
	 * @return 加密后的内容
	 */
	public static String encoderMD5(String content,String key) {
		return UnidirectionalCipherUtils.MD5(content, key);
	}

}
