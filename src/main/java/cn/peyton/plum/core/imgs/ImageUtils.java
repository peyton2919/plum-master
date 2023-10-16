package cn.peyton.plum.core.imgs;

import cn.peyton.plum.core.utils.LogUtils;
import cn.peyton.plum.core.utils.UUIDUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * 图片处理工具类
 *
 * @author peyton_yu
 * @version 1.0
 */
@SuppressWarnings("ALL")
public final class ImageUtils implements Serializable {
    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    private static final long MAX_IMAGE_VALUE = 2097152;
    /**
     * 几种常见的图片格式
     */
    public final static String IMAGE_TYPE_GIF = "gif";
    public final static String IMAGE_TYPE_JPG = "jpg";
    public final static String IMAGE_TYPE_JPEG = "jpeg";
    public final static String IMAGE_TYPE_BMP = "bmp";
    public final static String IMAGE_TYPE_PNG = "png";
    public final static String IMAGE_TYPE_PSD = "psd";

    // private static final long MAX_IMAGE_VALUE = 2097152;

    /**
     * 缩放图像（按比例缩放）
     *
     * @param srcImageFile  源图像文件地址
     * @param destImageFile 缩放后的图像地址
     * @param scale         缩放比例
     * @param flag          缩放选择:true 放大; false 缩小;
     */
    public static void scale(String srcImageFile, String destImageFile, int scale, boolean flag) {

        try {
            insideScale(ImageIO.read(new File(srcImageFile)), destImageFile, scale, flag);
        } catch (IOException e) {
            LogUtils.error(e.getMessage());
        }
    }

    /**
     * 缩放图像（按比例缩放）
     *
     * @param multipartFile        multipartFile对象
     * @param destImageFileAndName 缩放后的图像地址
     * @param scale                缩放比例
     * @param flag                 缩放选择:true 放大; false 缩小;
     */
    public static void scale(MultipartFile multipartFile, String destImageFileAndName, int scale, boolean flag) {

        try {
            insideScale(ImageIO.read(multipartFile.getInputStream()), destImageFileAndName, scale, flag);
        } catch (IOException e) {
            LogUtils.error(e.getMessage());
        }
    }

    /**
     * 缩放图像（按高度和宽度缩放）
     *
     * @param srcImageFile  源图像文件地址
     * @param destImageFile 缩放后的图像地址和名称
     * @param height        缩放后的高度
     * @param width         缩放后的宽度
     * @param bb            比例不对时是否需要补白：true为补白; false为不补白;
     */
    public static void scale(String srcImageFile, String destImageFile, int height, int width, boolean bb) {
        try {
            insideScale(ImageIO.read(new File(srcImageFile)), destImageFile, height, width, bb);
        } catch (IOException e) {
            LogUtils.error(e.getMessage());
        }
    }

    /**
     * 缩放图像（按高度和宽度缩放）
     *
     * @param multipartFile multipartFile对象
     * @param destImageFile 缩放后的图像地址和名称
     * @param height        缩放后的高度
     * @param width         缩放后的宽度
     * @param bb            比例不对时是否需要补白：true为补白; false为不补白;
     */
    public static void scale(MultipartFile multipartFile, String destImageFile, int height, int width, boolean bb) {
        try {
            insideScale(ImageIO.read(multipartFile.getInputStream()), destImageFile, height, width, bb);
        } catch (IOException e) {
            LogUtils.error(e.getMessage());
        }
    }

