package cn.peyton.plum.core.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * <h3>验证码类 .</h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @create date: 2018/11/16 16:04
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
public final class VerifyCodeUtils implements Serializable {
	/**
	 * 验证码类型为仅数字,即0~9
	 */
	public static final int TYPE_NUM_ONLY = 0;

	/**
	 * 验证码类型为仅字母,即大小写字母混合
	 */
	public static final int TYPE_LETTER_ONLY = 1;

	/**
	 * 验证码类型为数字和大小写字母混合
	 */
	public static final int TYPE_ALL_MIXED = 2;

	/**
	 * 验证码类型为数字和大写字母混合
	 */
	public static final int TYPE_NUM_UPPER = 3;

	/**
	 * 验证码类型为数字和小写字母混合
	 */
	public static final int TYPE_NUM_LOWER = 4;

	/**
	 * 验证码类型为仅大写字母
	 */
	public static final int TYPE_UPPER_ONLY = 5;

	/**
	 * 验证码类型为仅小写字母
	 */
	public static final int TYPE_LOWER_ONLY = 6;

	private VerifyCodeUtils() {
	}

	/**
	 * 生成随机颜色
	 */
	private static Color generateRandomColor() {
		Random random = new Random();
		return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
	}

	/**
	 * 生成图片验证码
	 * 
	 * @param type
	 *            验证码类型,参见本类的静态属性
	 * @param length
	 *            验证码字符长度,要求大于0的整数
	 * @param excludeString
	 *            需排除的特殊字符
	 * @param width
	 *            图片宽度(注意此宽度若过小,容易造成验证码文本显示不全,如4个字符的文本可使用85到90的宽度)
	 * @param height
	 *            图片高度
	 * @param interLine
	 *            图片中干扰线的条数
	 * @param randomLocation
	 *            每个字符的高低位置是否随机
	 * @param backColor
	 *            图片颜色,若为null则表示采用随机颜色
	 * @param foreColor
	 *            字体颜色,若为null则表示采用随机颜色
	 * @param lineColor
	 *            干扰线颜色,若为null则表示采用随机颜色
	 * @return 图片缓存对象
	 */
	public static BufferedImage generateImageCode(int type, int length, String excludeString, int width, int height,
			int interLine, boolean randomLocation, Color backColor, Color foreColor, Color lineColor) {
		String textCode = generateTextCode(type, length, excludeString);
		return generateImageCode(textCode, width, height, interLine, randomLocation, backColor, foreColor, lineColor);
	}

	/**
	 * 生成验证码字符串
	 * 
	 * @param type
	 *            验证码类型,参见本类的静态属性
	 * @param length
	 *            验证码长度,要求大于0的整数
	 * @param excludeString
	 *            需排除的特殊字符（无需排除则为null）
	 * @return 验证码字符串
	 */
	public static String generateTextCode(int type, int length, String excludeString) {
		if (length <= 0) {
			return "";
		}
		StringBuffer verifyCode = new StringBuffer();
		int i = 0;
		Random random = new Random();
		switch (type) {
		case TYPE_NUM_ONLY:
			while (i < length) {
				int t = random.nextInt(10);
				// 排除特殊字符
				if (null == excludeString || excludeString.indexOf(t + "") < 0) {
					verifyCode.append(t);
					i++;
				}
			}
			break;
		case TYPE_LETTER_ONLY:
			while (i < length) {
				int t = random.nextInt(123);
				if ((t >= 97 || (t >= 65 && t <= 90))
						&& (null == excludeString || excludeString.indexOf((char) t) < 0)) {
					verifyCode.append((char) t);
					i++;
				}
			}
			break;
		case TYPE_ALL_MIXED:
			while (i < length) {
				int t = random.nextInt(123);
				if ((t >= 97 || (t >= 65 && t <= 90) || (t >= 48 && t <= 57))
						&& (null == excludeString || excludeString.indexOf((char) t) < 0)) {
					verifyCode.append((char) t);
					i++;
				}
			}
			break;
		case TYPE_NUM_UPPER:
			while (i < length) {
				int t = random.nextInt(91);
				if ((t >= 65 || (t >= 48 && t <= 57))
						&& (null == excludeString || excludeString.indexOf((char) t) < 0)) {
					verifyCode.append((char) t);
					i++;
				}
			}
			break;
		case TYPE_NUM_LOWER:
			while (i < length) {
				int t = random.nextInt(123);
				if ((t >= 97 || (t >= 48 && t <= 57))
						&& (null == excludeString || excludeString.indexOf((char) t) < 0)) {
					verifyCode.append((char) t);
					i++;
				}
			}
			break;
		case TYPE_UPPER_ONLY:
			while (i < length) {
				int t = random.nextInt(91);
				if ((t >= 65) && (null == excludeString || excludeString.indexOf((char) t) < 0)) {
					verifyCode.append((char) t);
					i++;
				}
			}
			break;
		case TYPE_LOWER_ONLY:
			while (i < length) {
				int t = random.nextInt(123);
				if ((t >= 97) && (null == excludeString || excludeString.indexOf((char) t) < 0)) {
					verifyCode.append((char) t);
					i++;
				}
			}
			break;
		}
		return verifyCode.toString();
	}

