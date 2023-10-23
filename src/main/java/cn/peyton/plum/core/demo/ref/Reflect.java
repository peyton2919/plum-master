package cn.peyton.plum.core.demo.ref;

import cn.peyton.plum.core.demo.ref.ann.*;

/**
 * <h4></h4>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.plum.core.demo.ref.Reflect
 * @date 2023年10月23日 18:29
 * @version 1.0.0
 * </pre>
 */
@ClassAnnotation("反射")
public class Reflect {

    @FieldAnnotation("姓名")
    private String name;

    @FieldAnnotation("id")
    private Integer id;

    @ConstructorAnnotation("无参构造")
    private Reflect(){

    }

    @ConstructorAnnotation("含参构造")
    private Reflect(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    @MethodAnnotation("公共方法获取实例")
    public static Reflect getReflect(){
        return new Reflect();
    }

    @MethodAnnotation("获取name")
    public String getName() {
        return name;
    }

    @MethodAnnotation("设置name")
    public void setName(@ParameterAnnotation("姓名") String name) {
        this.name = name;
    }

    @MethodAnnotation("获取id")
    public Integer getId() {
        return id;
    }

    @MethodAnnotation("设置id")
    public void setId(Integer id) {
        this.id = id;
    }
}
