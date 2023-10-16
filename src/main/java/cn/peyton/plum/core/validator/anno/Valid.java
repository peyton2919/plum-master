package cn.peyton.plum.core.validator.anno;

import java.lang.annotation.*;

/**
 * <h3></h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @project name: plum
 * @class name: cn.peyton.plum.core.validator.valid.java
 * @create date: 2022/3/21 11:20
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
@Target({ElementType.METHOD,ElementType.TYPE}) //表明此注解可用在方法上
@Retention(RetentionPolicy.RUNTIME) //运行时有效
@Documented
public @interface Valid {
    /** 默认验证对象 */
    boolean require() default true;

    /** 默认：true ; 为true时表示验证对象单一属性[遇到验证有错误时就返回,只返回一组错误或没有错误] */
    boolean single() default true;

    //虽然没用到，但是要加上
    Class<?>[] groups() default {};



    /** 对象验证 */
    // Class<?> classes() default Void.class;

    /** <h4>多个基础参数验证</h4>
     * <pre>
     *  require:必须的不能为空
     *  number:数字
     *  float:浮点数字
     *  email:邮箱
     *  date:日期
     *  datetime:时间
     *  alpha:字母(如:16af)
     *  alphaNum:字母和数字(如:16af)
     *  phone:手机
     *  url:链接地址
     *  ip:IP地址
     *  in:某个范围(如:1,2,3)
     *  between:某个区间(如：6,16)
     *  notBetween:不在某个区间(如：6,16)
     *  length:长度(如：6; 6,16)
     *  max:最大
     *  min:最小
     *
     * </pre>
     */
    // String rule() default "key->规则1|规则2:0,10|规则3:4;key->...";




}
