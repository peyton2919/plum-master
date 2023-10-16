package cn.peyton.plum.core.validator.strategy.design;

import cn.peyton.plum.core.utils.LogUtils;
import cn.peyton.plum.core.utils.StrUtils;
import cn.peyton.plum.core.validator.Regulation;
import cn.peyton.plum.core.validator.constraints.Past;
import cn.peyton.plum.core.validator.strategy.AbstractValidator;

import java.lang.annotation.Annotation;
import java.text.ParseException;
import java.util.Map;

/**
 * <h3>注解的元素是一个过去的日期 策略类</h3>
 * <pre>
 * @email <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2018/11/16 16:05
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
public class PastStrategy extends AbstractValidator {

    @Override
    public void compare(Annotation annotation, String name, String type, Object value, Map<String, String> map) {
        Past future = (Past) annotation;
        String format = future.format();
        String reg = Regulation.formatRegexDate(format);
        if (null == reg) {
            map.put(name, "注解传入的正则规则样式出错了!");
            return;
        }
        message = future.message();
        if (StrUtils.isEmpty(value)) {
            if (regex(value.toString(), reg)) {
                map.put(name, "日期格式匹配出错了!");
                return;
            }
            try {
                long time = getTimeMillis(value.toString(), format);
                if (System.currentTimeMillis() < time) {
                    map.put(name, message);
                }
            } catch (ParseException e) {
                map.put(name, "比对数据出错了!");
                LogUtils.error(e.getMessage());
            }
        }
    }
}
