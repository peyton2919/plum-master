package cn.peyton.plum.core.mail;

import cn.peyton.plum.core.mail.utils.MailEncryptUtils;

import java.io.Serializable;

/**
 * <h3>发送邮件的 主机监听</h3>
 * <pre>
 *      这个监听必需要在spring配置文件中配置
 * </pre>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @create date: 2018/11/16 15:42
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
public class MailListener  implements Serializable {
    /** 发送邮件 地址  */
    private String from;
    /** 端口  */
    private Integer port;
    /** 密码  */
    private String password;
    /**  主机 */
    private String host;
    /**  名称 */
    private String nickname;
    /**  是否需要加密 默认 false */
    private boolean encryptMode = false;

    public MailListener(){}

    /**
     * @return 发送邮件 地址
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from 发送邮件 地址
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return 端口
     */
    public Integer getPort() {
        return port;
    }

    /**
     * @param port 端口
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * @return 密码
     */
    public String getPassword() {
        if (isEncryptMode()) {
            return MailEncryptUtils.decoder(password);
        }
        return password;
    }

    /**
     * @param password 密码
     */
    public void setPassword(String password) {

        this.password = password;
    }

    /**
     * @return 主机
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host 主机
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return 名称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname 名称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return 是否需要加密 默认 false
     */
    public boolean isEncryptMode() {
        return encryptMode;
    }

    /**
     * @param encryptMode 是否需要加密 默认 false
     */
    public void setEncryptMode(boolean encryptMode) {
        this.encryptMode = encryptMode;
    }
}
