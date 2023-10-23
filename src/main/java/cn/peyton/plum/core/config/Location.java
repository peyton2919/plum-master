package cn.peyton.plum.core.config;

import java.io.Serializable;

/**
 * <h4>路径位置</h4>
 * <pre>
 *     图片目录:
 *     获取到完成目录用来图片写入磁盘时用：(1~3)
 *     1. IMG_PRODUCT 产品图片目录
 *     2. IMG_ADVERT  广告图片目录
 *     3. IMG_AVATAR  头像目录
 *     设置简短的图片目录 拼接 自动生成的图片名称,最终保存到数据库：(4~6)
 *     4. IMG_PRODUCT_SHORT  产品图片
 *     5. IMG_ADVERT_SHORT   广告图片
 *     6. IMG_AVATAR_SHORT   头像
 *
 * </pre>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.plum.core.config.Location
 * @date 2023年10月14日 23:30
 * @version 1.0.0
 * </pre>
 */
public final class Location implements Serializable {

    /** @return 获取 class目录 */
    private static String path;

    private static Location instance = null;

    /** 私有构造 */
    private Location() {
        path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }

    public static synchronized Location getInstance(){
        if (null == instance) {
            instance = new Location();
        }
        return instance;
    }

    /**
     * <h4>获取 class目录</h4>
     * @return
     */
    public String getPath() {
        return path;
    }

    /** 产品图片 存放位置 图片写入磁盘时用 {完整目录x:/xx/xx/images/product/} */
    public static String IMG_PRODUCT = Location.getInstance().getPath() +"static/images/product/";
    /** 广告图片 存放位置 图片写入磁盘时用 {完整目录x:/xx/xx/images/advert/} */
    public static String IMG_ADVERT = Location.getInstance().getPath()+"static/images/advert/";
    /** 头像图片 存放位置 图片写入磁盘时用 {完整目录x:/xx/xx/images/avatar/} */
    public static String IMG_AVATAR = Location.getInstance().getPath()+"static/images/avatar/";

    /** 产品图片 根目录 简短目录名保存到数据库 {/images/product/} */
    public static String IMG_PRODUCT_SHORT = "/images/product/";
    /** 广告图片 根目录 简短目录名保存到数据库 {/images/advert/} */
    public static String IMG_ADVERT_SHORT = "/images/advert/";
    /** 头像图片 根目录 简短目录名保存到数据库 {/images/avatar/} */
    public static String IMG_AVATAR_SHORT = "/images/avatar/";


    // 内部类
    //private static class LocationHolder{
    //    private static final Location instance = new Location();
    //}

    //public static Location getInstance() {
    //    return LocationHolder.instance;
    //}
    /** @return 获取 class目录 */
    //static String getLocation() {
    //    return Thread.currentThread().getContextClassLoader().getResource("").getPath();
    //}
}
