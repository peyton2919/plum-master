package cn.peyton.plum.core.validator.strategy.design;

import cn.peyton.plum.core.utils.LogUtils;
import cn.peyton.plum.core.validator.constraints.Alike;
import cn.peyton.plum.core.validator.strategy.AbstractValidator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * <h4>被注解的元素与 fieldName 字段元素 要相同 策略类 </h4>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.core.validator.strategy.design.AlikeStrategy
 * @date 2023年10月08日 22:26
 * @version 1.0.0
 * </pre>
 */
public class AlikeStrategy  extends AbstractValidator {
    @Override
    public void compare(Annotation annotation, String name, String type, Object value, Map<String, String> map) {
        Alike _alike = (Alike) annotation;
        // 获取要判断相同 属性的字段名称
        String _cName = _alike.fieldName();

        Class<?> clazz = value.getClass();
        Field pwField = null,cpwField = null;
        String _pw = null,_cpw = null;
        try {
            pwField = clazz.getDeclaredField(_cName);
            cpwField = clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            LogUtils.error(e.getMessage());
            map.put(name, "[异常] 名称获取错误。");
        }
        pwField.setAccessible(true);
        cpwField.setAccessible(true);
        try {
            _pw = (String) pwField.get(value);
            _cpw = (String) cpwField.get(value);
        } catch (IllegalAccessException e) {
            LogUtils.error(e.getMessage());
            map.put(name, "[异常] 属性值获取错误。");
        }
        if(null == _cpw || !(_cpw).equals(_pw)) {
            map.put(name, _alike.message());
        }
    }
}
