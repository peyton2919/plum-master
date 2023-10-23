package cn.peyton.plum.core.demo.ref;

import cn.peyton.plum.core.demo.ref.ann.ConstructorAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

/**
 * <h4></h4>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.plum.core.demo.ref.ConstructorReflect
 * @date 2023年10月23日 19:32
 * @version 1.0.0
 * </pre>
 */
public class ConstructorReflect {

    /**
     * 构造方法操作
     * @param r
     */
    public static void constructor(Class<?> r) {
        //获取所有构造方法
        Constructor<?>[] constructors = r.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            //获取构造方法上面指定的注解
            ConstructorAnnotation constructorAnnotation = constructor.getAnnotation(ConstructorAnnotation.class);
            //获取注解的属性值
            if ("含参构造".equals(constructorAnnotation.value())){
                //获取各个参数名
                Parameter[] parameters = constructor.getParameters();
                for (Parameter parameter : parameters) {
                    System.out.println(parameter.getName());
                }
                //获取各个参数类型
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                for (Class<?> parameterType : parameterTypes) {
                    System.out.println(parameterType.getName());
                }
                //获取参数数量
                System.out.println(constructor.getParameterCount());
            }
        }
    }

}
