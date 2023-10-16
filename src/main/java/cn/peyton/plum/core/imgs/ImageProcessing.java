package cn.peyton.plum.core.imgs;

import cn.peyton.plum.core.utils.LogUtils;
import cn.peyton.plum.core.utils.UUIDUtils;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


/**
 * <h3>图片处理类</h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @project name: plum
 * @class name: cn.peyton.plum.core.img.ImageProcessing.java
 * @create date: 2021/11/18 21:56
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
public final class ImageProcessing  implements Serializable {

    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random RANDOM = new Random();

    /**
     * <h3>将CommonsMultipartFile转换成File</h3>
     * @param file
     * @return
     */
    public static File transferCommonsMultipartFileToFile(MultipartFile file) {
        File newFile = new File(file.getOriginalFilename());
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            LogUtils.error(e.getMessage());
        }
        return newFile;
    }

    /**
     * <h3>处理缩略图，并返回新生成图片的相对值路径</h3>
     * <h4>适用于spring文件上传</h4>
     * @param thumbnailInputStream 上传文件流
     * @param fileName 文件名
     * @param targetAddr 存放目标地址
     * @param watermarkName 水印名称{文件存在resources下}
     * @return
     */
    public static String execute(InputStream thumbnailInputStream,String fileName,
                                 String targetAddr,String watermarkName) {
        return _execute(thumbnailInputStream,fileName, targetAddr,watermarkName, Positions.BOTTOM_RIGHT,
                0.8f, 0.25f, 200, 200);
    }

    /**
     * <h3>图片转换</h3>
     * @param thumbnailInputStream 文件对象
     * @param fileName 文件名
     * @param targetAddr 文件存储目标路径
     * @param watermarkName 水印名称{文件存在resources下}
     * @param width 图片宽度
     * @param height 图片高度
     * @return
     */
    public static String execute(InputStream thumbnailInputStream,String fileName,
                                 String targetAddr, String watermarkName, int width, int height) {
        return _execute(thumbnailInputStream,fileName, targetAddr,watermarkName, Positions.BOTTOM_RIGHT,
                0.8f, 0.25f, width, height);
    }

    /**
     * <h3>图片转换</h3>
     * @param thumbnailInputStream 文件对象
     * @param fileName 文件名称
     * @param targetAddr 文件存储目标路径
     * @param watermarkName 水印名称{文件存在resources下}
     * @param positions 图片处理的位置 {Positions.BOTTOM_RIGHT}
     * @param outputQuality 输出质量{0~1f}之间
     * @param opacity 不透明度{0~1f}之间
     * @param width 图片宽度
     * @param height 图片高度
     * @return
     */
    public static String execute(InputStream thumbnailInputStream,String fileName, String targetAddr,String watermarkName, Positions positions,
                                  float outputQuality, float opacity,int width,int height) {
        return _execute(thumbnailInputStream,fileName, targetAddr,watermarkName, positions, outputQuality, opacity, width, height);
    }

    /**
     * <h3>图片转换</h3>
     * @param thumbnailInputStream 文件对象
     * @param fileName
     * @param targetAddr 文件存储目标路径
     * @param watermarkName 水印名称{文件存在resources下}
     * @param positions 图片处理的位置 {Positions.BOTTOM_RIGHT}
     * @param outputQuality 输出质量{0~1f}之间
     * @param opacity 不透明度{0~1f}之间
     * @param width 图片宽度
     * @param height 图片高度
     * @return
     */
    private static String _execute(InputStream thumbnailInputStream,String fileName,
                                   String targetAddr,String watermarkName, Positions positions,
                                   float outputQuality, float opacity,int width,int height) {
        // if(null == watermarkName || watermarkName.length() == 0){
        //     watermarkName = "watermark.png";
        // }
        //生成随机名
        String realFileName = UUIDUtils.createCurrentTimeAndRandom();
        //扩展名
        String extension = getFileExtension(fileName);
        //创建目录
        makeDirPath(targetAddr);
        //相对路径
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(relativeAddr);

        try {
            if (null != watermarkName) {
                Thumbnails.of(thumbnailInputStream).size(width, height)
                        .watermark(positions, ImageIO.read(new File(basePath + "/" + watermarkName)), opacity)
                        .outputQuality(outputQuality)
                        .toFile(dest);
            } else {
                Thumbnails.of(thumbnailInputStream).size(width, height)
                        .outputQuality(outputQuality)
                        .toFile(dest);
            }
            System.out.println("图片上传的路径: " + relativeAddr);
        } catch (IOException e) {
            LogUtils.error(e.getMessage());
        }
        // return relativeAddr;
        return realFileName + extension;
    }


    /**
     * 创建目标路径所涉及到的目录
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        File dirPath = new File(targetAddr);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 获取输入文件流的扩展名
     * @param fileName 文件名称
     * @return
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成随机文件名，当前年月日小时分钟秒钟 + 五位随机数
     * @return
     */
    private static String getRandomFileName() {
        //获取随机的五位数
        int rannum = RANDOM.nextInt(89999) + 10000;
        String nowTimeStr = FORMAT.format(new Date());
        return nowTimeStr + rannum;
    }
}
