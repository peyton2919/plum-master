package cn.peyton.plum.core.page;

import cn.peyton.plum.core.inf.BaseConvertBo;
import cn.peyton.plum.core.inf.mapper.IMapperPrimary;
import cn.peyton.plum.core.utils.base.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * <h3>封装 返回对象适配器 类</h3>
 * <pre>
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @projectName: lemon
 * @packageName: cn.peyton.spring.beans.ResultAdapter.java
 * @createDate: 2018/9/15 9:40
 * @version: 1.0.0
 * </pre>
 */
public final class PageResultAdapter<T> implements Serializable {

    public PageResultAdapter() { }

    /**
     * <h4>查找 全部 对象集合的适配器</h4>
     * @param mapper 查找 全部对象Mapper接口
     * @param page 分页对象
     * @param baseConvertBo 实现基础BO对象
     * @param <T> 需要操作对象
     * @return PageResult对象
     */
    public static <T> PageResult<T> adapt(IMapperPrimary mapper, PageQuery page,String keyword, BaseConvertBo baseConvertBo) {
        PageResult<T> result = new PageResult<T>();
        int count = mapper.count(keyword);
        if (count > 0) {
            result.setTotal(count);
            result.setData(baseConvertBo.adapter(mapper.selectByAllOrKeyword(page,keyword)));
        }
        return result;
    }


    /**
     * <h4>封装数据</h4>
     * @param data 对象集合
     * @param count 条数
     * @param <T> 需要操作对象
     * @return
     */
    public static <T> PageResult<T> adapt(List<T> data, Integer count) {
        PageResult<T> result= new PageResult<T>();
        if (Lists.isNotEmptyList(data)) {
            result.setData(data);
            result.setTotal(count);
        }
        return result;
    }

    /**
     * <h4>返回一个空的PageResult</h4>s
     * @param <T> 需要操作对象
     * @return
     */
    public static <T> PageResult<T> empty() {
        return new PageResult<T>();
    }


}