    /**
     * 定位图像切割(按指定起点坐标和宽高切割)
     *
     * @param srcImageFile  源图像地址名称
     * @param destImageFile 切片后的图像地址名称
     * @param x             目标切片起点坐标X
     * @param y             目标切片起点坐标Y
     * @param width         目标切片宽度
     * @param height        目标切片高度
     */
    public static void localizationShear(String srcImageFile, String destImageFile, int x, int y, int width,
                                         int height) {
        try {
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            // 源图宽度
            int srcWidth = bi.getWidth();
            // 源图高度
            int srcHeight = bi.getHeight();
            if (srcWidth > 0 && srcHeight > 0) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                // 四个参数分别为图像起点坐标和宽高
                // 即: CropImageFilter(int x,int y,int width,int height)
                ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
                Image img = Toolkit.getDefaultToolkit()
                        .createImage(new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                // 绘制切割后的图
                g.drawImage(img, 0, 0, width, height, null);
                g.dispose();
                // 输出为文件
                ImageIO.write(tag, IMAGE_TYPE_JPEG, new File(destImageFile));
            }
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        }
    }

    /**
     * 四边图像切割(按指定上、右、下、左 的大小剪切 )
     *
     * @param srcImageFile  源图像地址名称
     * @param destImageFile 切片后的图像地址名称
     * @param above         目标切片上边的大小
     * @param right         目标切片左边的大小
     * @param below         目标切片下边的大小
     * @param left          目标切片左边的大小
     */
    public static void quadrilateralShear(String srcImageFile, String destImageFile, int above, int right, int below,
                                          int left) {
        try {
            int width = 0, height = 0;
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            // 源图宽度
            int srcWidth = bi.getWidth();
            // 源图高度
            int srcHeight = bi.getHeight();
            if ((above + below) >= srcHeight || (right + left) >= srcWidth) {
                above = 0;
                right = 0;
                below = 0;
                left = 0;
                width = srcWidth;
                height = srcHeight;
            } else {
                width = srcWidth - right - left;
                height = srcHeight - above - below;
            }

            if (srcWidth > 0 && srcHeight > 0) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                // 四个参数分别为图像起点坐标和宽高
                // 即: CropImageFilter(int x,int y,int width,int height)
                ImageFilter cropFilter = new CropImageFilter(above, left, width, height);
                Image img = Toolkit.getDefaultToolkit()
                        .createImage(new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                // 绘制切割后的图
                g.drawImage(img, 0, 0, width, height, null);
                g.dispose();
                // 输出为文件
                ImageIO.write(tag, IMAGE_TYPE_JPEG, new File(destImageFile));
            }
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        }
    }