	/**
	 * 已有验证码,生成验证码图片
	 * 
	 * @param textCode
	 *            文本验证码
	 * @param width
	 *            图片宽度(注意此宽度若过小,容易造成验证码文本显示不全,如4个字符的文本可使用85到90的宽度)
	 * @param height
	 *            图片高度
	 * @param interLine
	 *            图片中干扰线的条数
	 * @param randomLocation
	 *            每个字符的高低位置是否随机
	 * @param backColor
	 *            图片颜色,若为null则表示采用随机颜色
	 * @param foreColor
	 *            字体颜色,若为null则表示采用随机颜色
	 * @param lineColor
	 *            干扰线颜色,若为null则表示采用随机颜色
	 * @return 图片缓存对象
	 */
	public static BufferedImage generateImageCode(String textCode, int width, int height, int interLine,
			boolean randomLocation, Color backColor, Color foreColor, Color lineColor) {
		// 创建内存图像
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics graphics = bufferedImage.getGraphics();
		// 画背景图
		graphics.setColor(null == backColor ? generateRandomColor() : backColor);
		graphics.fillRect(0, 0, width, height);
		// 画干扰线
		Random random = new Random();
		if (interLine > 0) {
			int x = 0, y = 0, x1 = width, y1 = 0;
			for (int i = 0; i < interLine; i++) {
				graphics.setColor(null == lineColor ? generateRandomColor() : lineColor);
				y = random.nextInt(height);
				y1 = random.nextInt(height);
				graphics.drawLine(x, y, x1, y1);
			}
		}
		// 字体大小为图片高度的80%
		int fsize = (int) (height * 0.8);
		int fx = height - fsize;
		int fy = fsize;
		// 设定字体
		graphics.setFont(new Font("Default", Font.PLAIN, fsize));
		// 写验证码字符
		for (int i = 0; i < textCode.length(); i++) {
			fy = randomLocation ? (int) ((Math.random() * 0.3 + 0.6) * height) : fy;
			graphics.setColor(null == foreColor ? generateRandomColor() : foreColor);
			// 将验证码字符显示到图象中
			graphics.drawString(textCode.charAt(i) + "", fx, fy);
			fx += fsize * 0.9;
		}
		graphics.dispose();
		return bufferedImage;
	}

    // ****************************************************************************** //

    /**
     * <h4>产生 一组 随机数</h4>
     * <pre>
     *     返回 Map<String,Object> 对象
     *     key:值 verifyCode 随机数
     *           verifyPic 随机数图片
     * </pre>
     * @param verifyLength
     * @return
     */
    public static Map<String, Object> generateVerify(int verifyLength) {
	    if (verifyLength<=4){
	        verifyLength = 4;
        }
	    //创建一张图片
        BufferedImage verifyPic = new BufferedImage(120, 40, BufferedImage.TYPE_3BYTE_BGR);
        //通过图片获取画笔
        Graphics2D g = verifyPic.createGraphics();
        //准备一个字母 + 数字 的字典
        String letters = "23456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        //生成随机验证码
        StringBuffer verifyCode = new StringBuffer();
        for (int i = 0; i < verifyLength; i++) {
            verifyCode.append(letters.charAt((int)(Math.random()*letters.length())));
        }
        //将图片的底板由黑变白
        g.setColor(Color.white);
        g.fillRect(0, 0, 120, 40);
        //将验证码画在图片之上
        g.setFont(new Font("微软雅黑", Font.BOLD, 24));
        for (int i = 0; i < verifyLength; i++) {
            //随机产生一个角度
            double theta = Math.random() * Math.PI / 4 * ((int) (Math.random() * 2) == 0 ? 1 : -1);
            //产生偏转
            g.rotate(theta, 24 + i * 22, 20);
            //每画一个字幕之前都随机给一个颜色
            g.setColor(new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256)));
            g.drawString(verifyCode.charAt(i) + "", 20 + i * 22, 26);
            //回正
            g.rotate(-theta, 24 + i * 22, 20);
        }
        for (int i = 0; i < 5; i++) {
            //给随机颜色
            g.setColor(new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256)));
            g.drawLine((int)(Math.random()*120),(int)(Math.random()*40),
                    (int)(Math.random()*120),(int)(Math.random()*40));
        }
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, 118, 38);
        //将验证码和图片一起存入 map
        Map<String, Object> data = new HashMap<>();
        data.put("verifyCode", verifyCode);
        data.put("verifyPic", verifyPic);
        return data;
    }
}
