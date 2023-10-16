package cn.peyton.plum.core.inf;


import cn.peyton.plum.core.inf.bo.IBo;
import cn.peyton.plum.core.utils.DateUtils;
import cn.peyton.plum.core.utils.base.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * <h3>业务对象 公共 抽象类 .</h3>
 * <pre>
 *     参数P T 用法
 *     P 表示 入参时验证用的对象[页面上用的param对象]
 *     T 表示 查找出来 的对象[与数据库表对应]
 * </pre>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @email <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2018/9/13 12:24
 * @version 1.0.0
 * </pre>
 */
public abstract class BaseConvertBo<P, T> implements IBo<P, T>, Serializable {
    /** 要返回的集合 */
//    protected List<P> params;

    /**
     * <h4> T 对象转成 P 对象<h4>
     *
     * @param info 对象
     * @return P 对象
     */
    public abstract P compat(T info);

    /**
     * <h4> P 对象转成 T 对象<h4>
     *
     * @param info 对象
     * @return T 对象
     */
    public abstract T convert(P info);

    /**
     * <h4>[T -> P]多个数据 转换{集合}</h4>
     *
     * @param list 集合
     * @return 有数据 为true
     */
    public List<P> adapter(List<T> list) {

        List<P> params = Lists.newArrayList();
        if (Lists.isNotEmptyList(list)) {
            for (T t : list) {
                params.add(compat(t));
            }
        }
        return params;
    }

    /**
     * <h4>[P -> T]多个数据 转换{集合}</h4>
     *
     * @param list 集合
     * @return 有数据 为true
     */
    public List<T> reverse(List<P> list) {
        List<T> params = Lists.newArrayList();
        if (Lists.isNotEmptyList(list)) {
            for (P p : list) {
                params.add(convert(p));
            }
        }
        return params;
    }


    /**
     * <h4>时间格式转换: 字符串时间 转 10 位时间数字</h4>
     *
     * @param date 字符串时间
     * @return 10 位时间数字
     */
    public static Integer convertStrToInt(String date) {
        return DateUtils.dateToTimestamp(date);
    }

    /**
     * <h4>时间格式转换: 10 位时间数字 转 字符串时间</h4>
     *
     * @param time 10 位时间数字
     * @return 字符串时间
     */
    public static String convertIntToStr(Integer time) {
        return DateUtils.timestampToStrDate(time);
    }
}
