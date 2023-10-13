package cn.peyton.plum.core.inf;

import cn.peyton.plum.core.inf.base.IUser;

import java.io.Serializable;

/**
 * <h3>基础 用户类 .</h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @create date: 2018/11/16 14:55
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
public abstract class BaseUser<K,T> implements IUser, Serializable {

    /**
     * <h4>设置用户类型[强制子类实现]</h4>
     * @return
     */
    protected abstract String abstractUserType();

    /**
     * <h4>获取用户类型</h4>
     * @return
     */
    public String getUserType() {
        return abstractUserType();
    }

    /**
     * <h4>设置用户类型 数值[强制子类实现]</h4>
     * @return -1为错误 0，1，2，3 正常
     */
    protected abstract Integer abstractUserTypeValue();

    /**
     * <h4>获取用户类型 数值</h4>
     * @return -1为错误 0，1，2，3 正常
     */
    public Integer getUserTypeValue() {
        return abstractUserTypeValue();
    }

    /**
     * <h4>设置用户名称[强制子类实现]</h4>
     * @return 获取名称
     */
    protected abstract String abstractUserName();

    /**
     * @return 获取名称
     */
    public String getUserName() {
        return abstractUserName();
    }

    /**
     * <h4>设置 主键 数值[强制子类实现]</h4>
     * @return 用户主键
     */

    protected abstract <K> K abstractPrimaryKey();
    /**
     * @return 用户主键
     */
    public <K> K getPrimaryKey() {
        return abstractPrimaryKey();
    }

    /**
     * <h4>获取子用户对象</h4>
     * @param <T> 子用户对象
     * @return 子用户对象
     */
    protected abstract <T> T abstractObject();

    /**
     * <h4>获取子用户对象</h4>
     * @param <T> 子用户对象
     * @return 子用户对象
     */
    public <T> T getObject() {
        return abstractObject();
    }

}