    /**
     * 给图片添加文字水印
     *
     * @param pressText     水印文字
     * @param srcImageFile  源图像地址
     * @param destImageFile 目标图像地址
     * @param fontName      水印的字体名称
     * @param fontStyle     水印的字体样式
     * @param color         水印的字体颜色
     * @param fontSize      水印的字体大小
     * @param x             修正值
     * @param y             修正值
     * @param alpha         透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public final static void pressText(String pressText, String srcImageFile, String destImageFile, String fontName,
                                       int fontStyle, Color color, int fontSize, int x, int y, float alpha) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 在指定坐标绘制水印文字
            g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);
            g.dispose();
            // 输出到文件流
            ImageIO.write((BufferedImage) image, IMAGE_TYPE_JPEG, new File(destImageFile));
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        }
    }

    /**
     * 给图片添加文字水印
     *
     * @param pressText     水印文字
     * @param srcImageFile  源图像地址
     * @param destImageFile 目标图像地址
     * @param fontName      字体名称
     * @param fontStyle     字体样式
     * @param color         字体颜色
     * @param fontSize      字体大小
     * @param x             修正值
     * @param y             修正值
     * @param alpha         透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public final static void pressText2(String pressText, String srcImageFile, String destImageFile, String fontName,
                                        int fontStyle, Color color, int fontSize, int x, int y, float alpha) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 在指定坐标绘制水印文字
            g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);
            g.dispose();
            ImageIO.write((BufferedImage) image, IMAGE_TYPE_JPEG, new File(destImageFile));
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        }
    }

    /**
     * 给图片添加图片水印
     *
     * @param pressImg      水印图片
     * @param srcImageFile  源图像地址
     * @param destImageFile 目标图像地址
     * @param x             修正值。 默认在中间
     * @param y             修正值。 默认在中间
     * @param alpha         透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public final static void pressImage(String pressImg, String srcImageFile, String destImageFile, int x, int y,
                                        float alpha) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);

            BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            // 水印文件
            Image srcBiao = ImageIO.read(new File(pressImg));
            int widethBiao = srcBiao.getWidth(null);
            int heightBiao = srcBiao.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g.drawImage(srcBiao, (wideth - widethBiao) / 2 + x, (height - heightBiao) / 2 + y, widethBiao,
                    heightBiao, null);
            // 水印文件结束
            g.dispose();
            ImageIO.write((BufferedImage) image, IMAGE_TYPE_JPEG, new File(destImageFile));
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        }
    }

    /**
     * @param multipartFile       multipartFile对象
     * @param outFilePath         图片输出路径
     * @param ratio               输出图片比率(小数)
     * @param isChangeOutFileName 是否改变输出的文件名,true为改变输出文件名,false为不改变输出文件名;
     * @return 转换后的图片名称
     */
    public static String compression(MultipartFile multipartFile, String outFilePath, float ratio,
                                     boolean isChangeOutFileName) {
        File file;
        String resultName = null;
        String ext = "";
        try {
            ext = multipartFile.getOriginalFilename();
            ext = ext.substring(ext.lastIndexOf("."), ext.length());
            Image img = ImageIO.read(multipartFile.getInputStream());
            int newWidth;
            int newHeight;
            // 判断是否是等比缩放
            newWidth = (int) (img.getWidth(null) * ratio);
            newHeight = (int) (img.getHeight(null) * ratio);
            System.out.println("图片 新宽- 高： " + newWidth + " - " + newHeight);
            BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);

            /*
             * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
             */
            tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
            file = new File(outFilePath);
            if (!file.exists()) {
                file.mkdir();
            }
            if (isChangeOutFileName) {
                resultName = UUIDUtils.createCurrentTimeAndRandom() + ext;
            } else {
                resultName = multipartFile.getOriginalFilename();
            }
            ImageIO.write(tag, "jpg", new File(outFilePath + resultName));
            tag.flush();
            img.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println(resultName);
        return resultName;
    }

