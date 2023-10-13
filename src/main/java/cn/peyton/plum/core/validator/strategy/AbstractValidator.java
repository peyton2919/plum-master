package cn.peyton.plum.core.validator.strategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h3>注解 验证器 抽象类</h3>
 * <pre>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2018/11/16 16:11
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
public abstract class AbstractValidator implements IValidator {

    private final String BIG_DECIMAL = "class java.math.BigDecimal";
    private final String FLOAT = "class java.lang.Float";
    private final String DOUBLE = "class java.lang.Double";
    private final String STRING = "class java.lang.String";
    private final String _FLOAT = "float";
    private final String _DOUBLE = "double";
    private final String INTEGER = "class java.lang.Integer";
    private final String INT = "int";
    private final String LONG = "class java.lang.Long,long";


    /**
     * 错误信息
     */
    protected String message;

    /**
     * <h4>正则匹配[多个用','分开]</h4>
     * @param value 值
     * @param rule 正则规则
     */
    protected boolean regex(String value , String rule) {
        String[] splits = value.split(",");
        Pattern pattern;
        Matcher matcher;
        for (String s : splits){
            pattern = Pattern.compile(rule);
            matcher = pattern.matcher(s.trim());
            if (!matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * <h4>判断浮点 数字 类型</h4>
     * <pre>
     *     比对 [BigDecimal , Float , Double , float , double , String]
     * </pre>
     * @param type 数据 类型
     * @return true为不是浮点类型
     */
    protected boolean existDecimal(String type) {
        if (BIG_DECIMAL.contains(type) ||
                FLOAT.contains(type) ||
                DOUBLE.contains(type) ||
                _FLOAT.contains(type) ||
                _DOUBLE.contains(type) ||
                STRING.contains(type)) {
            return false;
        }
        return true;
    }
    /**
     * <h4>判断整型 数字 类型</h4>
     * <pre>
     *     比对 [Integer,Long , int , String]
     * </pre>
     * @param type 数据 类型
     * @return true为不是整型类型
     */
    protected boolean existInt(String type) {
        if (INTEGER.contains(type) ||
                INT.contains(type) ||
                LONG.contains(type)||
                STRING.contains(type)) {
            return false;
        }
        return true;
    }

    /**
     * <h4>时间格式化</h4>
     * @param format
     * @return
     */
    protected SimpleDateFormat getDateFormat(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * <h4>获取时间 毫秒数</h4>
     * @param value 时间值
     * @param format 时间格式化
     * @return
     * @throws ParseException
     */
    protected long getTimeMillis(String value,String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date = dateFormat.parse(value);
        return date.getTime();
    }
}
