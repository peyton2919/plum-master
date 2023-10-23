package cn.peyton.plum.core.demo.ref;

import cn.peyton.plum.core.demo.StuDemo;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * <h4></h4>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.plum.core.demo.ref.TestReflect
 * @date 2023年10月23日 18:26
 * @version 1.0.0
 * </pre>
 */
public class TestReflect {
    public static void main(String[] args) {
        try {
            //获取字节码对象
            Class<?> r= Class.forName("cn.peyton.plum.core.demo.ref.Reflect");
            //类操作
            ClassReflect.class1(r);

            //属性操作
            FieldReflect.field(r);

            //方法操作
            MethodReflect.method(r);

            //构造方法方法
            ConstructorReflect.constructor(r);

            //反射获取值
            StuDemo demo = new StuDemo(1, "Judy", new Date());
            Class<?> clazz = demo.getClass();
            Field _field = clazz.getDeclaredField("name");
            _field.setAccessible(true);
            Object _nameObj = _field.get(demo);
            System.out.println("反射获取值: " + _nameObj);

            // 反射赋值
            System.out.println("获取Demo对象name值: " + demo.getName());

            _field.set(demo,"Tom");
            System.out.println("反射赋值后取值: " + demo.getName());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
