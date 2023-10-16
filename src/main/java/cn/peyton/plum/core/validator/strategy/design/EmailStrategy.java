package cn.peyton.plum.core.validator.strategy.design;

import cn.peyton.plum.core.utils.StrUtils;
import cn.peyton.plum.core.validator.Regulation;
import cn.peyton.plum.core.validator.constraints.Email;
import cn.peyton.plum.core.validator.strategy.AbstractValidator;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * <h3>注解的元素为 邮箱格式 策略 类</h3>
 * <pre>
 * @email <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2018/11/16 16:05
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
public class EmailStrategy extends AbstractValidator {


    @Override
    public void compare(Annotation annotation, String name, String type, Object value, Map<String, String> map) {
        Email email = (Email) annotation;
        message = email.message();
        if (StrUtils.isEmpty(value)) {
            if (regex(value.toString(), Regulation.REGEX_EMAIL_ALL)) {
                map.put(name, message);
            }
        }
    }
}
