package cn.peyton.plum.core.cache.base;

/**
 * <h3>缓存元素</h3>
 * 
 * @Title: CacleElement.java
 * @Package cn.peyton.wanzibaidai.ext.cache
 * @ClassName: CacleElement
 * @author Peyton_YU
 * @date 2017年9月5日 下午6:39:43
 */
public class CacheElement<T> {
	/** 键  */
	private Object key;
	/** 值  */
	private T value;
	/** 创建时间(当前时间Long类型)  */
	private long createTime;
	/** 生存时间  */
	private long lifeTime;
	/**  使用度 */
	private int hitCount;
	/** 构造函数  */
	public CacheElement() {}
	/**
	 * <h4></h4>
	 * @param key
	 * @param value
	 */
	public CacheElement(Object key, T value) {
		this.key = key;
		this.value = value;
		this.createTime = System.currentTimeMillis();
	}
	/**
	 * <h4></h4>
	 * @return
	 */
	public Object getKey() {
		return key;
	}
	/**
	 * <h4></h4>
	 * @param key
	 */
	public void setKey(Object key) {
		this.key = key;
	}
	/**
	 * <h4></h4>
	 * @return
	 */
	public Object getValue() {
		hitCount++;
		return value;
	}
	
	/**
	 * <h4></h4>
	 * @param value
	 */
	public void setValue(T value) {
		this.value = value;
	}
	/**
	 * <h4></h4>
	 * @return
	 */
	public long getCreateTime() {
		return createTime;
	}
	/**
	 * <h4></h4>
	 * @param createTime
	 */
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	/**
	 * <h4></h4>
	 * @return
	 */
	public int getHitCount() {
		return hitCount;
	}
	/**
	 * <h4></h4>
	 * @param hitCount
	 */
	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}
	/**
	 * <h4></h4>
	 * @return
	 */
	public long getLifeTime() {
		return lifeTime;
	}
	/**
	 * <h4></h4>
	 * @param lifeTime
	 */
	public void setLifeTime(long lifeTime) {
		this.lifeTime = lifeTime;
	}
	/**
	 * 	<h4>是否失效 , 失效为true , 否则为false ; 单位毫秒</h4>
	 * <h5>用当前时间 - 创建时间 > 存活时间 {System.currentTimeMillis() - getCreateTime() > getLifeTime();}</h5>
	 * <h5>1分钟 = 1 * 60 * 1000 毫秒</h5>
	 * @return
	 */
	public boolean isExpired() {
		boolean isExpired = System.currentTimeMillis() - getCreateTime() > getLifeTime();
		return isExpired;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[ key=").append(key).append(", isExpired=").append(isExpired()).append(", lifeTime=")
				.append(lifeTime).append(", createTime=").append(createTime).append(", hitCount=").append(hitCount)
				.append(", value=").append(value).append(" ]");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public final int hashCode() {
		if (null == key) {
			return "".hashCode();
		}
		return this.key.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public final boolean equals(Object object) {
		if ((object == null) || (!(object instanceof CacheElement))) {
			return false;
		}

		CacheElement<T> element = (CacheElement<T>) object;
		if ((this.key == null) || (element.getKey() == null)) {
			return false;
		}

		return this.key.equals(element.getKey());
	}

}