    /**
     * 图片 压缩
     *
     * @param multipartFile       multipartFile对象
     * @param outFilePath         图片输出路径
     * @param outWidth            输出图片宽度
     * @param outHeight           输出图片高度
     * @param isChangeOutFileName 是否改变输出的文件名,true为改变输出文件名,false为不改变输出文件名;
     * @return 转换后的图片名称
     */
    public static String compression(MultipartFile multipartFile, String outFilePath, int outWidth,
                                     int outHeight, boolean isChangeOutFileName) {
        File file;
        String ext;
        float ratio = 1.0f;
        String resultName = null;
        try {
            long tlen = multipartFile.getSize();
            if (tlen > MAX_IMAGE_VALUE) {
                ratio = 0.9f - ((tlen - MAX_IMAGE_VALUE) / tlen);
            }
            // 获取扩展名有带点的
            ext = multipartFile.getOriginalFilename();
            ext = ext.substring(ext.lastIndexOf("."), ext.length());
            Image img = ImageIO.read(multipartFile.getInputStream());
            // 得到源图宽
            int oldW = (int) (img.getWidth(null) * ratio);
            int oldH = (int) (img.getHeight(null) * ratio);
            System.out.println("源图宽-高：" + oldW + " - " + oldH);
            BufferedImage oldpic;
            if (oldW > oldH) {
                oldpic = new BufferedImage(oldW, oldW, BufferedImage.TYPE_INT_RGB);
                System.out.println("old_w > old_h");
            } else {
                if (oldW < oldH) {
                    oldpic = new BufferedImage(oldH, oldH, BufferedImage.TYPE_INT_RGB);
                    System.out.println("old_w < old_h");
                } else {
                    oldpic = new BufferedImage(oldW, oldH, BufferedImage.TYPE_INT_RGB);
                    System.out.println("默认输出 =================");
                }
            }
            Graphics2D g = oldpic.createGraphics();
            g.setColor(Color.white);
            if (oldW > oldH) {
                g.fillRect(0, 0, oldW, oldW);
                g.drawImage(img, 0, (oldW - oldH) / 2, oldW, oldH, Color.white, null);
            } else {
                if (oldW < oldH) {
                    g.fillRect(0, 0, oldH, oldH);
                    g.drawImage(img, (oldH - oldW) / 2, 0, oldW, oldH, Color.white, null);
                } else {
                    g.drawImage(img.getScaledInstance(oldW, oldH, Image.SCALE_SMOOTH), 0, 0, null);
                }
            }
            g.dispose();

            BufferedImage tag = new BufferedImage((int) outWidth, (int) outHeight, BufferedImage.TYPE_INT_RGB);

            /*
             * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
             */
            tag.getGraphics().drawImage(img.getScaledInstance(outWidth, outHeight, Image.SCALE_SMOOTH), 0, 0, null);
            file = new File(outFilePath);
            if (!file.exists()) {
                file.mkdir();
            }
            if (isChangeOutFileName) {
                resultName = UUIDUtils.createCurrentTimeAndRandom() + ext;
            } else {
                resultName = multipartFile.getOriginalFilename();
            }

            ImageIO.write(tag, IMAGE_TYPE_JPEG, new File(outFilePath + resultName));

            tag.flush();
            oldpic.flush();
            img.flush();
            System.out.println(resultName);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return resultName;
    }

    /**
     * 图片 压缩
     *
     * @param inputStream        图片输入流
     * @param outFilePathAndName 图片输出路径
     * @param outWidth           输出图片宽度
     * @param outHeight          输出图片高度
     * @return 转换后的图片名称
     */
    public static String compression(InputStream inputStream, String outFilePathAndName, int outWidth, int outHeight) {
        File file;
        try {
            Image img = ImageIO.read(inputStream);
            // 得到源图宽
            int oldW = img.getWidth(null);
            int oldH = img.getHeight(null);

            BufferedImage oldpic;
            if (oldW > oldH) {
                oldpic = new BufferedImage(oldW, oldW, BufferedImage.TYPE_INT_RGB);
            } else {
                if (oldW < oldH) {
                    oldpic = new BufferedImage(oldH, oldH, BufferedImage.TYPE_INT_RGB);
                } else {
                    oldpic = new BufferedImage(oldW, oldH, BufferedImage.TYPE_INT_RGB);
                }
            }
            // 创建
            Graphics2D g = oldpic.createGraphics();
            g.setColor(Color.white);
            if (oldW > oldH) {
                g.fillRect(0, 0, oldW, oldW);
                g.drawImage(img, 0, (oldW - oldH) / 2, oldW, oldH, Color.white, null);
            } else {
                if (oldW < oldH) {
                    g.fillRect(0, 0, oldH, oldH);
                    g.drawImage(img, (oldH - oldW) / 2, 0, oldW, oldH, Color.white, null);
                } else {
                    g.drawImage(img.getScaledInstance(oldW, oldH, Image.SCALE_SMOOTH), 0, 0, null);
                }
            }
            g.dispose();

            BufferedImage tag = new BufferedImage((int) outWidth, (int) outHeight, BufferedImage.TYPE_INT_RGB);

            /*
             * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
             */
            tag.getGraphics().drawImage(img.getScaledInstance(outWidth, outHeight, Image.SCALE_SMOOTH), 0, 0, null);
            file = new File(outFilePathAndName);
            if (!file.exists()) {
                file.mkdir();
            }
            ///// =============================================================
            ImageIO.write(tag, IMAGE_TYPE_JPEG, file);

            tag.flush();
            oldpic.flush();
            img.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }

            } catch (IOException e) {
                LogUtils.error(e.getMessage());
            }
        }

