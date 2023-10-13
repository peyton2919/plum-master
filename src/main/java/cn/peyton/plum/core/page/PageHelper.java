package cn.peyton.plum.core.page;

import java.io.Serializable;

/**
 * <h3>基础分页工具类,定义一些分页</h3>
 * <pre>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2018/11/16 14:54
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
public class PageHelper implements Serializable {

	/** @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/** 当前页码 ,默认1 */
	private int currentPage = 1;
	/** 总行数  */
	private int totalRows;
	/** 每页记录数,默认12  */
	private int pageRecorders = 12;
	/** 参数属性(多个参数属性用','隔开)  */
	private String paramProperties;
	/**  参数值(多个参数值用','隔开)  */
	private Object paramValues;
	/**
	 * <h4> 当前页码,默认1</h4>
	 * @return 当前页码,默认1
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	/**
	 * <h4>当前页码,默认1</h4>
	 * @param currentPage 当前页码,默认1
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	/**
	 * <h4>总行数</h4>
	 * @return 总行数
	 */
	public int getTotalRows() {
		return totalRows;
	}
	/**
	 * <h4>总行数</h4>
	 * @param totalRows 总行数
	 */
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	/**
	 * <h4>每页记录数,默认12</h4>
	 * @return 每页记录数,默认12
	 */
	public int getPageRecorders() {
		return pageRecorders;
	}
	/**
	 * <h4>每页记录数,默认12</h4>
	 * @param pageRecorders 每页记录数,默认12
	 */
	public void setPageRecorders(int pageRecorders) {
		this.pageRecorders = pageRecorders;
	}
	/**
	 * <h4>参数属性(多个参数属性用','隔开)</h4>
	 * @return 参数属性(多个参数属性用','隔开)
	 */
	public String getParamProperties() {
		return paramProperties;
	}
	/**
	 * <h4>参数属性(多个参数属性用','隔开)</h4>
	 * @param paramProperties 参数属性(多个参数属性用','隔开)
	 */
	public void setParamProperties(String paramProperties) {
		this.paramProperties = paramProperties;
	}
	/**
	 * <h4> 参数值(多个参数值用','隔开)</h4>
	 * @return 参数值(多个参数值用','隔开)
	 */
	public Object getParamValues() {
		return paramValues;
	}
	/**
	 * <h4> 参数值(多个参数值用','隔开)</h4>
	 * @param paramValues 参数值(多个参数值用','隔开)
	 */
	public void setParamValues(Object paramValues) {
		this.paramValues = paramValues;
	}
	
}
