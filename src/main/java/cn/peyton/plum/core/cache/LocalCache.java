package cn.peyton.plum.core.cache;

import cn.peyton.plum.core.cache.base.Cache;
import cn.peyton.plum.core.cache.base.CacheElement;
import cn.peyton.plum.core.cache.base.LRULinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * <h3>本地缓存组件</h3>
 * <pre>
 *      本地缓存默认5分钟;
 * </pre>
 * @Title: LocalCache.java 
 * @Package cn.peyton.plum.tools.cache
 * @ClassName: LocalCache  
 * @author Peyton_YU
 * @date 2017年9月5日 下午6:46:01
 */
public class LocalCache<K ,T> implements Cache<K , T> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/** 存放LRU对象集合  */
	private LRULinkedHashMap<K, CacheElement<T>> cacheMap;
	/** 初始化标识 为'false' */
	protected boolean initFlag = false;// 
	/** 默认存活时间 '5' 分钟  */
	protected final long defaultLifeTime = 5 * 60 * 1000;// 5分钟
	/** 警告比较时间 , 默认为 'false' */
	protected boolean warnLongerLifeTime = false;
	/**  默认初始容量 为  '100' */
	protected final int DEFAULT_INITIAL_CAPACITY = 100;
	/** 默认最大容量为 '100000'{约10MB}  */
	protected final int DEFAULT_MAX_CAPACITY = 100000;
	/** 初始化缓存容量  */
	protected int initialCapacity = DEFAULT_INITIAL_CAPACITY;// 
	/**  最大缓存容量  */
	protected int maxCapacity = DEFAULT_MAX_CAPACITY;//
	/**  存取缓存操作响应超时时间(毫秒数) '20' */
	protected int timeout = 20;// 
	/** 开启缓存 , 默认开启 为  'true'  */
	private boolean enabled = true;
	/** GC线程 (垃圾回收机制线程) */
	private Thread gcThread = null;
	/** 最后一次GC清理信息{ size, removeCount, time ,nowTime}  */
	private String lastGCInfo = null;// 
	/** 记录GC清理细节为  'false'  */
	private boolean logGCDetail = false;// 
	/** 是否允许清理的缓存(添加元素时) 'true' */
	private boolean enableGC = true;// 
	/** 清理过期元素模式 { 0=迭代模式 ; 1=随机模式 } , 默认为 '0'  */
	private int gcMode = 0;// 
	/** 间隔时间(2分钟{2 * 60 * 1000})  */
	private int gcIntervalTime = 2 * 60 * 1000;// 
	/** 是否迭代扫描全部  'true' */
	private boolean iterateScanAll = true;// 
	/**  GC清理百分比 , 默认为 '0.5F' */
	private float gcFactor = 0.5F;// 
	/** 迭代模式下一次最大迭代数量  */
	private int maxIterateSize = DEFAULT_MAX_CAPACITY / 2;// 
	/** 最后迭代下标 转为 , 默认为   '0' */
	private volatile int iterateLastIndex = 0;// 
	/** 随机模式下最大随机次数 , 默认为 '100'  */
	private int maxRandomTimes = 100;// 
	/** 随机数  */
	protected final static Random random = new Random();
	/** 本地缓存对象  */
	@SuppressWarnings("rawtypes")
	private static LocalCache instance = new LocalCache();

	
	/**
	 * <h4>初始化LocalCache对象{单立}</h4>
	 * @return 初始化LocalCache对象{单立}
	 */
	@SuppressWarnings("rawtypes")
	public static LocalCache getInstance() {
		return instance;
	}
	/** <h4>私有构造函数</h4> */
	private LocalCache() {
		this.init();
	}
	
	/** <h4>同步</h4>*/
	protected synchronized void init() {
		if (initFlag) {
			logger.warn("异常: 重复初始化");
			return;
		}
		//初始化缓存
		this.initCache();
		//开启清除缓存线程
		this.startGCDaemonThread();
        // 初始化 标识 设为 true
		initFlag = true;

		if (logger.isInfoEnabled()) {
			logger.info("初始化 --> 成功");
		}
	}
	
	/** <h4>初始化缓存</h4> */
	private void initCache() {
	    // 判断 初始化 标记 为 true 表示 已经初始化,不需要在初始化,return返回
		if (initFlag) { return; }
		//初始容量赋值
		initialCapacity = (initialCapacity <= 0 ? DEFAULT_INITIAL_CAPACITY : initialCapacity);
		//最大容量赋值
		maxCapacity = (maxCapacity < initialCapacity ? DEFAULT_MAX_CAPACITY : maxCapacity);
		//创建LRULinkedHashMap对象
		cacheMap = new LRULinkedHashMap<K, CacheElement<T>>(initialCapacity, maxCapacity);
		//写日志
		if (logger.isInfoEnabled()) {
			logger.info("初始化缓存 --> 成功");
		}
	}

	/** <h4>开始GC(清除)缓存线程</h4> */
	private void startGCDaemonThread() {
	    // 判断 初始化 标记 为 true 表示 已经初始化,不需要在初始化,return返回
		if (initFlag) { return; }
        // 设置最大的重复数量
		this.maxIterateSize = maxCapacity / 2;
		try {
			this.gcThread = new Thread() {
				public void run() {
					logger.info("[" + (Thread.currentThread().getName()) + "]start...");
					// sleep
					try {
						Thread.sleep(getGcIntervalTime() < 30000 ? 30000 : getGcIntervalTime());
					} catch (Exception e) {
						e.printStackTrace();
					}
					while (true) {
						// gc
						gc();
						// sleep
						try {
							Thread.sleep(getGcIntervalTime() < 30000 ? 30000 : getGcIntervalTime());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			};
			this.gcThread.setName("localCache-gcThread");
			this.gcThread.setDaemon(true);
			this.gcThread.start();

			if (logger.isInfoEnabled()) {
				logger.info("startGCDaemonThread -- OK");
			}
		} catch (Exception e) {
			logger.error("[localCache gc]DaemonThread -- error: " + e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@SuppressWarnings({"unchecked" })
	@Override
	public T get(K key) {
		if (!isEnabled()) {
			return null;
		}
		long st = System.currentTimeMillis();

		T objValue = null;
		CacheElement<T> cacheObj = cacheMap.get(key);

		if (isExpiredCache(cacheObj)) {//判断是否期满 , 期满移除
			cacheMap.remove(key);
		} else {
			objValue = (T) (cacheObj == null ? null : cacheObj.getValue());
			if (null != cacheObj) {
				cacheObj.setCreateTime(System.currentTimeMillis());
				cacheObj.setHitCount(cacheObj.getHitCount() + 1);
			}
			
		}

		long et = System.currentTimeMillis();
		if ((et - st) > timeout) {
			if (this.logger.isWarnEnabled()) {
				this.logger.warn("getCache_timeout_" + (et - st) + "_[" + key + "]");
			}
		}

		if (logger.isDebugEnabled()) {
			String message = ("get( " + key + ") return: " + objValue);
			logger.debug(message);
		}
		return objValue;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void put(K key, T value, Long lifeTime) {
		if (!isEnabled()) {
			return;
		}
		//开始时间
		Long st = System.currentTimeMillis();

		lifeTime = (null == lifeTime ? defaultLifeTime : lifeTime);
		CacheElement<T> cacheObj = new CacheElement<T>();
		cacheObj.setCreateTime(System.currentTimeMillis());
		cacheObj.setLifeTime(lifeTime);
		cacheObj.setValue(value);
		cacheObj.setKey(key);
		cacheMap.put(key, cacheObj);
		//结束时间
		long et = System.currentTimeMillis();
		if ((et - st) > timeout) {
			if (this.logger.isWarnEnabled()) {
				this.logger.warn("putCache_timeout_" + (et - st) + "_[" + key + "]");
			}
		}

		if (logger.isDebugEnabled()) {
			String message = ("putCache( " + cacheObj + " ) , 耗时 " + (et - st) + "(毫秒).");
			logger.debug(message);
		}
		if (lifeTime > defaultLifeTime && this.isWarnLongerLifeTime()) {
			if (logger.isWarnEnabled()) {
				String message = ("LifeTime[" + (lifeTime / 1000) + "秒] too long for putCache(" + cacheObj + ")");
				logger.warn(message);
			}
		}
	}

	/**
	 * key 是否过期
	 * 
	 * @param key
	 * @return
	 */
	protected boolean isExpiredKey(Object key) {
		CacheElement<T> cacheObj = cacheMap.get(key);
		return this.isExpiredCache(cacheObj);
	}

	/**
	 * cacheObj 是否过期
	 * 
	 * @param cacheObj
	 * @return
	 */
	protected boolean isExpiredCache(CacheElement<T> cacheObj) {
		if (cacheObj == null) {
			return false;
		}
		return cacheObj.isExpired();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void removeAll() {
		try {
			cacheMap.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 */
	
	@Override
	public void remove(Object key) {
		try {
			cacheMap.remove(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public boolean containsKey(Object key) {
		return cacheMap.containsKey(key);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public int size() {
		return cacheMap.size();
	}

	
	public Iterator<K> getKeyIterator() {
		return cacheMap.keySet().iterator();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		if (!this.enabled) {
			// 清理缓存
			this.removeAll();
		}
	}

	/**
	 * 清理过期缓存
	 */
	protected synchronized boolean gc() {
		if (!isEnableGC()) {
			return false;
		}
		try {
			//设置清除缓存 方式 为 0 迭代清除缓存 ; 否则为随机清除缓存 
			if (getGcMode() == 0) {
				iterateRemoveExpiredCache();
			}else {
				randomRemoveExpiredCache();
			}
		} catch (Exception e) {
			logger.error("gc() has error: " + e.getMessage(), e);
		}
		return true;
	}

	/**
	 * 迭代模式 - 移除过期的 key
	 *
	 */
	private void iterateRemoveExpiredCache() {
		long startTime = System.currentTimeMillis();

		int size = cacheMap.size();
		if (size == 0) {
			return;
		}

		int keyCount = 0;
		int removedCount = 0;

		int startIndex = 0;
		int endIndex = 0;

		try {
			Object[] keys = cacheMap.keySet().toArray();
			keyCount = keys.length;
			int maxIndex = keyCount - 1;

			// 初始化扫描下标
			if (iterateScanAll) {
				startIndex = 0;
				endIndex = maxIndex;
			} else {
				int gcThreshold = this.getGcThreshold();
				int iterateLen = gcThreshold > this.maxIterateSize ? this.maxIterateSize : gcThreshold;

				startIndex = this.iterateLastIndex;
				startIndex = ((startIndex < 0 || startIndex > maxIndex) ? 0 : startIndex);
				endIndex = (startIndex + iterateLen);
				endIndex = (endIndex > maxIndex ? maxIndex : endIndex);
			}

			// 迭代清理
			boolean flag = false;
			for (int i = startIndex; i <= endIndex; i++) {
				flag = this.removeExpiredKey(keys[i]);//
				if (flag) {
					removedCount++;
				}
			}

			this.iterateLastIndex = endIndex;
			keys = null;

		} catch (Exception e) {
			logger.error("iterateRemoveExpiredCache -- 移除过期的 key时出现异常: " + e.getMessage(), e);
		}

		long endTime = System.currentTimeMillis();

		StringBuffer sb = new StringBuffer();
		sb.append("iterateRemoveExpiredCache [ size: ").append(size).append(", keyCount: ").append(keyCount)
				.append(", startIndex: ").append(startIndex).append(", endIndex: ").append(iterateLastIndex)
				.append(", removedCount: ").append(removedCount).append(", currentSize: ").append(this.cacheMap.size())
				.append(", timeConsuming: ").append(endTime - startTime).append(", nowTime: ").append(new Date())
				.append(" ]");
		this.lastGCInfo = sb.toString();

		if (logger.isInfoEnabled()) {
			logger.info("iterateRemoveExpiredCache -- 清理结果 -- " + lastGCInfo);
		}
	}

	/**
	 * 随机模式 - 移除过期的 key
	 */
	private void randomRemoveExpiredCache() {
		long startTime = System.currentTimeMillis();

		int size = cacheMap.size();
		if (size == 0) {
			return;
		}

		int removedCount = 0;
		try {
			Object[] keys = cacheMap.keySet().toArray();
			int keyCount = keys.length;

			boolean removeFlag = false;

			int removeRandomTimes = this.getGcThreshold();

			removeRandomTimes = (removeRandomTimes > this.getMaxRandomTimes() ? this.getMaxRandomTimes()
					: removeRandomTimes);
			while (removeRandomTimes-- > 0) {

				int index = random.nextInt(keyCount);
				boolean flag = this.removeExpiredKey(keys[index]);
				if (flag) {
					removeFlag = true;
					removedCount++;
				}
			}
			// 尝试 移除 首尾元素
			if (!removeFlag) {
				this.removeExpiredKey(keys[0]);
				this.removeExpiredKey(keys[keyCount - 1]);
			}
			keys = null;

		} catch (Exception e) {
			logger.error("randomRemoveExpiredCache -- 移除过期的 key时出现异常: " + e.getMessage(), e);
		}
		long endTime = System.currentTimeMillis();

		StringBuffer sb = new StringBuffer();
		sb.append("randomRemoveExpiredCache [ size: ").append(size).append(", removedCount: ").append(removedCount)
				.append(", currentSize: ").append(this.cacheMap.size()).append(", timeConsuming: ")
				.append(endTime - startTime).append(", nowTime: ").append(new Date()).append(" ]");
		this.lastGCInfo = sb.toString();

		if (logger.isInfoEnabled()) {
			logger.info("randomRemoveExpiredCache -- 清理结果 -- " + lastGCInfo);
		}
	}
	/**
	 * <h4>移除期满 缓存</h4>
	 * @param key
	 * @return 移除成功为 'true' , 否则为 'false'
	 */
	private boolean removeExpiredKey(Object key) {
		boolean flag = false;

		CacheElement<T> cacheObj = null;
		if (null != key) {
			try {
				cacheObj = cacheMap.get(key);
				boolean isExpiredCache = this.isExpiredCache(cacheObj);//是否过期
				//过期移除 ,返回 'true' 否则 'false'
				if (isExpiredCache) {
					cacheMap.remove(key);
					flag = true;
				}
			} catch (Exception e) {
				logger.error("removeExpired(" + key + ") -- error: " + e.getMessage(), e);
			}
		}

		if (!flag && logGCDetail) {
			this.logger.warn("removeExpiredKey(" + key + ") return [" + flag + "]--" + cacheObj);
		}

		return flag;
	}
	
	
	//============================================ private method ===================================================//
	/**
	 * <h4>初始化缓存容量</h4>
	 * @return 初始化缓存容量
	 */
	public int getInitialCapacity() {
		return initialCapacity;
	}
	/**
	 * <h4>最大缓存容量</h4>
	 * @return 最大缓存容量
	 */
	public int getMaxCapacity() {
		return maxCapacity;
	}
	/**
	 * <h4>清理过期元素模式 { 0=迭代模式 ; 1=随机模式 }</h4>
	 * @return 清理过期元素模式 { 0=迭代模式 ; 1=随机模式 }
	 */
	public int getGcMode() {
		return gcMode;
	}
	/**
	 * <h4>清理过期元素模式 { 0=迭代模式 ; 1=随机模式 }</h4>
	 * @param gcMode 清理过期元素模式 { 0=迭代模式 ; 1=随机模式 }
	 */
	public void setGcMode(int gcMode) {
		this.gcMode = gcMode;
	}
	/**
	 * <h4>间隔时间(2分钟)</h4>
	 * @return 间隔时间(2分钟)
	 */
	public int getGcIntervalTime() {
		return gcIntervalTime;
	}
	/**
	 * <h4>间隔时间(2分钟)</h4>
	 * @param gcIntervalTime 间隔时间(2分钟)
	 */
	public void setGcIntervalTime(int gcIntervalTime) {
		this.gcIntervalTime = gcIntervalTime;
	}
	/**
	 * <h4>是否允许清理的缓存(添加元素时)</h4>
	 * @return 是否允许清理的缓存(添加元素时)
	 */
	public boolean isEnableGC() {
		return enableGC;
	}
	/**
	 * <h4>是否允许清理的缓存(添加元素时)</h4>
	 * @param enableGC 是否允许清理的缓存(添加元素时)
	 */
	public void setEnableGC(boolean enableGC) {
		this.enableGC = enableGC;
	}
	/**
	 * <h4>是否迭代扫描全部</h4>
	 * @return 是否迭代扫描全部
	 */
	public boolean isIterateScanAll() {
		return iterateScanAll;
	}
	/**
	 * <h4>是否迭代扫描全部</h4> 
	 * @param iterateScanAll 是否迭代扫描全部
	 */
	public void setIterateScanAll(boolean iterateScanAll) {
		this.iterateScanAll = iterateScanAll;
	}
	/**
	 * <h4> GC清理百分比</h4>
	 * @return  GC清理百分比
	 */
	public float getGcFactor() {
		return gcFactor;
	}
	/**
	 * <h4>GC清理百分比</h4>
	 * @param gcFactor  GC清理百分比
	 */
	public void setGcFactor(float gcFactor) {
		this.gcFactor = gcFactor;
	}
	/**
	 * <h4>GC 阀值</h4>
	 * @return GC 阀值
	 */
	public int getGcThreshold() {
		int threshold = (int) (this.cacheMap.getMaxCapacity() * gcFactor);
		return threshold;
	}
	/**
	 * <h4>最后一次GC清理信息{ size, removeCount, time ,nowTime}</h4>
	 * @return 最后一次GC清理信息{ size, removeCount, time ,nowTime}
	 */
	public String getLastGCInfo() {
		return lastGCInfo;
	}
	/**
	 * <h4>最后一次GC清理信息{ size, removeCount, time ,nowTime}</h4>
	 * @param lastGCInfo 最后一次GC清理信息{ size, removeCount, time ,nowTime}
	 */
	public void setLastGCInfo(String lastGCInfo) {
		this.lastGCInfo = lastGCInfo;
	}
	/**
	 * <h4>记录GC清理细节</h4>
	 * @return 记录GC清理细节
	 */
	public boolean isLogGCDetail() {
		return logGCDetail;
	}
	/**
	 * <h4>记录GC清理细节</h4>
	 * @param logGCDetail 记录GC清理细节
	 */
	public void setLogGCDetail(boolean logGCDetail) {
		this.logGCDetail = logGCDetail;
	}
	/**
	 * <h4>存取缓存操作响应超时时间(毫秒数) 20</h4>
	 * @return 存取缓存操作响应超时时间(毫秒数) 20
	 */
	public int getTimeout() {
		return timeout;
	}
	/**
	 * <h4>存取缓存操作响应超时时间(毫秒数) 20</h4>
	 * @param timeout 存取缓存操作响应超时时间(毫秒数) 20
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	/**
	 * <h4>迭代模式下一次最大迭代数量</h4>
	 * @return 迭代模式下一次最大迭代数量
	 */
	public int getMaxIterateSize() {
		return maxIterateSize;
	}
	/**
	 * <h4>迭代模式下一次最大迭代数量</h4>
	 * @param maxIterateSize 迭代模式下一次最大迭代数量
	 */
	public void setMaxIterateSize(int maxIterateSize) {
		this.maxIterateSize = maxIterateSize;
	}
	/**
	 * <h4>随机模式下最大随机次数</h4>
	 * @return 随机模式下最大随机次数
	 */
	public int getMaxRandomTimes() {
		return maxRandomTimes;
	}
	/**
	 * <h4>随机模式下最大随机次数</h4>
	 * @param maxRandomTimes 随机模式下最大随机次数
	 */
	public void setMaxRandomTimes(int maxRandomTimes) {
		this.maxRandomTimes = maxRandomTimes;
	}
	/**
	 * <h4>初始化标识</h4>
	 * @return 初始化标识
	 */
	public boolean isInitFlag() {
		return initFlag;
	}
	/**
	 * <h4>初始化标识</h4>
	 * @return 初始化标识
	 */
	public long getDefaultLifeTime() {
		return defaultLifeTime;
	}
	/**
	 * <h4>警告比较时间 , 默认为 false</h4>
	 * @return 警告比较时间 , 默认为 false
	 */
	public boolean isWarnLongerLifeTime() {
		return warnLongerLifeTime;
	}
	/**
	 * <h4>警告比较时间 , 默认为 false</h4>
	 * @param warnLongerLifeTime 警告比较时间 , 默认为 false
	 */
	public void setWarnLongerLifeTime(boolean warnLongerLifeTime) {
		this.warnLongerLifeTime = warnLongerLifeTime;
	}

	// ======================== dynMaxCapacity ========================
	/** 异步最大容量*/
	private int dynMaxCapacity = maxCapacity;
	/**
	 * <h4>异步最大容量</h4>
	 * @return  异步最大容量
	 */
	public int getDynMaxCapacity() {
		return dynMaxCapacity;
	}
	/**
	 * <h4> 异步最大容量</h4>
	 * @param dynMaxCapacity  异步最大容量
	 */
	public void setDynMaxCapacity(int dynMaxCapacity) {
		this.dynMaxCapacity = dynMaxCapacity;
	}
	/**
	 * <h4> 重置 最大容量</h4>
	 *
	 */
	public void resetMaxCapacity() {
		if (dynMaxCapacity > initialCapacity && dynMaxCapacity != maxCapacity) {

			if (logger.isInfoEnabled()) {
				logger.info("resetMaxCapacity( " + dynMaxCapacity + " ) start...");
			}

			synchronized (cacheMap) {
				LRULinkedHashMap<K, CacheElement<T>> cacheMap0 = new LRULinkedHashMap<K, CacheElement<T>>(
						initialCapacity, dynMaxCapacity);
				cacheMap.clear();
				cacheMap = cacheMap0;
				this.maxCapacity = dynMaxCapacity;
			}

			if (logger.isInfoEnabled()) {
				logger.info("resetMaxCapacity( " + dynMaxCapacity + " ) OK.");
			}
		} else {
			if (logger.isWarnEnabled()) {
				logger.warn("resetMaxCapacity( " + dynMaxCapacity + " ) NO.");
			}
		}
	}

	// ======================== showCacheElement ========================
	/** 显示缓存锁 */
	private String showCacheKey;
	/**
	 * <h4>显示缓存锁</h4>
	 * @return 显示缓存锁
	 */
	public String getShowCacheKey() {
		return showCacheKey;
	}
	/**
	 * <h4>显示缓存锁</h4>
	 * @param showCacheKey 显示缓存锁
	 */
	public void setShowCacheKey(String showCacheKey) {
		this.showCacheKey = showCacheKey;
	}
	/**
	 * <h4>显示容量元素</h4>
	 * @return 显示容量元素
	 */
	public Object showCacheElement() {
		Object v = null;

		if (null != this.showCacheKey) {
			v = (Object) cacheMap.get(showCacheKey);
		}
		return v;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<K , T> getAll() {
		Map<K, T> _maps = new HashMap<>();
		Iterator<Map.Entry<K, CacheElement<T>>>  _its = cacheMap.entrySet().iterator();	
		while (_its.hasNext()) {
			Map.Entry<K, CacheElement<T>> _self = _its.next();
			_maps.put(_self.getKey(), (T) _self.getValue().getValue());
		}

		return _maps;
	}
	@Override
	public void put(K key, T t) {
		put(key, t, defaultLifeTime);
	}

	@Override
	public void putAll(Map<K , T> map) {
		Iterator<Map.Entry<K, T>> _its = map.entrySet().iterator();
		while (_its.hasNext()) {
			Map.Entry<K, T> _self = _its.next();
			put(_self.getKey(), _self.getValue());
		}		
	}
	@Override
	public List<T> toList() {
		return new ArrayList<T>(getAll().values());
	}
	@Override
	public void toMap(List<T> lists) {
		toMap(lists, "id");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void toMap(List<T> lists, String fieldNameKey) {
		if (null != lists && lists.size() > 0) {
			try {
				T t = lists.get(0);
				PropertyDescriptor descriptor = new PropertyDescriptor(fieldNameKey, t.getClass());
				Method methodKey = descriptor.getReadMethod();
				for(T t2 : lists) {
					K _key = (K) methodKey.invoke(t2);
					put(_key, t2);
				}
			} catch (Exception e) {
				
			}
		}
	}
	
	
}
