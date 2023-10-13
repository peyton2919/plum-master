package cn.peyton.plum.core.utils.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <h3>Map 工具集</h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @create date: 2018/11/16 15:58
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
public final class Maps implements Serializable{

    /**
     * <h4>判断map集合是否为空</h4>
     * @param map 集合
     * @param <K, V>
     * @return 为true时,map集合为空
     */
    public static <K, V> boolean isNull(Map<K, V> map) {
        if (null == map) {
            return true;
        }
        return false;
    }


    /**
     * <h4>创建LinkHashMap</h4>
     * @param map
     */
    public static <K, V> void createLinkHashMap(Map<K, V> map) {
        if (null == map){
            map = new LinkedHashMap<K, V>();
        }
    }

    /**
     * <h4>创建LinkHashMap</h4>
     */
    public static <K, V> Map<K, V> createLinkHashMap() {
        return new LinkedHashMap<K, V>();
    }

    /**
     * <h4>创建HashMap</h4>
     * @param map
     */
    public static <K, V> void createHashMap(Map<K, V> map) {
        if (null == map){
            map = new HashMap<K, V>();
        }
    }

    /**
     * <h4>创建HashMap</h4>
     */
    public static <K, V> Map<K, V> createHashMap() {
        return new HashMap<K, V>();
    }

//    public static <K, V> V convert(Map<K, V> map) {
//        List<V> vs = map.en;
//    }
}
