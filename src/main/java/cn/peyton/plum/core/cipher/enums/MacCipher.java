package cn.peyton.plum.core.cipher.enums;

/**
 * <h3>MAC算法可选以下多种算法</h3>
 * 	 * <pre>
 * 	 * HmacMD5
 * 	 * HmacSHA1
 * 	 * HmacSHA256
 * 	 * HmacSHA384
 * 	 * HmacSHA512
 * 	 * </pre>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @project name: plum
 * @class name: cn.peyton.plum.tools.cipher.enums.MacCipher.java
 * @create date: 2021/11/2 14:17
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
public enum MacCipher {
    HmacMD5(0,"HmacMD5") ,

    HmacSHA1(1,"HmacSHA1") ,

    HmacSHA256(2,"HmacSHA256")  ,

    HmacSHA384(3,"HmacSHA384"),

    HmacSHA512(4,"HmacSHA512");

    private int id;
    private String value;
    private MacCipher(int id, String value) {
        this.id = id;
        this.value = value;
    }
    public int getId() {
        return id;
    }
    public String getValue() {
        return value;
    }
}
