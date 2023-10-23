package cn.peyton.plum.core.demo.ref;

import cn.peyton.plum.core.demo.ref.ann.ClassAnnotation;

import java.lang.annotation.Annotation;

/**
 * <h4></h4>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.plum.core.demo.ref.ClassReflect
 * @date 2023年10月23日 19:30
 * @version 1.0.0
 * </pre>
 */
public class ClassReflect {

    /**
     * 类操作
     * @param r
     */
    public static void class1(Class<?> r) {
        //获取全限定类名
        r.getName();
        //获取包名
        r.getPackage().getName();
        //获取类上所有注解
        Annotation[] annotations = r.getAnnotations();
        //获取类上指定的注解
        ClassAnnotation classAnnotation =r.getAnnotation(ClassAnnotation.class);
        //注解的全限定类名
        String aClass = classAnnotation.annotationType().getName();
        //判断是否有指定注解
        boolean b = r.isAnnotationPresent(ClassAnnotation.class);
    }

}
