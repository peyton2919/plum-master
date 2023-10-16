/**
 * <h3>加密包</h3>
 * <pre>
 *      直接调用 BaseCipher 类下的方法：
 *          1. encoderMD5(content,key) 单向 MD5 加密
 *          2. encoderHMAC(content) 相称性AES 加密
 *          3. decoderHMAC(content) 相称性AES 解密
 * </pre>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2023年10月04日 20:11:31
 * @version 1.0.0
 * </pre>
 */
package cn.peyton.plum.core.cipher;