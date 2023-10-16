package cn.peyton.plum.mall.service;

import cn.peyton.plum.mall.pojo.Stu;

import java.util.List;

/**
 * <h4></h4>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.plum.mall.service.StuService
 * @date 2023年10月14日 12:13
 * @version 1.0.0
 * </pre>
 */
public interface StuService {

    Stu add(Stu stu);

    List<Stu> finds();

    Stu findById(Integer id);

}
