package cn.peyton.plum.core.utils;

import cn.peyton.plum.core.err.ValidationException;

import java.io.Serializable;
import java.util.List;

/**
 * <h3>检查工具类 (string).</h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @create date: 2018/11/16 15:54
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
public final class CheckedUtils implements Serializable {
    /**
     * <h4>为空判断,长度必需大于0</h4>
     * @param obj 字符串
     * @return 不为空返回true
     */
    public static boolean isEmpty(Object obj) {
        if (null != obj && !"".equals(obj.toString().trim())) {
            return true;
        }
        return false;
    }

    /**
     * <h4>为空判断</h4>
     * @param obj 字符串
     * @return 不为空返回true
     */
    public static boolean isNull(Object obj) {
        if (null != obj) {
            return true;
        }
        return false;
    }

    /**
     * <h4>判断List 集合是否为空</h4>
     * @param list 集合
     * @return 不为空返回true
     */
    public static boolean isNotEmptyList(List<?> list) {
        if (null != list && list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * <h4>检查对象如果为空,抛出 ValidationException 异常</h4>
     * @param obj
     * @param message
     */
    public static void checkNoNull(Object obj, String message) {
        if (null == obj) {
            throw new ValidationException(message);
        }
    }

}
