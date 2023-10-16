package cn.peyton.plum.core.cache.base;

import java.util.LinkedHashMap;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <h3>缓存容器 , 实现 LRU策略的 LinkedHashMap</h3>
 * 
 * @Title: LRULinkedHashMap.java
 * @Package cn.peyton.plum.tools.cache
 * @ClassName: LRULinkedHashMap
 * @author Peyton_YU
 * @date 2017年9月5日 下午6:41:48
 */
public class LRULinkedHashMap<K , T> extends LinkedHashMap<K, T> {

	protected static final long serialVersionUID = 2828675280716975892L;
	/** 默认最大的记录数  */
	protected static final int DEFAULT_MAX_ENTRIES = 100;
	/** 初始容量  */
	protected final int initialCapacity;
	/**  最大容量  */
	protected final int maxCapacity;
	/** 是否允许自动移除比较旧的元素(添加元素时)  */
	protected boolean enableRemoveEldestEntry = true;// 
	/** 默认加载因子  */
	protected static final float DEFAULT_LOAD_FACTOR = 0.8f;
	/** 再生锁 */
	protected final Lock lock = new ReentrantLock();
	/**
	 * <h4>构造函数</h4>
	 * @param initialCapacity 初始容量
	 */
	public LRULinkedHashMap(int initialCapacity) {
		this(initialCapacity, DEFAULT_MAX_ENTRIES);
	}
	/**
	 * <h4>构造函数</h4>
	 * @param initialCapacity 初始容量
	 * @param maxCapacity 最大容量
	 */
	public LRULinkedHashMap(int initialCapacity, int maxCapacity) {
		// set accessOrder=true, LRU
		super(initialCapacity, DEFAULT_LOAD_FACTOR, true);
		this.initialCapacity = initialCapacity;
		this.maxCapacity = maxCapacity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedHashMap#removeEldestEntry(java.util.Map.Entry)
	 */
	protected boolean removeEldestEntry(java.util.Map.Entry<K, T> eldest) {
		return enableRemoveEldestEntry && (size() > maxCapacity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedHashMap#get(java.lang.Object)
	 */
	public T get(Object key) {
		try {
			lock.lock();
			return super.get(key);
		} finally {
			lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
	 */
	public T put(K key, T value) {
		try {
			lock.lock();
			return super.put(key, value);
		} finally {
			lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#remove(java.lang.Object)
	 */
	public T remove(Object key) {
		try {
			lock.lock();
			return super.remove(key);
		} finally {
			lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedHashMap#clear()
	 */
	public void clear() {
		try {
			lock.lock();
			super.clear();
		} finally {
			lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#keySet()
	 */
	public Set<K> keySet() {
		try {
			lock.lock();
			return super.keySet();
		} finally {
			lock.unlock();
		}
	}

	public boolean isEnableRemoveEldestEntry() {
		return enableRemoveEldestEntry;
	}

	public void setEnableRemoveEldestEntry(boolean enableRemoveEldestEntry) {
		this.enableRemoveEldestEntry = enableRemoveEldestEntry;
	}

	public int getInitialCapacity() {
		return initialCapacity;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

}
