package cn.peyton.plum.core.validator.constraints;

/**
 * <h3>判断 类型枚举</h3>
 * <pre>
 * @email <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2018/11/16 16:05
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
public interface Constants {
    /** 元素必须为 false*/
    int ASSERT_FALSE = 1000;
    /** 元素必须为 true */
    int ASSERT_TRUE = 1001;
    /** 元素必须为 日期 [yyyy/MM/dd;yyyyMMdd;yyyy-MM-dd] */
    int DATE = 1002;
    /** 元素必须为 日期时间 [yyyy/MM/dd hh:mm:ss;yyyyMMdd hh:mm:ss;yyyy-MM-dd hh:mm:ss] */
    int  DATETIME = 1003;
    /** 元素必须为 数字并小于等于指定的最大值 */
    int DECIMAL_MAX = 1004;
    /** 元素必须为 数字并大于等于指定的最小值 */
    int DECIMAL_MIN = 1005;
    /** 元素必须为 邮箱  */
    int EMAIL = 1006;
    /** 元素必须为 一个将来的日期 */
    int FUTURE = 1007;
    /** 元素字符串的大小必须为 在指定的范围内 */
    int LENGTH = 1008;
    /** 元素大小必须为 在指定的范围内*/
    int SIZE = 1009;
    /** 元素必须为 数字并小于等于指定的最大值 */
    int MAX = 2000;
    /** 元素必须为 素必须为 数字并大于等于指定的最小值 */
    int MIN =  2001;
    /** 元素字符串必须为 非null，且长度必须大于0  */
    int NOT_BLANK = 2002;
    /** 元素必须不为 null */
    int NOT_NULL = 2003;
    /** 元素必须为 null */
    int NULL = 2004;
    /** 元素必须为 一个过去的日期 */
    int PAST = 2005;
    /** 元素必须为 手机  */
    int PHONE = 2006;
    /** 元素必须为 电话 */
    int TELEPHONE = 2007;
    /** 元素必须为 时间 [hh:mm:ss]*/
    int TIME = 2008;

}
