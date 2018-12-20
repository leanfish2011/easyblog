package com.blog.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 图片工具类，主要针对图片水印处理
 */
public class ImageCompareHelper {

	// 项目根目录路径
	private static final String path = System.getProperty("user.dir");

	/**
	 * 生成缩略图 <br/>
	 * 保存:ImageIO.write(BufferedImage, imgType[jpg/png/...], File);
	 * 
	 * @param source 原图片
	 * @param width 缩略图宽
	 * @param height 缩略图高
	 * @param b 是否等比缩放
	 */
	private static BufferedImage thumb(BufferedImage source, int width, int height, boolean b) {
		// targetW，targetH分别表示目标长和宽
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) width / source.getWidth();
		double sy = (double) height / source.getHeight();

		if (b) {
			if (sx > sy) {
				sx = sy;
				width = (int) (sx * source.getWidth());
			} else {
				sy = sx;
				height = (int) (sy * source.getHeight());
			}
		}

		if (type == BufferedImage.TYPE_CUSTOM) { // handmade
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else {
			target = new BufferedImage(width, height, type);
		}
		Graphics2D g = target.createGraphics();
		// smoother than exlax:
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();

		return target;
	}

	/**
	 * 读取JPEG图片
	 * @param filename 文件名
	 * @return BufferedImage 图片对象
	 */
	private static BufferedImage readPNGImage(String filename) {
		try {
			File inputFile = new File(filename);
			BufferedImage sourceImage = ImageIO.read(inputFile);
			return sourceImage;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 灰度值计算
	 * @param pixels 像素
	 * @return int 灰度值
	 */
	private static int rgbToGray(int pixels) {
		// int _alpha = (pixels >> 24) & 0xFF;
		int _red = (pixels >> 16) & 0xFF;
		int _green = (pixels >> 8) & 0xFF;
		int _blue = (pixels) & 0xFF;

		return (int) (0.3 * _red + 0.59 * _green + 0.11 * _blue);
	}

	/**
	 * 计算数组的平均值
	 * @param pixels 数组
	 * @return int 平均值
	 */
	private static int average(int[] pixels) {
		float m = 0;
		for (int i = 0; i < pixels.length; ++i) {
			m += pixels[i];
		}
		m = m / pixels.length;
		return (int) m;
	}

	/**
	 * 计算"汉明距离"（Hamming distance）。 如果不相同的数据位不超过5，就说明两张图片很相似；如果大于10，就说明这是两张不同的图片。
	 * @param standFinger 源hashCode
	 * @param uploadFinger 与之比较的hashCode
	 */
	private static int hammingDistance(String standFinger, String uploadFinger) {
		int difference = 0;
		int len = standFinger.length();

		for (int i = 0; i < len; i++) {
			if (standFinger.charAt(i) != uploadFinger.charAt(i)) {
				difference++;
			}
		}

		return difference;
	}

	/**
	 * 生成图片指纹
	 * @param filename 文件名
	 * @return 图片指纹
	 */
	public static String produceFingerPrint(String filename) {
		BufferedImage source = ImageCompareHelper.readPNGImage(filename);// 读取文件

		int width = 8;
		int height = 8;

		// 第一步，缩小尺寸。
		// 将图片缩小到8x8的尺寸，总共64个像素。这一步的作用是去除图片的细节，只保留结构、明暗等基本信息，摒弃不同尺寸、比例带来的图片差异。
		BufferedImage thumb = ImageCompareHelper.thumb(source, width, height, false);

		// 第二步，简化色彩。
		// 将缩小后的图片，转为64级灰度。也就是说，所有像素点总共只有64种颜色。
		int[] pixels = new int[width * height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				pixels[i * height + j] = ImageCompareHelper.rgbToGray(thumb.getRGB(i, j));
			}
		}

		// 第三步，计算平均值。
		// 计算所有64个像素的灰度平均值。
		int avgPixel = ImageCompareHelper.average(pixels);

		// 第四步，比较像素的灰度。
		// 将每个像素的灰度，与平均值进行比较。大于或等于平均值，记为1；小于平均值，记为0。
		int[] comps = new int[width * height];
		for (int i = 0; i < comps.length; i++) {
			if (pixels[i] >= avgPixel) {
				comps[i] = 1;
			} else {
				comps[i] = 0;
			}
		}

		// 第五步，计算哈希值。
		// 将上一步的比较结果，组合在一起，就构成了一个64位的整数，这就是这张图片的指纹。组合的次序并不重要，只要保证所有图片都采用同样次序就行了。
		StringBuffer hashCode = new StringBuffer();
		for (int i = 0; i < comps.length; i += 4) {
			int result = comps[i] * (int) Math.pow(2, 3) + comps[i + 1] * (int) Math.pow(2, 2)
					+ comps[i + 2] * (int) Math.pow(2, 1) + comps[i + 3];
			hashCode.append(binaryToHex(result));
		}

		// 得到指纹以后，就可以对比不同的图片，看看64位中有多少位是不一样的。
		return hashCode.toString();
	}

	/**
	 * 计算相似度
	 * @param standFinger 标准图像指纹
	 * @param uploadFinger 上传的待比较图像指纹
	 * @return 相似度百分比
	 */
	public static double getSimilarty(String standFinger, String uploadFinger) {
		int difference = hammingDistance(standFinger, uploadFinger);
		double similarty = (double) (16 - difference) / 16 * 100;

		return similarty;
	}

	/**
	 * 二进制转为十六进制
	 * @param int binary
	 * @return char hex
	 */
	private static char binaryToHex(int binary) {
		char ch = ' ';
		switch (binary) {
		case 0:
			ch = '0';
			break;
		case 1:
			ch = '1';
			break;
		case 2:
			ch = '2';
			break;
		case 3:
			ch = '3';
			break;
		case 4:
			ch = '4';
			break;
		case 5:
			ch = '5';
			break;
		case 6:
			ch = '6';
			break;
		case 7:
			ch = '7';
			break;
		case 8:
			ch = '8';
			break;
		case 9:
			ch = '9';
			break;
		case 10:
			ch = 'a';
			break;
		case 11:
			ch = 'b';
			break;
		case 12:
			ch = 'c';
			break;
		case 13:
			ch = 'd';
			break;
		case 14:
			ch = 'e';
			break;
		case 15:
			ch = 'f';
			break;
		default:
			ch = ' ';
		}
		return ch;
	}
}