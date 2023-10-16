package cn.peyton.plum.mall.mapper;

import cn.peyton.plum.mall.pojo.Stu;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <h4></h4>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.plum.mall.mapper.StuMapper
 * @date 2023年10月14日 12:07
 * @version 1.0.0
 * </pre>
 */
public interface StuMapper {

    @Insert("insert into stu (name,create_time) values(#{name},#{createTime})")
    int add(Stu stu);

    @Select("select * from stu")
    List<Stu> finds();

    @Select("select * from stu where id=#{id}")
    Stu findById(Integer id);
}
