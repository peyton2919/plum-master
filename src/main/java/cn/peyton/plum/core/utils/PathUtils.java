package cn.peyton.plum.core.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.io.Serializable;

/**
 * <h3>路径工具类</h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @create date: 2018/11/16 16:00
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
public final class PathUtils implements Serializable {
    static String seperator = System.getProperty("file.separator");
    /**
     * <h4>获取路径</h4>
     * @param location 位置名
     * @param request request对象
     * @return
     */
    public final static String getPath(String location, HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath(location);
    }

    /**
     * <h4>图片上传时的路径名称转换</h4>
     * @param location 图片保存的上级根目录名称
     * @param name 图片原始名称
     * @param dataPath 保存到数据库中的图片路径
     * @param completePath 完整的图片路径
     */
    public final static void convertPath(HttpServletRequest request, String location, String name,
                                         StringBuilder dataPath , StringBuilder completePath) {
        if (null == name || null == dataPath || null == completePath) {
            return;
        }
        //获取扩展名
        String ext = NameUtils.getImageExtendName(name);
        //获取当前 完成路径
        String path = request.getSession(false).getServletContext().getRealPath(location);
        String uuidName = UUIDUtils.createUUID();
        dataPath.append(location);
        dataPath.append(uuidName + ext);
        //完整 路径
        completePath.append(path);
        completePath.append(uuidName + ext);
    }

    /**
     * <h4>获取完整文件路径</h4>
     * @param name 名称
     * @param request 请求对象
     * @return 完成路径
     */
    public final static String getCompletePath(String name, HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath(name);
    }

    /**
     * 获取根目录地址
     * win 系统存在 d:/projectdev/images/;
     * 其他系统存在 /home/projectdev/images/;
     * @return
     */
    public final static String getImgBasePath() {
        //获取操作系统名称
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = "d:/projectdev/images/";
        }else {
            basePath = "/home/projectdev/images/";
        }
        basePath = basePath.replace("/", seperator);
        return basePath;
    }

    /**
     * <h4>获取 到target class的目录</h4>
     * @return
     */
    public final static String getTargetClass() {
        //ResourceUtils.getURL("classpath:").getPath()
        return Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }

    /**
     * <h3>获取项目的图片地址</h3>
     * @param id 编号
     * @return
     */
    public final static <K> String getProjectImagePath(K id) {
        String imagePath = "upload/item/shop/" + id + "/";
        return imagePath.replace("/", seperator);
    }
}
