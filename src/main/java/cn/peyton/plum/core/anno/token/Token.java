package cn.peyton.plum.core.anno.token;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h3>加入此注解,需要 token 验证</h3>
 <pre>
 *     通过 @Token 注解方式(注释在 controller 层上需要 Token 验证的的方法上);
 *     配合 TokenAspect 类 aop 切片使用;
 * </pre>
 * <pre>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2022/3/20 22:46
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
@Target({ElementType.METHOD,ElementType.TYPE}) //表明此注解可用在方法上
@Retention(RetentionPolicy.RUNTIME) //运行时有效
public @interface Token {

    boolean required() default true;
}
