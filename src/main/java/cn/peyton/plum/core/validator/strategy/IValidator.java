package cn.peyton.plum.core.validator.strategy;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * <h3>注解 验证器 接口 .</h3>
 * <pre>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2018/11/16 16:12
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
public interface IValidator {
    /**
     * <h4>类型验证器</h4>
     * @param annotation 注解类型
     * @param name 属性名称
     * @param type 属性类型
     * @param value 值
     * @param map 错误信息集合 [LinkedHashMap]
     */
    void compare(Annotation annotation, String name,
                 String type, Object value, Map<String, String> map);


}
