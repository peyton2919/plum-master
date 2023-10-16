package cn.peyton.plum.core.mail.beans;

import java.io.Serializable;
import java.util.Set;

/**
 * <h3>发送邮件的实体类</h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @create date: 2018/11/16 15:43
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
public class Mail implements Serializable{

    /** 主题 */
    private String subject;
    /**  信息 */
    private String message;
    /** 接收者集合 */
    private Set<String> receivers;

    //================================== Constructor =======================================//

    /**
     * <h4>构造函数</h4>
     */
    public Mail() {
    }

    /**
     * <h4>构造函数</h4>
     * @param subject 主题
     * @param message 信息
     * @param receivers 接收者集合
     */
    public Mail(String subject, String message, Set<String> receivers) {
        this.subject = subject;
        this.message = message;
        this.receivers = receivers;
    }
    //================================== GET AND SET =======================================//

    /**
     * @return 主题
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject 主题
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return  信息
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message 信息
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return 接收者集合
     */
    public Set<String> getReceivers() {
        return receivers;
    }

    /**
     * @param receivers 接收者集合
     */
    public void setReceivers(Set<String> receivers) {
        this.receivers = receivers;
    }
}
