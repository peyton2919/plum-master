package cn.peyton.plum.core.utils;

import java.io.Serializable;

/**
 * <h3>自定义 层级类</h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @create date: 2018/11/16 15:58
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
public final class LevelUtils implements Serializable {
    /**  连接符 */
    public final static String SEPARATOR = ".";

    /**  Level 根目录 默认: 0 */
    public final static String ROOT = "0";

    /**
     *
     */
    /**
     * <h4>Level 计算规则</h4>
     * <pre>
     *     0
     *     0.1
     *     0.1.2
     *     0.1.3
     *     0.4
     * </pre>
     * @param parentLevel
     * @param parentId
     * @return
     */
    public static String calculateLevel(String parentLevel,int parentId) {
        if (StrUtils.isBlank(parentLevel)) {
            return ROOT;
        } else {
            return StrUtils.join(parentLevel, SEPARATOR, parentId);
        }
    }

    /**
     * <h4>Level 计算规则</h4>
     * <pre>
     *     0
     *     0.1
     *     0.1.2
     *     0.1.3
     *     0.4
     * </pre>
     * @param parentLevel
     * @param parentId
     * @return
     */
    public static String calculateLevelLong(String parentLevel,Long parentId) {
        if (StrUtils.isBlank(parentLevel)) {
            return ROOT;
        } else {
            return StrUtils.join(parentLevel, SEPARATOR, parentId);
        }
    }

    /**
     * <h4>Level 计算规则</h4>
     * <pre>
     *     0
     *     0.1
     *     0.1.2
     *     0.1.3
     *     0.4
     * </pre>
     * @param parentLevel
     * @param parentId
     * @return
     */
    public static String calculateLevelObject(String parentLevel,Object parentId) {
        if (StrUtils.isBlank(parentLevel)) {
            return ROOT;
        } else {
            return StrUtils.join(parentLevel, SEPARATOR, parentId);
        }
    }

}
