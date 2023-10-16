package cn.peyton.plum.core.utils;

import java.io.Serializable;

/**
 * <h3>名称工具类<</h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @create date: 2018/11/16 15:59
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
public final class NameUtils implements Serializable {

    /**
     * <h4>名称转换成UUID</h4>
     * @param fileName 旧文件名
     * @return 新文件名
     */
    public static String convert(String fileName) {
        String ext = fileName.substring(fileName.lastIndexOf("."));
        return UUIDUtils.createUUID() + ext;
    }

    /**
     * <h4>获取 图片名</h4>
     * @param str 图片路径 如: xxx/xxx/aaa.jpg 获取到[aaa.jpg]
     * @return 图片名
     */
    public static String getImageName(String str) {
        if (str == null || "".equals(str)) {
            return "";
        } else {
            return str.substring(str.lastIndexOf("/") +1);
        }
    }

    /**
     * <H4>获取图片扩展名 .png</H4>
     * @param name 图片名
     * @return 扩展名 [.ext]
     */
    public static String getImageExtendName(String name) {
        if (null == name || "".equals(name)) {
            return ".jpg";
        } else {
            return name.substring(name.lastIndexOf("."));
        }
    }

}
