package cn.peyton.plum.core.validator.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <h3>被注解的字符串的大小必须在指定的范围内</h3>
 * <pre>
 * @email <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2018/11/16 16:05
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
public @interface Length {
    /**
     * @return 错误信息提示
     */
    String message();

    /**
     * @return 值元素长度必须大于或等于
     */
    long min() default 1;
    /**
     * @return 值元素长度必须小于或等于
     */
    long max();
}
