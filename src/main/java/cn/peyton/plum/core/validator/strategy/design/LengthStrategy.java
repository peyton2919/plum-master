package cn.peyton.plum.core.validator.strategy.design;

import cn.peyton.plum.core.utils.StrUtils;
import cn.peyton.plum.core.validator.constraints.Length;
import cn.peyton.plum.core.validator.strategy.AbstractValidator;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * <h3>注解的字符串的大小必须在指定的范围内 策略类</h3>
 * <pre>
 * @email <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2018/11/16 16:05
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
public class LengthStrategy extends AbstractValidator {


    @Override
    public void compare(Annotation annotation, String name, String type, Object value, Map<String, String> map) {
        Length length = (Length) annotation;
        message = length.message();
        long min = length.min() < 1 ? 1: length.min();
        long max = length.max();
        if (min > max) {
            map.put(name, "设置值错误,max值 要大于 min值");
            return;
        }
        if (StrUtils.isEmpty(value)) {
            long le = value.toString().trim().length();
            if (le < min || le > max) {
                map.put(name, message + "[取值范围:" + min + " ~ " + max + "之间]");
            }
        }
    }
}
