package cn.peyton.plum.core.utils;

import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

/**
 * <h3>唯一码 类 .</h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @create date: 2018/11/16 16:03
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
public final class UniqueCodeUtils implements Serializable {

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
    public static String createRandomName(){
        return createRandomName(10);
    }
    /**
     * <h4>创建随机英文名称</h4>
     * @param length 名称长
     * @return 名称字符串
     */
    public static String createRandomName(int length) {
        if(length < 8) length = 8;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int temp = random.nextInt(48);
            sb.append(CHARS[temp]);
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
        int[] l = new int[exp < 0 ? 0 : exp];
        for (int i = 0; i < l.length; i++) {
            l[i]=random.nextInt(48);
        }
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; i < l.length; i++) {
            sb.insert(random.nextInt(14+i), CHARS[l[i]]);
        }
        return sb.toString();
    }

    private final static char[] CHARS = {'a','b','c','d','e','f','g','h','j','k','l','p','o','i','u','y',
            't','r','s','w','q','z','x','v','n','m','Q','W','E','R','T','Y',
            'U','I','O','P','M','N','B','V','C','X','Z','A','S','D','F','G','H','J','K','L'};

    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            System.out.println(UUID.randomUUID());
        }
    }
}
