package cn.peyton.plum.core.utils;

import jakarta.servlet.http.HttpSession;

import java.io.Serializable;

/**
 * <h3>用户工具类 .util</h3>
 * <pre>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2022/3/30 13:55
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
public abstract class UserUtils<T> implements Serializable {
    static String SESSION_USER = "user.peyton.cn_202310140933";

    /**
     * <h4> 从 session 获取 用户对象</h4>
     * <pre>
     *     key默认为: PROPERTY.SESSION_USER
     * </pre>
     * @param session
     * @return 用户传递对象类
     */
    public final static <T> T getUser(HttpSession session) {
        return UserUtils.getUser(session, null);
    }
    /**
     * <h4> 从 session 获取 用户对象</h4>
     * @param session
     * @param key
     * @return 用户传递对象类
     */
    public final static <T> T getUser(HttpSession session, String key) {
        if (key == null) {
            return (T) session.getAttribute(SESSION_USER);
        }
        return (T) session.getAttribute(key);
    }

    /**
     * <h4>保存用户到 session 中</h4>
     * @param session
     * @param t 用户对象
     * @param <T>
     */
    public final static <T> void saveUser(HttpSession session, T t) {
        saveUser(session, t,SESSION_USER);
    }

    /**
     * <h4>保存用户到 session 中</h4>
     * @param session
     * @param t 用户对象
     * @param key 用户key
     * @param <T>
     */
    public final static <T> void saveUser(HttpSession session, T t,String key) {
        session.setAttribute(key, t);
    }
}
