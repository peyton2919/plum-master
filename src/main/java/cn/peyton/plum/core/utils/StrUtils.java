package cn.peyton.plum.core.utils;

import cn.peyton.plum.core.err.ValidationException;
import cn.peyton.plum.core.json.JSONResult;
import cn.peyton.plum.core.page.ResponseStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * <h3>字符串 工具类</h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @create date: 2018/11/16 16:02
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
public final class StrUtils implements Serializable {

    private final static String CONNECTOR = ",";

    /**
     * 判断字符串是否为空
     * @param str 字符串
     * @return true 为空, 否则 取反
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断是否空格
     * @param str 字符串
     * @return true 为空, 否则 取反
     */
    public static boolean isBlank(String str) {
        int strLen = length(str);
        if (strLen == 0) {
            return true;
        } else {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * <h4>为空判断,长度必需大于0</h4>
     * @param obj 字符串
     * @return 不为空返回true
     */
    public static boolean isEmpty(Object obj) {
        if (null != obj && !"".equals(obj.toString().trim())) {
            return true;
        }
        return false;
    }

    /**
     * <h4>为空判断</h4>
     * @param obj 字符串
     * @return 不为空返回true
     */
    public static boolean isNull(Object obj) {
        if (null != obj) {
            return true;
        }
        return false;
    }

    /**
     * <h4>检查对象如果为空,抛出 ValidationException 异常</h4>
     * @param obj
     * @param message
     */
    public static void checkNoNull(Object obj, String message) {
        if (null == obj) {
            throw new ValidationException(JSONResult.error(ResponseStatus.VALIDATE_FAIL,message));
        }
    }

    /**
     * 获取字符串长度
     * @param str 字符串
     * @return 长度
     */
    public static int length(String str) {
        return str == null ? 0 : str.length();
    }

    /**
     * 字符连接
     * @param elements 字符集
     * @param <T>
     * @return
     */
    public static <T> String join(T... elements) {
        return join((Object[])elements, (String)null);
    }

    /**
     * 字符连接
     * @param array 字符串数组
     * @param delimiter 分隔符
     * @return
     */
    public static String join(Object[] array, String delimiter) {
        return array == null ? null : join((Object[])array, delimiter, 0, array.length);
    }

    /**
     * 字符连接
     * @param array 
     * @param delimiter
     * @param startIndex
     * @param endIndex
     * @return
     */
    public static String join(Object[] array, String delimiter, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else if (endIndex - startIndex <= 0) {
            return "";
        } else {
            StringJoiner joiner = new StringJoiner(toStringOrEmpty(delimiter));

            for(int i = startIndex; i < endIndex; ++i) {
                joiner.add(toStringOrEmpty(array[i]));
            }

            return joiner.toString();
        }
    }

    /**
     * <h4>字符串 转换成 Integer 集合</h4>
     *
     * @param str
     * @return
     */
    public static List<Integer> splitToListInt(String str) {
//        List<String> strList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(str);
//        List<Integer> list = strList.stream().map(strItem -> Integer.parseInt(strItem)).collect(Collectors.toList());

        return null;
    }

    /**
     * <h4>字符串 转换成 Integer 集合</h4>
     *
     * @param str
     * @return
     */
    public static List<Long> splitToListLong(String str) {
//        VerifyCodeUtils v;
//        List<String> strList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(str);
//        List<Long> list = strList.stream().map(strItem -> Long.parseLong(strItem)).collect(Collectors.toList());
        return null;
    }

    /**
     * <h4>Ip 为0:0:0:0:0:0:0:1 转成 127.0.0.1</h4>
     *
     * @param ip
     * @return
     */
    public static String convertIp(String ip) {
        if (ip != null && !"".equals(ip.trim())) {
            if ("0:0:0:0:0:0:0:1".equals(ip.trim())) {
                return "127.0.0.1";
            }
        }
        return ip;
    }

    /**
     * <h4>字符串 转换成 String 集合</h4>
     * @param str  字符串
     * @return
     */
    public static List<String> splitToListString(String str) {
        if (null == str) {
            return null;
        }
//        return Splitter.on(CONNECTOR).trimResults().omitEmptyStrings().splitToList(str);
        return null;
    }

    /**
     * <h4>字符串 转换成 String 集合[图片集合]</h4>
     * @param str 字符串
     * @return
     */
    public static List<String> splitImagesStr2List(String str) {
        if (null == str) {
            return null;
        }
        String[] splits = str.split(",");

        int length = splits.length;
        if (length <= 2){return  null;}
        String prefix = splits[0];
        length = length - 1;
        String suffix = splits[length];
        List<String> results = new ArrayList<>();
        for (int i = 1; i < length; i++) {
            String temp = prefix + splits[i] + suffix;
            results.add(temp);
        }
        return results;
    }

    /**
     * <h4>获取图片集合 最大数值</h4>
     * @param str
     * @return
     */
    public static Integer getImagesMaxValue(String str) {
        if (null == str) {
            return null;
        }
        String[] splits = str.split(",");
        int length = splits.length;
        if (length <= 2){return  0;}
        return Integer.parseInt(splits[length - 2]);
    }

    /**
     * <h4>字符串集合 转成字符串</h4>
     * @param lists 字符串集合
     * @return
     */
    public static String convertList2Str(List<String> lists) {
        int size = lists.size();
        if (null != lists && size > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                sb.append(lists.get(i));
                if (i != size - 1) {
                    sb.append(CONNECTOR);
                }
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * <h4>字符串集合 转成字符串</h4>
     * @param lists 字符串集合
     * @return
     */
    public static String convertList2Str(String... lists) {
        int size = lists.length;
        if (null != lists && size > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                sb.append(lists[i]);
                if (i != size - 1) {
                    sb.append(CONNECTOR);
                }
            }
            return sb.toString();
        }
        return null;
    }
    /**
     * <h4>字符串集合 转成字符串</h4>
     * @param lists 字符串集合
     * @return
     */
    public static String convertList2Str(Object... lists) {
        int size = lists.length;
        if (null != lists && size > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                sb.append(lists[i]);
                if (i != size - 1) {
                    sb.append(CONNECTOR);
                }
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * <h4>截取字符串长度</h4>
     * @param value 字符串
     * @param begin 开始的位置
     * @param end 结束的位置
     * @return 截取后的字符串
     */
    public static String substring(String value, int begin, int end) {
        if (null == value || "".equals(value) || (value.length() <= end - begin)) {
            return value;
        }
        return value.substring(begin, end);
    }


    private static String toStringOrEmpty(Object obj) {
        return Objects.toString(obj, "");
    }

}
