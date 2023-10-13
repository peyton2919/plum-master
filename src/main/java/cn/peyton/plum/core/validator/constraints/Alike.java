package cn.peyton.plum.core.validator.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <h4>被注解的元素与 fieldName 字段元素 要相同</h4>
 * <pre>
 *     只能注释在对象内
 * </pre>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.core.validator.constraints.Alike
 * @date 2023年10月08日 22:02
 * @version 1.0.0
 * </pre>
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
public @interface Alike {

    /**
     * @return 需要判断的元素 字段名称(必选)
     */
    String fieldName();
    /**
     * @return 错误信息提示
     */
    String message() default "确认密码不正确。";
}
