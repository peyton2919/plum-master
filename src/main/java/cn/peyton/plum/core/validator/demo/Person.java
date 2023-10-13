package cn.peyton.plum.core.validator.demo;

import cn.peyton.plum.core.validator.constraints.*;

/**
 * <h3>范例类</h3>
 * <pre>
 * @email <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2018/11/16 16:05
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
public class Person {

    private Integer id;

    @NotNull(message = "名称不能为空!")
    @Length(min = 0,max = 10, message = "名称长度最大为10个字符!")
    private String name;
    @Size(min = 1,max = 120,message = "年龄只能在1-120岁之间")
    private Integer age;
    @Date
    private String create;
    @Email
    private String email;

    @Telephone
    private String tel;
    @Phone
    private String phone;
    @Length(min = 0,max = 12,message = "备注长度为12字符之内!")
    private String remarks;

    public Person() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
