package cn.peyton.plum.core.validator.strategy.design;

import cn.peyton.plum.core.utils.CheckedUtils;
import cn.peyton.plum.core.validator.Regulation;
import cn.peyton.plum.core.validator.constraints.Phone;
import cn.peyton.plum.core.validator.strategy.AbstractValidator;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * <h3>注解的元素为 手机格式 策略 类</h3>
 * <pre>
 * @email <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2018/11/16 16:05
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
public class PhoneStrategy extends AbstractValidator {


    @Override
    public void compare(Annotation annotation, String name, String type, Object value, Map<String, String> map) {
        Phone phone = (Phone) annotation;
        message = phone.message();
        if (CheckedUtils.isEmpty(value)) {
            if (regex(value.toString(), Regulation.REGEX_PHONE)) {
                map.put(name, message);
            }
        }
    }
}
