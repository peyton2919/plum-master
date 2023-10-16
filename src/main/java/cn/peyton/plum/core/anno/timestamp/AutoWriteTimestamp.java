package cn.peyton.plum.core.anno.timestamp;

import java.lang.annotation.*;

/**
 * <h4>自动创建 10位的时间戳 注解</h4>
 * <pre>
 *     通过 @AutoWriteTimestamp 注解方式(注释在 service 层上需要自动生成的时间属性的方法上);
 *     配合 AutoWriteTimestampAspect 类 aop 切片使用;
 * </pre>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.children.chatter.aop.timestamp.Titestamp
 * @date 2023年10月07日 21:12
 * @version 1.0.0
 * </pre>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoWriteTimestamp {
    /**
     * <h4>需要创建时间(10 位 int 类型)的属性名称</h4>
     * <pre>
     *     对象的时间属性，多个时 用 `,` 格开
     *     字符串时间格式: yyyy-MM-dd HH:mm:ss
     * </pre>
     * @return
     */
    String value() default "createTime";

}
