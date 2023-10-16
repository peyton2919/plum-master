package cn.peyton.plum.core.cipher.child;

import cn.peyton.plum.core.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

/**
 * <h3>MD5 加密类 .</h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @create date: 2018/11/16 14:40
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
@Slf4j
public final class Md5Utils {

    /**
     * <h4>MD单向加密</h4>
     * @param content 要加密内容
     * @return 加密后内容
     */
    public final static String encrypt(String content) {
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = content.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            log.error("generate md5 error, {}", content, e);
            LogUtils.error(content);
            return null;
        }
    }

    /**
     * <h4>MD单向加密</h4>
     * @param content 要加密内容
     * @param key key值
     * @return 加密后内容
     */
    public static String encrypt(String content, String key) {
        if (null == content || "".equals(content)) {
            return content;
        }
        String md5 = encrypt(content);
        return encrypt(md5 + key);
    }

//    public static void main(String[] args) {
//        String pwd = "12345678";
//        String key = "asdfasdfe78^&<>?%$#@!";
//        System.out.println(encrypt(pwd));
//        System.out.println(encrypt(pwd,key));
//        System.out.println(encrypt(pwd));
//    }
}
