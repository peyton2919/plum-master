package cn.peyton.plum.core.imgs;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <h3>图片旋转类</h3>
 * @Title: RotateImage.java
 * @ClassName: RotateImage
 * @author Peyton_YU
 * @date 2017年10月6日 下午11:11:44
 */
public final class ImageRotateUtils {

	/**
	 * <h4>图片旋转</h4>
	 * @param src 源图片
	 * @param angle 旋转度数
	 * @return 旋转后的图片
	 */
	public static BufferedImage rotate(BufferedImage src, int angle) {
        // 传进来图片的路径，和旋转的角度，好像只能是90度90度这样旋转
		int srcWidth = src.getWidth(null);
		int srcHeight = src.getHeight(null);
		// calculate the new image size
		Rectangle rectDes = calcRotatedSize(new Rectangle(new Dimension(srcWidth, srcHeight)), angle);

		BufferedImage res = new BufferedImage(rectDes.width, rectDes.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = res.createGraphics();
		// transform
		g2.translate((rectDes.width - srcWidth) / 2, (rectDes.height - srcHeight) / 2);
		g2.rotate(Math.toRadians(angle), srcWidth / 2, srcHeight / 2);

		g2.drawImage(src, null, null);
		return res;
	}

	/**
	 * <h4>计算旋转</h4>
	 * @param src
	 * @param angle
	 * @return
	 */
	private static Rectangle calcRotatedSize(Rectangle src, int angle) {
		// if angel is greater than 90 degree, we need to do some conversion
		if (angle >= 90) {
			if (angle / 90 % 2 == 1) {
				int temp = src.height;
				src.height = src.width;
				src.width = temp;
			}
			angle = angle % 90;
		}

		double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
		double len = 2 * Math.sin(Math.toRadians(angle) / 2) * r;
		double angelAlpha = (Math.PI - Math.toRadians(angle)) / 2;
		double angelDaltaWidth = Math.atan((double) src.height / src.width);
		double angelDaltaHeight = Math.atan((double) src.width / src.height);

		int lenDaltaWidth = (int) (len * Math.cos(Math.PI - angelAlpha - angelDaltaWidth));
		int lenDaltaHeight = (int) (len * Math.cos(Math.PI - angelAlpha - angelDaltaHeight));
		int desWidth = src.width + lenDaltaWidth * 2;
		int desHeight = src.height + lenDaltaHeight * 2;
		return new Rectangle(new Dimension(desWidth, desHeight));
	}
}
