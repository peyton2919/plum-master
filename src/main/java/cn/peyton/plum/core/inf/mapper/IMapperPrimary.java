package cn.peyton.plum.core.inf.mapper;

import cn.peyton.plum.core.page.PageQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <h4>mapper 主要几个扩展</h4>
 * <pre>
 *     1. 分页查询(含模糊查找)
 *          selectByAllOrKeyword(pageQuery,keyword);
 *          count(keyword)
 *          pageQuery 分页对象, keyword 模糊查找关键字, 当keyword = null 时为全部查询;
 *          selectByKeyword 与 count 成对出现;
*      2. 简单重名判断
 *        isRename(id,name);
 *     3. 状态更新
 *         upStatus(id,status);
 * </pre>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.plum.core.inf.mapper.IMapperPrimary
 * @date 2023年10月13日 21:20
 * @version 1.0.0
 * </pre>
 */
public interface IMapperPrimary<K , T> {

    /**
     * <h4>分页查询(或模糊查找)</h4>
     * @param page 分页对象
     * @param keyword 关键字, 当keyword = null 时为全部查询
     * @return 对象集合
     */
    List<T> selectByAllOrKeyword(@Param("page") PageQuery page,@Param("keyword") String keyword);

    /**
     * <h4>查找全部数量</h4>
     * @param keyword 关键字, 当keyword = null 时为全部查询
     * @return 总条数
     */
    int count(@Param("keyword") String keyword);

    /**
     * <h4>判断是否重名</h4>
     * @param id 编号
     * @param name 名称
     * @return 大于0 表示 重名
     */
    int isRename(@Param("id") K id, @Param("name") String name);

    /**
     * <h4>更新状态</h4>
     * @param id 主键
     * @param status 状态值
     * @return 受影响行数
     */
    int upStatus(@Param("id") K id, @Param("status") Integer status);
}
