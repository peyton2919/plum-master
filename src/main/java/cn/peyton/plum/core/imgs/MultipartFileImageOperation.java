package cn.peyton.plum.core.imgs;

import cn.peyton.plum.core.utils.LogUtils;
import cn.peyton.plum.core.utils.UUIDUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * <h3>图片操作类</h3>
 * <pre>
 * 用在springmvc MultipartFile上传图片压缩处理
 * </pre>
 * @Title: MultipartFileImageOperates.java 
 * @Package cn.peyton.wanzibaidai.ext.img 
 * @ClassName: MultipartFileImageOperates  
 * @author Peyton_YU
 * @date 2017年10月7日 上午12:08:23
 */
@SuppressWarnings("ALL")
public final class MultipartFileImageOperation implements Serializable {
	/** @Fields serialVersionUID */
	private static final long serialVersionUID = 1L;
	/**  2 * 1024 * 1024  */
	private static final long MAX_IMAGE_VALUE_SIZE = 2097152;
	/** 图片格式化 png格式输出字节 更大 */
	@SuppressWarnings("unused")
	private static final String FORMAT_PNG = "png";
	/** 图片格式化  jpg格式输出字节会更精细  */
	private static final String FORMAT_JPG = "jpg";
	
	
	/**
	 * <h4>图片旋转</h4>
	 * @param src 源图片
	 * @param angle 旋转度数
	 * @return 旋转后的图片
	 */
	public static BufferedImage rotate(BufferedImage src, int angle) {
		return ImageRotateUtils.rotate(src, angle);
	}
	
	/**
	 * <h4>图片旋转</h4>
	 * @param multipartFile  multipartFile对象
	 * @param outFilePath 图片输出路径{必需要绝对路径最以'\'结束}
	 * @param angle 旋转角度{需要isRotate为true时这个值才有用}
	 * @param isChangeOutFileName 是否改变输出的文件名{true为改变输出文件名,false为不改变输出文件名}
	 * @return 图片名称
	 */
	public static String rotate(MultipartFile multipartFile ,String outFilePath,
			int angle,boolean isChangeOutFileName) {
		StringBuilder sb = new StringBuilder(outFilePath);
		String tName = multipartFile.getOriginalFilename();
		String ext = tName.substring(tName.lastIndexOf("."), tName.length());
		//输出名称是否需要改变
		if (isChangeOutFileName) {
			tName = UUIDUtils.createCurrentTimeAndRandom() + ext;
		}
		sb.append(tName);
        rotate(new File(sb.toString()), angle);
		return tName;
	}
	/**
	 * <h4>图片旋转</h4>
	 * @param file 文件对象
	 * @param angle  旋转角度
	 * @return 文件名称
	 */
	public static String rotate(File file,int angle) {
		try {
			if (angle < 0 || angle > 359) {
				angle = 0;
			}
			ImageIO.write(ImageRotateUtils.rotate(ImageIO.read(file), angle),
					FORMAT_JPG, file);
		} catch (IOException e) {
			LogUtils.error(e.getMessage());
		}
		return file.getName();
	}

	/**
	 * 
	 * @param multipartFile multipartFile对象
	 * @param outFilePath 图片输出路径{必需要绝对路径最以'\'结束}
	 * @param ratio 输出图片比率(小数)
	 * @param isChangeOutFileName 是否改变输出的文件名{true为改变输出文件名,false为不改变输出文件名}
	 * @return 图片名称
	 * @throws IOException 
	 */
	public static String compression(MultipartFile multipartFile, String outFilePath, float ratio,
			boolean isChangeOutFileName) throws IOException {
		return insideCompression(multipartFile, outFilePath, ratio, isChangeOutFileName, false, 0);
	}
	
	/**
	 * 
	 * @param multipartFile multipartFile对象
	 * @param outFilePath 图片输出路径{必需要绝对路径最以'\'结束}
	 * @param ratio 输出图片比率(小数)
	 * @param isChangeOutFileName 是否改变输出的文件名{true为改变输出文件名,false为不改变输出文件名}
	 * @param isRotate 是否需要旋转{true旋转;false不旋转}
	 * @param angle 旋转角度{需要isRotate为true时这个值才有用}
	 * @return 图片名称
	 * @throws IOException 
	 */
	public static String compression(MultipartFile multipartFile, String outFilePath, float ratio,
			boolean isChangeOutFileName,boolean isRotate,int angle) throws IOException {
		return insideCompression(multipartFile, outFilePath, ratio, isChangeOutFileName, isRotate, angle);
	}
	
