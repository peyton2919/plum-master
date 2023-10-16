package cn.peyton.plum.core.validator;


import cn.peyton.plum.core.err.ValidationException;
import cn.peyton.plum.core.json.JSONResult;
import cn.peyton.plum.core.page.ResponseStatus;
import cn.peyton.plum.core.utils.LogUtils;
import cn.peyton.plum.core.validator.strategy.BaseValidator;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * <h3>外部 调用验证类</h3>
 * <pre>
 * @email <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2018/11/16 16:05
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
public final class Validation implements Serializable {

    private static BaseValidator validator = BaseValidator.getInstance();

    /**
     * <h4>验证对象全部错误</h4>
     * @param obj 对象
     */
    public static void check(Object obj) {
        Map<String, String> errors = validator.validate(obj);
        _ex(errors);
    }

    /**
     * <h4>验证对象错误</h4>
     * @param obj 对象
     * @param single 为true时表示验证对象单一属性[遇到验证有错误时就返回,只返回一组错误或没有错误]
     */
    public static void check(Object obj,boolean single) {
        if (single){
            Map<String, String> errors = validator.validateProperty(obj);
            _ex(errors);
        }
        check(obj);
    }

    /**
     * <h4>验证对象全部错误</h4>
     * @param obj 对象
     * @return 错误集合
     */
    public static Map<String, String> valid(Object obj) {
        return validator.validate(obj);
    }


    /**
     * <h4>验证对象错误</h4>
     * @param obj 对象
     * @param single 为true时表示验证对象单一属性[遇到验证有错误时就返回,只返回一组错误或没有错误]
     * @return 错误集合
     */
    public static Map<String, String> valid(Object obj, boolean single) {
        if (single) {
            return validator.validateProperty(obj);
        }
        return valid(obj);
    }

    /**
     *<h4>验证错误</h4>
     * <pre>
     *     false表示对象全部字段, true表示单个字段[有一个错误信息就返回]
     * </pre>
     * @param errMap 错误集合
     * @param annotations 注解集合
     * @param name 属性名称
     * @param type 字段类型
     * @param value 值
     * @param single false表示对象全部字段, true表示单个字段[有一个错误信息就返回]
     */
    public static void valid(Map<String,String> errMap, Annotation[] annotations,
                           String name, String type, String value,
                           boolean single) {
        if (single) {
            valid(errMap, annotations, name, type, value);
        } else {
            validator.validate(errMap, annotations, name, type, value,single);
        }

    }

    /**
     *<h4>验证错误</h4>
     * <pre>
     *     false表示对象全部字段, true表示单个字段[有一个错误信息就返回]
     * </pre>
     * @param errMap 错误集合
     * @param annotations 注解集合
     * @param name 属性名称
     * @param type 字段类型
     * @param value 值
     */
    public static void valid(Map<String,String> errMap, Annotation[] annotations,
                                            String name, String type, String value) {
        validator.validate(errMap,annotations,name,type,value,true);
    }

    /**
     * <h4>map 转成 字符串 格式</h4>
     * @param errors 错误集合
     */
    private static void _ex(Map<String, String> errors) {

        if (validator.ERROR) {
            StringBuffer sb = new StringBuffer();
            for(String key : errors.keySet()){
                sb.append(errors.get(key) + "<br>");
            }
            LogUtils.info(sb);
            throw new ValidationException(JSONResult.error(ResponseStatus.VALIDATE_FAIL,sb));
        }
    }

}
