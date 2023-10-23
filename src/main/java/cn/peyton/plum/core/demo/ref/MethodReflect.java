package cn.peyton.plum.core.demo.ref;

import cn.peyton.plum.core.demo.ref.ann.MethodAnnotation;
import cn.peyton.plum.core.demo.ref.ann.ParameterAnnotation;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * <h4></h4>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.plum.core.demo.ref.MethodReflect
 * @date 2023年10月23日 19:29
 * @version 1.0.0
 * </pre>
 */
public class MethodReflect {

    /**
     *方法操作
     * @param r
     */
    public static void method(Class<?> r) {
        //获取所有方法
        Method[] methods = r.getDeclaredMethods();
        for (Method method : methods) {
            //获取方法上指定的注解
            MethodAnnotation methodAnnotation = method.getAnnotation(MethodAnnotation.class);
            System.out.println(methodAnnotation.value());
            //获取方法名
            System.out.println(method.getName());
            //获取返回值类型
            System.out.println(method.getReturnType().getName());
            //获取各个形参数名及类型
            Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters) {
                System.out.println(parameter.getName());
                System.out.println(parameter.getType().getName());
                //获取参数的注解
                ParameterAnnotation annotation = parameter.getAnnotation(ParameterAnnotation.class);
                if (null != annotation) {
                    System.out.println(annotation.value());
                }

            }
        }
    }
}
