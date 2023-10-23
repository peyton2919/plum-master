package cn.peyton.plum.core.anno.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h4>权限 注释类</h4>
 * <pre>
 *     配合 AuthenticationAspect 使用
 * </pre>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.plum.core.anno.auth.Permission
 * @date 2023年10月15日 19:01
 * @version 1.0.0
 * </pre>
 */
@Target({ElementType.METHOD,ElementType.TYPE}) //表明此注解可用在方法上
@Retention(RetentionPolicy.RUNTIME) //运行时有效
public @interface Permission {
    /** 需要验证的类型 */
    //String type();

    /** 重定向的 链接地址 */
    String redirect();

    /**  从 session 中获取 对象的 key */
    String key();

    /** 需要核验名称, token */
    String verify() default "token";
    /**  */

    /** 需要验证的对象 */
    //虽然没用到，但是要加上
    Class<?>[] groups() default {};
}