        return outFilePathAndName;
    }

    /**
     * 缩 放处理
     *
     * @return
     */
    public void zoom(File imageFile, File outFile) throws Exception {

        try {

            BufferedImage bufferedImage = ImageIO.read(imageFile);
            int w = (int) (bufferedImage.getWidth() * 0.2);
            int h = (int) (bufferedImage.getHeight() * 0.2);
            // 假设图片宽 高 最大为130 80,使用默认缩略算法
            Image iTemp = bufferedImage.getScaledInstance(w, h, Image.SCALE_DEFAULT);

            double ratio = 0.0;
            if ((bufferedImage.getHeight() > 130) || (bufferedImage.getWidth() > 80)) {
                if (bufferedImage.getHeight() > bufferedImage.getWidth()) {
                    ratio = 80.0 / bufferedImage.getHeight();
                } else {
                    ratio = 130.0 / bufferedImage.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
                iTemp = op.filter(bufferedImage, null);
            }

            ImageIO.write((BufferedImage) iTemp, "jpg", outFile);

        } catch (IOException e) {
            LogUtils.error(e.getMessage());
            throw new Exception(e);
        }
    }


    //===============================================================================

    /**
     * 计算text的长度（一个中文算两个字符）
     *
     * @param text
     * @return
     */
    private static int getLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (new String(text.charAt(i) + "").getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length / 2;
    }

    /**
     * <h4>缩放图像（按高度和宽度缩放）</h4>
     *
     * @param src
     * @param result 保存的路径和名称
     * @param height 高
     * @param width 宽
     * @param bb 是否补白
     * @throws IOException
     */
    @SuppressWarnings("static-access")
    private static void insideScale(BufferedImage src, String result, Integer height, Integer width, boolean bb)
            throws IOException {
        // 缩放比例
        double ratio = 0.0d;

        Image iTemp = src.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
        // 计算比例
        if ((src.getHeight() > height) || (src.getWidth() > width)) {
            if (src.getHeight() > src.getWidth()) {
                ratio = (new Integer(height)).doubleValue() / src.getHeight();
            } else {
                ratio = (new Integer(width)).doubleValue() / src.getWidth();
            }
            AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
            iTemp = op.filter(src, null);
        }
        // 补白
        if (bb) {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.setColor(Color.white);
            g.fillRect(0, 0, width, height);
            if (width == iTemp.getWidth(null)) {
                g.drawImage(iTemp, 0, (height - iTemp.getHeight(null)) / 2, iTemp.getWidth(null), iTemp.getHeight(null),
                        Color.white, null);
            } else {
                g.drawImage(iTemp, (width - iTemp.getWidth(null)) / 2, 0, iTemp.getWidth(null), iTemp.getHeight(null),
                        Color.white, null);
            }
            g.dispose();
            iTemp = image;
        }
        ImageIO.write((BufferedImage) iTemp, IMAGE_TYPE_JPEG, new File(result));
    }

    private static void insideScale(BufferedImage src, String destImageFileAndName, Integer scale, boolean flag)
            throws IOException {
        // 得到源图宽
        int width = src.getWidth();
        // 得到源图长
        int height = src.getHeight();
        // 放大
        if (flag) {
            width = width * scale;
            if (width > 4128) {
                width = 4128;
            }
            height = height * scale;
            if (height > 3096) {
                height = 3096;
            }
            // 缩小
        } else {
            width = width / scale;
            height = height / scale;
        }
        Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        // 绘制缩小后的图
        g.drawImage(image, 0, 0, null);
        g.dispose();
        // 输出到文件流
        ImageIO.write(tag, IMAGE_TYPE_JPEG, new File(destImageFileAndName));
    }

}
