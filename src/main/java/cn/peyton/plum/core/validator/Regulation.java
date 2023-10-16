package cn.peyton.plum.core.validator;

import cn.peyton.plum.core.err.GlobalException;
import cn.peyton.plum.core.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * <h3>正则 规则 类</h3>
 * <pre>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2018/11/16 15:44
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
@Slf4j
public final class Regulation implements Serializable {

    /** 中文验证 正则表达式<br>  样式 ：2000/01/01 | 2000 01 01 | 2000-01-01 |
     * 2000/01/01 12:00:00 | 2000 01 01 12:00:00 | 2000-01-01 12:00:00 */
    public static final String REGX_SIMPLE_DATE = "^((\\d{2}(([02468][048])|"
            + "([13579][26]))[\\-\\/\\s]?((((0?[13578])|"
            + "(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))"
            + "|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))"
            + "|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|"
            + "(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|"
            + "(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|"
            + "(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|"
            + "(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))"
            + "(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
    /**
     * <pre>
     *      <h4>日期 验证 可匹配2月28与29</h4>
     *      格式:{yyyyMMdd,yyyy/MM/dd,yyyy-MM-dd}
     * </pre>
     */
    public final  static String REGEX_DATE = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})"
            + "(\\s*|[-\\/])(((0[13578]|1[02])(\\s*|[-\\/])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(\\s*|[-\\/])"
            + "(0[1-9]|[12][0-9]|30))|(02(\\s*|[-\\/])(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})"
            + "(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))(\\s*|[-\\/])02(\\s*|[-\\/])29))$";

    /** <pre>
     *      <h4>时间 验证</h4>
     *      格式:{HHmmss,HH-mm-ss,HH/mm/ss,HH:mm:ss}
     * </pre>
     */
    public final  static String REGEX_TIME = "^([0-1]?[0-9]|2[0-3])(\\s*|[-\\/]|[:])([0-5][0-9])(\\s*|[-\\/]|[:])([0-5][0-9])$";

    /** <pre>
     *      <h4>时间 验证</h4>
     *      格式:{HH:mm:ss}
     * </pre>
     */
    public final  static String REGEX_SIMPLE_TIME = "^([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";

    /**
     * <pre>
     *      <h4>日期时间 可匹配2月28与29</h4>
     *      格式:{yyyyMMdd,yyyy/MM/dd,yyyy-MM-dd}} | {HHmmss,HH-mm-ss,HH/mm/ss,HH:mm:ss} 可任意搭配
     * </pre>
     */
    public final  static String REGEX_DATETIME ="^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})" +
            "(\\s*|[-\\/])(((0[13578]|1[02])(\\s*|[-\\/])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(\\s*|[-\\/])" +
            "(0[1-9]|[12][0-9]|30))|(02(\\s*|[-\\/])(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})" +
            "(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))(\\s*|[-\\/])02(\\s*|[-\\/])29))" +
            "(\\s*|[-\\/]|[:])([0-1]?[0-9]|2[0-3])(\\s*|[-\\/]|[:])([0-5][0-9])(\\s*|[-\\/]|[:])([0-5][0-9])$";
    /**
     * <pre>
     *     <h4>邮箱 验证</h4>
     *     只能是字母与数字 组成
     *     格式: {xyzzz@xxx.com}
     * </pre>
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+"
                    + "[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * <pre>
     *     <h4>邮箱 验证</h4>
     *     格式: {xyzzz@xxx.com}
     * </pre>
     */
    public static final String REGEX_EMAIL_ALL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /**
     * <pre>
     *      <h4>手机 验证</h4>
     *      样式 {130(到139)12345678 150(到159不含154)12345678 180(到189)12345678}
     * </pre>
     */
    public static final String REGEX_PHONE = "^((13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))+(\\d{8})$";

    /**
     * <pre>
     *      <h4>电话 验证</h4>
     *      样式 {1234567 12345678 0001234567 0001245678 00001234567 000012345678
     *          000-1234567 000-12345678 <br>0000-1234567 0000-12345678
     *      }
     * </pre>
     */
    public static final String REGEX_TEL = "^(([0]\\d{2,3}[\\-\\s*])|([0]\\d{2,3}))(\\d{7,8})$";

    /**
     * <pre>
     *      <h4>电话与手机 验证</h4>
     *      样式 {1234567 12345678 0001234567 0001245678 00001234567 000012345678
     *          000-1234567 000-12345678 0000-1234567 0000-12345678 130(到139)12345678
     *          150(到159不含154)12345678 180(到189)12345678
     *      }
     * </pre>
     */
    public static final String REGEX_TEL_PHONE = "^(((([0]\\d{2,3}[\\-\\s*])|([0]\\d{2,3}))(\\d{7,8}))|" +
            "(((13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))+(\\d{8})))$";

    /**
     * <pre>
     *      <h4>整数 验证</h4>
     * </pre>
     */
    public static final String REGEX_INT = "^(\\d+)$";

    /**
     * <pre>
     *      <h4>正负整数 验证</h4>
     * </pre>
     */
    public static final String REGEX_INT_ALL = "^(-)?[1-9][0-9]*$";

    /**
     * <pre>
     *      <h4>QQ 验证</h4>
     * </pre>
     */
    public static final String REGEX_QQ = "^((([1-9](\\d){4,14})|([1-9](\\d){4,14}\\s))+)$";

    /**
     * <pre>
     *      <h4>整数(0-100) 验证</h4>
     * </pre>
     */
    public static final String REGEX_INT_1_100 = "^([1-9]\\d?|100)$";

    /**
     * <pre>
     *      <h4>浮点小数(0-1)  验证</h4>
     * </pre>
     */
    public static final String REGEX_FLOAT_0_1 = "^(([0]+\\.+\\d+)|([1]+\\.+[0]+))$";

    /**
     * <pre>
     *      <h4>钱小数(2)  验证</h4>
     * </pre>
     */
    public static final String REGEX_MONEY = "^([1-9][\\d]{0,7}|0)(\\.[\\d]{1,2})?$";

    /**
     * <pre>
     *      <h4>BigDecimal(10,3)正负数 浮点小数(0-3) 验证</h4>
     * </pre>
     */
    public static final String REGEX_DECIMAL = "^(-)?(\\d{0,10}.\\d{0,3})$";

    /**
     * <pre>
     *      <h4>浮点小数  验证</h4>
     * </pre>
     */
    public static final String REGEX_DOUBLE_ALL = "^(-)?((\\d+\\.+\\d+)||\\d+)$";

    /**
     * <pre>
     *      <h4>浮点小数与 整数  验证</h4>
     * </pre>
     */
    public static final String REGEX_INT_DOUBLE = "^((\\d+\\.+\\d+)||(\\d+))$";

    /**
     * <pre>
     *      <h4>浮点小数 BigDecimal 12位号数 6位小数  验证</h4>
     * </pre>
     */
    public static final String REGEX_BIG_DECIMAL = "^[1-9]\\d{0,12}(\\.\\d{1,6})?$|^0(\\.\\d{0,6})?$";

    /**
     * <pre>
     *      <h4>网址 验证</h4>
     *      样式 {http://www.baidu.com , http://baidu.com}
     * </pre>
     */
    public static final String REGEX_WEB_SITE_URL = "^(http://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?)$";

    /**
     * <pre>
     *      <h4>中文 验证</h4>
     * </pre>
     */
    public static final String REGEX_CHINESE = "^([\u4e00-\u9fa5]{0,})$";

    /**
     * <pre>
     *      <h4>身份证 验证</h4>
     * </pre>
     */
    public static final String REGEX_IDENTITY = "^((\\d{15})|(\\d{18})|(\\d{17}(\\d|X|x)))$";

    /**
     * <pre>
     *      <h4>邮编 验证</h4>
     * </pre>
     */
    public static final String REGEX_ZIP_CODE = "^[1-9]\\d{5}(?!\\d)$";

    /**
     * <pre>
     *      <h4>字段 由英文、数字、下划线组成 验证</h4>
     * </pre>
     */
    public static final String REGEX_ENGLISH_NUMERAL_UNDERLINE = "^\\w+$";

    /**
     * <pre>
     *      <h4>用户名称长度6-18(包含A-Za-z0-9_-) 验证</h4>
     * </pre>
     */
    public static final String REGEX_USERNAME_6_18 = "^[A-Za-z0-9_-]{6,18}$";

    /**
     * <pre>
     *      <h4>用户名称长度6-18(包含A-Za-z0-9_-@#+=) 验证</h4>
     * </pre>
     */
    public static final String REGEX_PASSWORD_6_18 = "^[A-Za-z0-9_-@#+=]{6,18}$";

    /**
     * <h4>正则校验</h4>
     * @param check 校验规则 (在本类中以REGX_开头的静态属性)
     * @param valiField 要校验的字符
     * @return true 表示校验通过 ; false 表示没校验通过;
     */
    public static Boolean regex(String check, String valiField) {
        try {
            return  Pattern.matches(check,valiField);
        } catch (GlobalException e) {
            log.error("正则匹配异常: 匹配规则:{} , 匹配字段:{}",check,valiField);
            LogUtils.error(check, "不匹配", valiField, e.getMessage());
        }
        return false;
    }

    /**
     * <h4>格式不正确 提示</h4>
     * @param title 标题
     * @return
     */
    public static String hintIncorrect(String title) {
        return title + "格式不正确!";
    }

    /**
     * <h4>不可以为空 提示</h4>
     * @param title 标题
     * @return
     */
    public static String hintNotBlank(String title) {
        return title + "不可以为空!";
    }

    /**
     * <h4>长度 提示</h4>
     * @param title 标题
     * @param min 最小值
     * @param max
     * @return
     */
    public static String hintLength(String title, int min, int max) {
        if (min >= 0) {
            return title + "长度需要在" + max + "个字以内!";
        }else {
            return title + "长度需要在" + min + "到" + max + " 个字之间!";
        }
    }

    /**
     * <h4>日期 正则 </h4>
     * <pre>
     *     返回正则字符串
     * </pre>
     * @param format 日期格式样式 [yyyy/MM/dd]
     * @return
     */
    public static String formatRegexDate(String format) {
        int length = format.length();
        if (length != 8 && length != 10) {return null;}
        String sub = "";
        String sub1 = "";
        if (length == 10){
            sub = format.substring(4,5);
            sub1 = format.substring(7, 8);
            if (!sub.equals(sub1)){return null;}
        }
        if ("/".equals(sub)) {
            return "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})\\/(((0[13578]|1[02])\\/(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)\\/(0[1-9]|[12][0-9]|30))|(02\\/(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))\\/02\\/29))$";
        } else if (" ".equals(sub)) {
            return "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})\\s*(((0[13578]|1[02])\\s*(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)\\s*(0[1-9]|[12][0-9]|30))|(02\\s*(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))\\s*02\\s*29))$";
        } else if ("-".equals(sub)) {
            return "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))$";
       }
       //else {
//            return "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229))$";
//        }
        return null;
    }
}
