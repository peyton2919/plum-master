package cn.peyton.plum.core.config;

/**
 * <h4>路径位置</h4>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.plum.core.config.Location
 * @date 2023年10月14日 23:30
 * @version 1.0.0
 * </pre>
 */
public interface Location {
    /** @return 获取 class目录 */
    static String getLocation() {
        return Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }

    /** 产品图片 存放位置 */
    String PATH_IMG_PRODUCT = getLocation()+"static/images/product/";
    /** 广告图片 存放位置 */
    String PATH_IMG_AD = getLocation()+"static/images/ad/";
    /** 头像图片 存放位置 */
    String PATH_IMG_AVATAR = getLocation()+"static/images/avatar/";

}