	/**
	 * 图片 压缩
	 * 
	 * @param multipartFile multipartFile对象
	 * @param outFilePath 图片输出路径{必需要绝对路径最以'\'结束}
	 * @param outWidth 输出图片宽度 
	 * @param outHeight 输出图片高度
	 * @param isChangeOutFileName 是否改变输出的文件名,true为改变输出文件名,false为不改变输出文件名;
	 * @return 图片名称
	 * @throws IOException 
	 */
	public static String compression(MultipartFile multipartFile, String outFilePath, int outWidth,
			int outHeight,boolean isChangeOutFileName) throws IOException {
		return insideCompression(multipartFile, outFilePath, outWidth, outHeight, isChangeOutFileName, false, 0);
	}
	
	/**
	 * 图片 压缩
	 * 
	 * @param multipartFile multipartFile对象
	 * @param outFilePath 图片输出路径{必需要绝对路径最以'\'结束}
	 * @param outWidth 输出图片宽度 {输出图片高度 按比率定义}
	 * @param isChangeOutFileName 是否改变输出的文件名,true为改变输出文件名,false为不改变输出文件名;
	 * @return 图片名称
	 * @throws IOException 
	 */
	public static String compression(MultipartFile multipartFile, String outFilePath, int outWidth,
			boolean isChangeOutFileName) throws IOException {
		return insideCompression(multipartFile, outFilePath, outWidth, -100, isChangeOutFileName, false, 0);
	}
	
	/**
	 * <h4>压缩图片</h4>
	 * @param multipartFile multipartFile对象
	 * @param outFilePath 图片输出路径{必需要绝对路径最以'\'结束}
	 * @param outWidth 输出图片宽度
	 * @param outHeight 输出图片高度
	 * @param isChangeOutFileName  是否改变输出的文件名{true为改变输出文件名,false为不改变输出文件名}
	 * @param isRotate 是否需要旋转{true旋转;false不旋转}
	 * @param angle 旋转角度{需要isRotate为true时这个值才有用}
	 * @return 图片名称
	 * @throws IOException 
	 */
	public static String compression(MultipartFile multipartFile, String outFilePath, int outWidth,
			int outHeight,boolean isChangeOutFileName,boolean isRotate,int angle) throws IOException {
		return insideCompression(multipartFile, outFilePath, outWidth, outHeight, isChangeOutFileName, isRotate, angle);
	}
	
