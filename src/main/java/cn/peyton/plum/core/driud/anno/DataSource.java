package cn.peyton.plum.core.driud.anno;

import cn.peyton.plum.core.driud.enums.DataSourceType;

import java.lang.annotation.*;

/**
 * <h4>数据源 注释</h4>
 * <pre>
 *     可以声明在 方法 和 类 上
 * </pre>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.plum.config.driud.anno.DataSource
 * @date 2023年10月11日 23:32
 * @version 1.0.0
 * </pre>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {

    public DataSourceType value() default DataSourceType.MASTER;
}
