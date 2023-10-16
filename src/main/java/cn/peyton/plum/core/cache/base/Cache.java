package cn.peyton.plum.core.cache.base;

import java.util.List;
import java.util.Map;

/**
 * <h3>缓存接口</h3>
 * 
 * @Title: Cache.java
 * @ClassName: Cache
 * @author Peyton_YU
 * @date 2017年9月5日 下午2:15:24
 * @param <K>
 * @param <T>
 */
public interface Cache<K , T> {
	/** 清理过期元素 迭代模式 为 0 */
	public static final int EXPIRED_REMOVE_ITERATE = 0;
	/** 清理过期元素随机模式 为 1 */
	public static final int EXPIRED_REMOVE_RANDOM = 1;

	/**
	 * <h4>获取全部缓存缓存</h4>
	 * 
	 * @return Map<K, T>类型数据
	 */
	Map<K, T> getAll();

	/**
	 * <h4>获取指定key缓存</h4>
	 * 
	 * @param key
	 *            key
	 * @return 缓存
	 */
	T get(K key);

	/**
	 * <h4>添加缓存</h4>
	 * 
	 * @param key
	 *            key
	 * @param t
	 *            缓存
	 */
	void put(K key, T t);

	/**
	 * <h4>添加缓存</h4>
	 * 
	 * @param key
	 *            key
	 * @param t
	 *            缓存
	 * @param milliSecond
	 *            缓存生命周期(毫秒)
	 */
	void put(K key, T t, Long milliSecond);

	/**
	 * 缓存容器中是否包含 key
	 * 
	 * @param key
	 * @return
	 */
	boolean containsKey(K key);

	/**
	 * <h4>添加多个缓存集合</h4>
	 * 
	 * @param map
	 *            添加多个缓存集合
	 */
	void putAll(Map<K, T> map);

	/**
	 * <h4>移除 指定key缓存</h4>
	 * 
	 * @param key
	 *            key
	 */
	void remove(K key);

	/**
	 * 缓存列表大小
	 * 
	 * @return
	 */
	int size();

	/**
	 * 是否启用缓存
	 */
	boolean isEnabled();

	/**
	 * 启用 或 停止
	 * 
	 * @param enabled
	 */
	void setEnabled(boolean enabled);

	/**
	 * 移除所有缓存
	 */
	void removeAll();
	/**
	 * <h4>Map类型转成List类型</h4>
	 * @return
	 */
	List<T> toList();
	/**
	 * <h4>List集合转成Map集合,默认为 'id"</h4>
	 * @param lists
	 */
	void toMap(List<T> lists);
	/**
	 * <h4>List集合转成Map集合</h4>
	 * @param lists 集合
	 * @param fieldNameKey 集合里对象的属性名 如 'id'...
	 */
	void toMap(List<T> lists, String fieldNameKey);
}
