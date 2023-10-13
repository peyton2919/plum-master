package cn.peyton.plum.core.inf.service;

import cn.peyton.plum.core.page.PageQuery;
import cn.peyton.plum.core.page.PageResult;

/**
 * <h3>公共Service接口 .</h3>
 * <pre>
 *     参数K P T 用法
 *     K 表示 主键数据 类型
 *     P 表示 入参时验证用的对象[用在页面的param]
 *     T表示 查找出来 的对象[与数据库表对应]
 * </pre>
 * <pre>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2018/11/16 15:31
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
public interface IService<K,P> {
    /**
     * <h4>添加对象</h4>
     * @param param 对象
     */
    P save(P param);

    /**
     * <h4>更新对象</h4>
     * @param param 对象
     */
//    @Transactional(propagation = Propagation.REQUIRED)
    Boolean update(P param);
    /**
     * <h4>根据主键删除对象</h4>
     * @param id 主键
     */
    Boolean delete(K id);
    /**
     * <h4>根据主键查找对象</h4>
     * @param id 主键
     * @return 对象
     */
    P findById(K id);

    /**
     * <h4>查找对象集合</h4>
     * @param page 分页对象
     * @return 对象集合
     */
    PageResult<P> findByAll(PageQuery page);
}
