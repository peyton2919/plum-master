package cn.peyton.plum.core.inf.base;

/**
 * <h3>用户 基础类</h3>
 * <pre>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2018/11/16 15:35
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
public interface IUser {
    /**
     * 超级管理员 类型 数值
     */
    Integer ADMIN_TYPE_NUM = 3;
    /**
     * 员工 类型 数值
     */
    Integer EMPLOYEE_TYPE_NUM = 2;
    /**
     * 供应商 类型 数值
     */
    Integer SUPPLIER_TYPE_NUM = 1;
    /**
     * 顾客 类型 数值
     */
    Integer CUSTOMER_TYPE_NUM = 0;

}
