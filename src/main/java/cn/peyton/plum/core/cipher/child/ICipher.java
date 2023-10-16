package cn.peyton.plum.core.cipher.child;

/**
 * <h3>加密解密规范</h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @create date: 2018/11/16 14:39
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
public interface ICipher {

    /**
     * <h4>加密</h4>
     *
     */
    void encoder();

    /**
     * <h4>解密</h4>
     *
     */
    void decoder();

    public class Code{
        /** 编码格式为 'UTF' */
        public static final String CHARACTER ="UTF-8";
    }
}
