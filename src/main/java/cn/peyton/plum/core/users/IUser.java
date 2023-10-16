package cn.peyton.plum.core.users;

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
    /** 超级管理员 类型 数值 */
    Integer TYPE_ADMIN = 999;
    /*** 员工 类型 数值 */
    Integer TYPE_EMPLOYEE = 998;
    /** 供应商 类型 数值 */
    Integer TYPE_SUPPLIER = 988;
    /** 顾客 类型 数值  */
    Integer TYPE_CUSTOMER = 987;


    /** 账号 登录 类型 数值 */
    Integer LOGIN_TYPE_ACCOUNT = 1909;
    /** 手机 登录 类型 数值 */
    Integer LOGIN_TYPE_PHONE = 1908;
    /** 邮箱 登录 类型 数值 */
    Integer LOGIN_TYPE_EMAIL = 1907;
    /** 微信 登录 类型 数值 */
    Integer LOGIN_TYPE_WEIXIN = 1906;
    /** 支付宝 登录 类型 数值 */
    Integer LOGIN_TYPE_ZHIFUBAO = 1905;
    /** 新浪微博 登录 类型 数值 */
    Integer LOGIN_TYPE_XINLANGWEIBO = 1904;
    /** 百度 登录 类型 数值 */
    Integer LOGIN_TYPE_BAIDU = 1903;


}
