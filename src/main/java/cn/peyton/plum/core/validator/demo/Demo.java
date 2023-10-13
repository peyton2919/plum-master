package cn.peyton.plum.core.validator.demo;

import cn.peyton.plum.core.validator.Validation;

import java.util.Map;

/**
 * <h3>注解验证 Demo类</h3>
 * <pre>
 * @email <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2018/11/16 16:05
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
public class Demo {
    public static void main(String[] args) {
        Person person = new Person();
        person.setCreate("2005/02/30");
        person.setRemarks("1234567890123456");
        Map<String, String> map = Validation.valid(person);
        System.out.println(map.toString());
    }
}
