package cn.peyton.plum.core.validator.strategy.design;

import cn.peyton.plum.core.utils.StrUtils;
import cn.peyton.plum.core.validator.constraints.AssertFalse;
import cn.peyton.plum.core.validator.strategy.AbstractValidator;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * <h3>注解的元素为 false 策略类</h3>
 * <pre>
 * @email <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2018/11/16 16:05
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
public class AssertFalseStrategy extends AbstractValidator {
    private final String BOOL = "boolean";
    private final String BOOLEAN = "class java.lang.Boolean";
    private final String FALSE = "false";

    @Override
    public void compare(Annotation annotation, String name, String type, Object value, Map<String, String> map) {
        AssertFalse af = (AssertFalse) annotation;
        message = af.message();
        if (!BOOL.equals(type) && !BOOLEAN.equals(type)){
            map.put(name, message);
        }
        if (StrUtils.isEmpty(value)){
            if (!FALSE.equals(value.toString().trim())) {
                map.put(name, message);
            }
        }else {
            map.put(name, message);
        }
    }
}
