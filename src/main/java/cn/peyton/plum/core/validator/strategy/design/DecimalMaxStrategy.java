package cn.peyton.plum.core.validator.strategy.design;

import cn.peyton.plum.core.utils.StrUtils;
import cn.peyton.plum.core.validator.constraints.DecimalMax;
import cn.peyton.plum.core.validator.strategy.AbstractValidator;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.Map;

/**
 * <h3>注解的元素是浮点数字，其值必须小于等于指定的最大值 策略类</h3>
 * <pre>
 * @email <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2018/11/16 16:05
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
public class DecimalMaxStrategy extends AbstractValidator {

    @Override
    public void compare(Annotation annotation, String name, String type, Object value, Map<String, String> map) {
        if (existDecimal(type)) {
            map.put(name, "数据类型不正确");
            return;
        }
        DecimalMax decimalMax = (DecimalMax) annotation;
        message = decimalMax.message();
        BigDecimal max = new BigDecimal(decimalMax.value());
        if (StrUtils.isEmpty(value)) {
            BigDecimal bigDecimal = new BigDecimal(value.toString());
            if (bigDecimal.compareTo(max) > 0) {
                map.put(name, message);
            }
        }
    }
}
