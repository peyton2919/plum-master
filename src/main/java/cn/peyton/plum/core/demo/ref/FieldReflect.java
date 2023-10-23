package cn.peyton.plum.core.demo.ref;

import cn.peyton.plum.core.demo.ref.ann.FieldAnnotation;

import java.lang.reflect.Field;

/**
 * <h4></h4>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.plum.core.demo.ref.FieldReflect
 * @date 2023年10月23日 19:31
 * @version 1.0.0
 * </pre>
 */
public class FieldReflect {
    /**
     * 属性操作
     * @param r
     */
    public static void field(Class<?> r) {
        try {
            //获取指定属性
            Field id = r.getDeclaredField("id");
            //获取属性名
            System.out.println(id.getName());
            //获取属性类型
            System.out.println(id.getType().getName());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        //获取所有属性
        Field[] fields = r.getDeclaredFields();
        for (Field field : fields) {
            //获取属性上面指定的指定的注解
            FieldAnnotation fieldAnnotation = field.getAnnotation(FieldAnnotation.class);
            //获取注解属性值
            System.out.println(fieldAnnotation.value());
        }
    }

}
