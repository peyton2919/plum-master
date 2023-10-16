package cn.peyton.plum.core.utils.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <h3></h3>
 * <p>
 * <pre>
 * Author: <a href="http://www.peyton.cn">peyton</a>
 * Email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * ProjectName: lemon
 * PackageName: cn.peyton.spring.util.Lists.java
 * Create: 2018-08-19 21:14
 * Version: 1.0.0
 * </pre>
 */

/**
 * <h3>List 工具集</h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @create date: 2018/11/16 15:58
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
public final class Lists<T,P>implements Serializable {

    /**
     * <h4>判断list集合是否为空或者list集合大小为0</h4>
     * @param list 集合
     * @param <T>
     * @return 为true时,list集合为空或大小为0
     */
    public static <T> boolean isEmpty(List<T> list){
        if (null == list || list.size() < 1) {
            return true;
        }
        return false;
    }

    /**
     * <h4>判断list集合是否为空或者list集合大小为0</h4>
     * @param list 集合
     * @param <T>
     * @return 为true时,list集合为空
     */
    public static <T> boolean isNull(List<T> list){
        if (null == list) {
            return true;
        }
        return false;
    }

    /**
     * <h4>判断List 集合是否为空</h4>
     * @param list 集合
     * @return 不为空返回true
     */
    public static boolean isNotEmptyList(List<?> list) {
        if (null != list && list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * <h4>创建ArrayList对象</h4>
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<T>();
    }

    /**
     * <h4>创建ArrayList list 为空时创建</h4>
     * @param list
     */
    public static <T> void createArrayList(List<T> list) {
        if (null == list) {
            list = new ArrayList<T>();
        }
    }


    /**
     * <h4>创建LinkedList对象</h4>
     * @param <T>
     * @return
     */
    public static <T> LinkedList<T> newLinkedList() {
        return new LinkedList<T>();
    }

    /**
     * <h4>创建LinkedList list 为空时创建</h4>
     * @param list
     */
    public static <T> void createLinkedList(List list) {
        if (null == list) {
            list = new LinkedList();
        }
    }



}
