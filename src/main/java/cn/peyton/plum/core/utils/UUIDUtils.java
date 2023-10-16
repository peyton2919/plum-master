package cn.peyton.plum.core.utils;

import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

/**
 * <h3>UUID 工具类</h3>
 * @Title: SharedMethodUtils.java 
 * @Package cn.peyton.wanzibaidai.core.common 
 * @ClassName: SharedMethodUtils  
 * @author Peyton_YU
 * @date 2017年9月4日 下午8:21:27
 */
public abstract class UUIDUtils implements Serializable {
	/**
	 * <h4>生成UUID</h4>
	 * @return 32位字符串
	 */
	public static String createUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replaceAll("-", "");
	}
	/**
	 * <h4>生成20位字符串,当前系统时间+7位不固定位置字母</h4>
	 * @return  20位字符串
	 */
	public static String createCurrentTimeAndRandom() {
		return _createTimeAndRandom(7);
	}
	/**
	 * <h4>生成最少16位字符串</h4>
	 * @param exp 最小1,小于1就默认为1
	 * @return 最少 16位字符串
	 */
	public static String createCurrentTimeAndRandom(int exp) {
		return _createTimeAndRandom(exp);
	}
	/**
	 * <h4>创建随机英文名称</h4>
	 * <p>默认长度为10</p>
	 * @return 名称字符串
	 */
	public static String creatRandomName(){
		return creatRandomName(10);
	}
	/**
	 * <h4>创建随机英文名称</h4>
	 * @param length 名称长
	 * @return 名称字符串
	 */
	public static String creatRandomName(int length) {
		if(length < 8) length = 8;
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int temp = random.nextInt(48);
			sb.append(cs[temp]);
		}
		return sb.toString();
	}
	
	
	/**
	 * <h4>生成最少16位字符串</h4>
	 * @param exp 最小1,小于1就默认为1
	 * @return 最少 16位字符串
	 */
	private static String _createTimeAndRandom(int exp) {
		
		Random random = new Random();
		String str = System.currentTimeMillis()+"";
		int[] l = new int[exp<0?0:exp];
		for (int i = 0; i < l.length; i++) {
			l[i]=random.nextInt(48);
		}
		StringBuilder sb = new StringBuilder(str);
		for (int i = 0; i < l.length; i++) {
			sb.insert(random.nextInt(14+i), cs[l[i]]);
		}
		return sb.toString();
	}
	
	private final static char[] cs = {'a','b','c','d','e','f','g','h','j','k','l','p','o','i','u','y',
			't','r','s','w','q','z','x','v','n','m','Q','W','E','R','T','Y',
			'U','I','O','P','M','N','B','V','C','X','Z','A','S','D','F','G','H','J','K','L'};
}
