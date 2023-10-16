package cn.peyton.plum.core.mail.utils;

import cn.peyton.plum.core.mail.beans.Mail;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <h3>Mail 工具类</h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @create date: 2018/11/16 15:42
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
public final class MailUtils implements Serializable {

    /**
     * <h4>获取Mail对象[单个]</h4>
     * @param receiver 接收者地址
     * @param password 密码[没有加密过]
     * @return
     */
    public static Mail getMail(String receiver , String password) {
        Mail mail = new Mail();
        mail.setSubject("密码 注册");
        mail.setMessage("你所注册的密码为: [ " + password + " ]");
        Set<String> set = new HashSet<>();
        set.add(receiver);
        mail.setReceivers(set);
        return mail;
    }

    /**
     * <h4>获取Mail对象[多个]</h4>
     * @param receivers 接收者地址集合
     * @param password 密码[没有加密过]
     * @return
     */
    public static Mail getMail(List<String> receivers, String password) {
        if (null == receivers || receivers.size() == 0) {return new Mail();}

        Mail mail = new Mail();
        mail.setSubject("密码 注册");
        mail.setMessage("你所注册的密码为: [ " + password + " ]");
        Set<String> set = new HashSet<>();
        for(String receiver : receivers) {
            set.add(receiver);
        }
        mail.setReceivers(set);
        return mail;
    }
}
