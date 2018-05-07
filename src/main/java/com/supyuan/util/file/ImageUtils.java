package com.supyuan.util.file;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;

import com.supyuan.file.FileSize;
import com.supyuan.util.FileUtils;

public class ImageUtils {
	
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	
	static List<String> imageSuffix = new ArrayList<String>();
	
	static {
		imageSuffix.add(".jpg");
		imageSuffix.add(".jpeg");
		imageSuffix.add(".png");
		imageSuffix.add(".bmp");
	}

	public static boolean isImage(String path) {
		String sufffix = FileUtils.getSuffix(path);
		if (StringUtils.isNotBlank(sufffix))
			return imageSuffix.contains(sufffix.toLowerCase());
		return false;
	}

	public static void main(String[] args) {
		System.out.println(FileUtils.getSuffix("xxx.jpg"));
	}
	
	/**
	 * 同时保存原图和缩略图
	 * @param file
	 * @param dest
	 * @param sufffix
	 * @throws IOException
	 */
	public static void scaleOldAndSmall(File file, String dest, String sufffix) throws IOException {
		StringBuilder bu = new StringBuilder(dest);
		File newfile = new File(bu.append(sufffix).toString());
		if (!newfile.getParentFile().exists()) {
			newfile.getParentFile().mkdirs();
		}
		
		Image src = ImageIO.read(file);
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		BufferedImage targetBuffered = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = targetBuffered.getGraphics();
		graphics.drawImage(src, 0, 0,width, height, null);
		graphics.dispose();
		targetBuffered.flush();
		//保存原图
		ImageIO.write(targetBuffered, FileUtils.getSuffixNoSpot(file.getName()), newfile);
		//保存缩略图
		scaleSmall(newfile, dest, sufffix, FileSize.S200X150.getWidth(), FileSize.S200X150.getHeight());
	}
	
	/**
	 * 保存缩略图
	 * @param file
	 * @param dest
	 * @param sufffix
	 * @param w
	 * @param h
	 * @throws IOException
	 */
	public static void scaleSmall(File file, String dest, String sufffix, int w, int h) throws IOException {
		StringBuilder bu = new StringBuilder(dest).append("_").append(FileSize.S200X150.getDesc()).append(sufffix);
		File smallFile = new File(bu.toString());
		if (!smallFile.getParentFile().exists()) {
			smallFile.getParentFile().mkdirs();
		}
		Image src = ImageIO.read(file);
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		graphics.drawImage(src, 0, 0, w, h, null);  
		graphics.dispose();
		image.flush();
		ImageIO.write(image, FileUtils.getSuffixNoSpot(file.getName()), smallFile);
	}
	
	/**
	 * 保存原图
	 * @param file
	 * @param dest
	 * @throws IOException
	 */
	public static void scale(File file, String dest) throws IOException {
		String sufffix = FileUtils.getSuffix(file.getName());
		StringBuilder bu = new StringBuilder(dest).append(sufffix);
		File smallFile = new File(bu.toString());
		if (!smallFile.getParentFile().exists()) {
			smallFile.getParentFile().mkdirs();
		}
		Image src = ImageIO.read(file);
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		graphics.drawImage(src, 0, 0,width,height, null);  
		graphics.dispose();
		image.flush();
		//保存原图
		ImageIO.write(image, sufffix, smallFile);
	}

}