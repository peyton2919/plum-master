package cn.peyton.plum.core.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <h3>分页展示返回类</h3>
 * <pre>
 * @email <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @create date: 2018/11/16 14:27
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
public final class PageResult<T>  implements Serializable {
    /** 返回数据 集合 */
    private List<T> data = new ArrayList<T>();
    /** 总条数 */
    private int total = 0;

    //================================== Constructor =======================================//

    /**
     * <h4>构造函数</h4>
     */
    public PageResult() { }

    /**
     * <h4>构造函数</h4>
     * @param data 返回数据 集合
     * @param total 总条数
     */
    public PageResult(List<T> data, int total) {
        this.data = data;
        this.total = total;
    }

    /**
     * <h4>赋值</h4>
     * @param data 数据
     * @param total 总条数
     */
    public void set(List<T> data, int total) {
        this.data = data;
        this.total = total;
    }

    //================================== GET AND SET =======================================//

    /**
     * @return 返回数据 集合
     */
    public List<T> getData() {
        return data;
    }

    /**
     * @param data 返回数据 集合
     */
    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * @return 总条数
     */
    public int getTotal() {
        return total;
    }

    /**
     * @param total 总条数
     */
    public void setTotal(int total) {
        this.total = total;
    }

}
