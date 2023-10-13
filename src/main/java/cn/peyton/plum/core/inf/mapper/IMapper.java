package cn.peyton.plum.core.inf.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * <h3>Mapper公共接口 .</h3>
 * <pre>
 *     参数K T V 用法
 *     K 表示 主键数据 类型
 *     T 表示 查找出来 的对象[与数据库表对应]
 * </pre>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @create date: 2018/11/16 15:26
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
@SuppressWarnings("ALL")
@Mapper
public interface IMapper<K , T> {
    /**
     * <h4>插入 T对象</h4>
     * @param record 权限对象
     * @return 受影响的行数
     */
    int insert(T record);

    /**
     * <h4>插入 T对象[根据属性是否有值 插入]</h4>
     * @param record 权限对象
     * @return 受影响的行数
     */
    int insertSelective(T record);

    /**
     * <h4>更新 T对象</h4>
     * @param record 权限对象
     * @return 受影响的行数
     */
    int updateByPrimaryKey(T record);

    /**
     * <h4>更新 权限对象[根据属性是否有值 更新]</h4>
     * @param record 权限对象
     * @return 受影响的行数
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * <h4>根据 ID 删除 T对象</h4>
     * @param id 权限ID
     * @return 受影响的行数
     */
    int deleteByPrimaryKey(K id);

    /**
     * <h4>根据 ID 查找 T对象</h4>
     * @param id 权限ID
     * @return 权限对象
     */
    T selectByPrimaryKey(K id);

}