	/**
	 * <h4>压缩图片</h4>
	 * @param multipartFile multipartFile对象
	 * @param outFilePath 图片输出路径{必需要绝对路径最以'\'结束}
	 * @param outWidth 输出图片宽度{输出图片高度 按比率定义}
	 * @param isChangeOutFileName  是否改变输出的文件名{true为改变输出文件名,false为不改变输出文件名}
	 * @param isRotate 是否需要旋转{true旋转;false不旋转}
	 * @param angle 旋转角度{需要isRotate为true时这个值才有用}
	 * @return 图片名称
	 * @throws IOException 
	 */
	public static String compression(MultipartFile multipartFile, String outFilePath, int outWidth,
			boolean isChangeOutFileName,boolean isRotate,int angle) throws IOException {
		return insideCompression(multipartFile, outFilePath, outWidth, -100, isChangeOutFileName, isRotate, angle);
	}
	
	
	/**
	 * <h4>压缩图片</h4>
	 * @param multipartFile multipartFile对象
	 * @param outFilePath 图片输出路径{必需要绝对路径最以'\'结束}
	 * @param outWidth 输出图片宽度
	 * @param outHeight 输出图片高度
	 * @param isChangeOutFileName  是否改变输出的文件名{true为改变输出文件名,false为不改变输出文件名}
	 * @param isRotate 是否需要旋转{true旋转;false不旋转}
	 * @param angle 旋转角度{需要isRotate为true时这个值才有用}
	 * @return 图片名称
	 * @throws IOException 
	 */
	private static String insideCompression(MultipartFile multipartFile, String outFilePath, int outWidth,
			int outHeight,boolean isChangeOutFileName,boolean isRotate,int angle) throws IOException {
		File file;
		String ext;
		float ratio = 1.0f;
		String resultName = null;
		long tLen = multipartFile.getSize();
		if (tLen > MAX_IMAGE_VALUE_SIZE) {
			ratio = 0.9f - ((tLen - MAX_IMAGE_VALUE_SIZE) / tLen);
		}
		// 获取扩展名有带点的
		ext = getExtName(multipartFile.getOriginalFilename());
		Image img = ImageIO.read(multipartFile.getInputStream());
        // 得到源图宽
		int oldW = (int) (img.getWidth(null) * ratio);
		int oldH = (int) (img.getHeight(null) * ratio);
        //表示宽度 大于高度
        boolean tChange = true;
		if (oldH > oldW) {
			tChange = false;
		}
        //比率 {用 小数 除以 大数}
        float tRatio = 0f;
        //获取到比率
		tRatio = (tChange) ?(float)oldH/(float)oldW : (float)oldW/(float)oldH;
        BufferedImage oldPic = null;
		if (outHeight <= 0) {
			outHeight = (int) (outWidth / tRatio);
			int tw = outWidth;
			int th = outHeight;
			if (tChange) {
				outWidth = th;
				outHeight = tw;
			}
		}
		if (oldW > oldH) {
			oldPic = new BufferedImage(oldW, oldW, BufferedImage.TYPE_INT_RGB);
		} else {
			if (oldW < oldH) {
				oldPic = new BufferedImage(oldH, oldH, BufferedImage.TYPE_INT_RGB);
			} else {
				oldPic = new BufferedImage(oldW, oldH, BufferedImage.TYPE_INT_RGB);
			}
		}
		Graphics2D g = oldPic.createGraphics();
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
		/* Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢 */
		tag.getGraphics().drawImage(img.getScaledInstance(outWidth, outHeight, Image.SCALE_SMOOTH), 0, 0, null);
		file = new File(outFilePath);
		if (!file.exists()) {
			file.mkdir();
		}
		//输出名称是否需要改变
		if (isChangeOutFileName) {
			resultName = UUIDUtils.createCurrentTimeAndRandom() + ext;
		} else {
			resultName = multipartFile.getOriginalFilename();
		}
        writeImageIo(outFilePath, isRotate, angle, resultName, tag);
		tag.flush();
		oldPic.flush();
		img.flush();
		return resultName;
	}


	/**
	 * 
	 * @param multipartFile multipartFile对象
	 * @param outFilePath 图片输出路径
	 * @param ratio 输出图片比率(小数)
	 * @param isChangeOutFileName 是否改变输出的文件名{true为改变输出文件名,false为不改变输出文件名}
	 * @param isRotate 是否需要旋转{true旋转;false不旋转}
	 * @param angle 旋转角度{需要isRotate为true时这个值才有用}
	 * @return 图片名称
	 * @throws IOException 
	 */
	private static String insideCompression(MultipartFile multipartFile, String outFilePath, float ratio,
			boolean isChangeOutFileName,boolean isRotate,int angle) throws IOException {
		File file;
		String resultName = null;
		String ext = getExtName(multipartFile.getOriginalFilename());

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
        writeImageIo(outFilePath, isRotate, angle, resultName, tag);

        tag.flush();
		img.flush();
		return resultName;
	}

    /**
     * <h4>写图片</h4>
     * @param outFilePath
     * @param isRotate
     * @param angle
     * @param resultName
     * @param tag
     * @throws IOException
     */
    private static void writeImageIo(String outFilePath, boolean isRotate, int angle, String resultName, BufferedImage tag) throws IOException {
        //是否需要旋转true为旋转
	    if (isRotate) {
            if (angle < 0 || angle > 359) {
                angle = 0;
            }
            ImageIO.write(ImageRotateUtils.rotate(tag, angle), FORMAT_JPG, new File(outFilePath + resultName));
        }else {
            ImageIO.write(tag, FORMAT_JPG , new File(outFilePath + resultName));
        }
    }

    /**
     * <h4>获取扩展名</h4>
     *
     * @param name
     * @return
     */
    private static String getExtName(String name) {

        if (null == name || "".equals(name)) {
            return "";
        }
        return name.substring(name.lastIndexOf("."), name.length());
    }
}
