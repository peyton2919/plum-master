package cn.peyton.plum.core.json;

import cn.peyton.plum.core.utils.LogUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

/**
 * <h3>Json 操作类</h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @project name: plum
 * @class name: cn.peyton.plum.core.base.JsonMapper.java
 * @create date: 2021/11/3 23:38
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
public final class JsonMapper implements Serializable {

    public static ObjectMapper objectMapper;

    /**
     * json对象转JAVA对象
     *
     * @param jsonStr
     * @param valueType
     * @return
     */
    public static <T> T readValue(String jsonStr, Class<T> valueType) {

        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        try {

            return objectMapper.readValue(jsonStr, valueType);
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        }

        return null;
    }

    /**
     * json数组转List
     * @param jsonStr
     * @param valueTypeRef
     * @return
     */
    public static <T> T readValue(String jsonStr, TypeReference<T> valueTypeRef) {

        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        try {
            return objectMapper.readValue(jsonStr, valueTypeRef);
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        }

        return null;
    }

    /**
     * 把JavaBean转换为json字符串
     *
     * @param object
     * @return
     */
    public static String toJSon(Object object) {

        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        }
        return null;
    }
}
