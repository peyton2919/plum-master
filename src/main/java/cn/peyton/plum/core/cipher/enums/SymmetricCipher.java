package cn.peyton.plum.core.cipher.enums;

/**
 * <h3>相称性的密码,加密解密枚举</h3>
 * <pre>
 * 	 * 有以下几种方式：
 * 	 * DES,
 * 	 * DESEDE,
 * 	 * AES,
 * 	 * BLOWFISH,
 * 	 * RC2
 * 	 * </pre>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @project name: plum
 * @class name: cn.peyton.plum.tools.cipher.enums.SymmetricCipher.java
 * @create date: 2021/11/2 14:14
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
public enum SymmetricCipher {
    /** DES 加密解密方式 键大小必须等于56  */
    DES(0,"DES"),
    /** DESede(TripleDES) 加密解密方式  键大小必须等于112或168  */
    DESEDE(1,"DESEDE"),
    /** AES 加密解密方式 键大小必须等于128, 192或256，但192和256位可能不可用  */
    AES(2,"AES"),
    /** Blowfish 加密解密方式 键大小必须是8的倍数，只能从32到448（包括）  */
    BLOWFISH(3,"BLOWFISH"),
    /** RC2 加密解密方式 密钥大小必须介于40到1024位之间  */
    RC2(4,"RC2");

    private int id;
    private String value;
    private SymmetricCipher(int id,String value) {
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
