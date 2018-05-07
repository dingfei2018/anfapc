package com.supyuan.file;

/**
 * 文件尺寸
 * @author liangxp
 *
 * Date:2017年9月20日下午3:02:22 
 * 
 * @email liangxp@anfawuliu.com
 */
public enum FileSize {
	
	S000X000(0,0),
	S200X150(200,150),
	S1200X400(1200,400),
	S1200X500(1200,500),
	S1200X600(1200,600);
	
	private int width;
	
	private int height; 
	
	private int size; 
	
	private String fileType;
	
	private String  desc;
	
	private FileSize(int width, int height) {
		this.width = width;
		this.height = height;
		this.size = 2097152;//2M
		this.fileType = "jpg|jpeg|png";
		this.desc = width + "X" + height;
	}
	
	private FileSize(int width, int height, int size, String fileType, String desc) {
		this.width = width;
		this.height = height;
		this.size = size;
		this.fileType = fileType;
		this.desc = desc;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
