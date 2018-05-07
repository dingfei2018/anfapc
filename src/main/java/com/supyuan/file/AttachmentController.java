package com.supyuan.file;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.log.Log;
import com.jfinal.upload.UploadFile;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.util.Config;
import com.supyuan.util.FileUtils;
import com.supyuan.util.file.ImageUtils;

/**
 * 
 * 文件上传
 * 
 * @author liangxp
 *
 * Date:2017年6月27日下午12:03:35 
 * 
 * @email liangxp@anfawuliu.com
 */
@ControllerBind(controllerKey = "/file")
public class AttachmentController extends BaseProjectController {
	
	private static final Log log = Log.getLog(AttachmentController.class);
	
	private static final String jspPath = "/pages/file/";
	
	private static final String FILENAME = "fileName";
	
	private static final String DOMAIN_PREFIX = Config.getStr("IMAGE.DOMAIN_PREFIX");//图片文件访问域名前缀
	
	private static final String IMAGE_PATH = Config.getStr("FILE.UPLOAD_PATH");//图片上传目录
	
	public void index() {
		renderJsp(jspPath + "file.jsp");
	}
	
	public void uploadImage() {
		JSONObject json = new JSONObject();
		SessionUser sessionSysUser = getSessionSysUser();
		if(sessionSysUser==null){
			json.put("success", false);
			json.put("message", "未登陆文件上传失败");
			renderJson(json.toString());
			return;
		}
		String imgType = getPara("imgType");
		String filePath = getUploadFilePath(sessionSysUser.getUserId(), imgType);
		UploadFile uploadFile = null;
		try {
			uploadFile = getFile(FILENAME, filePath);
			FileSize fileSize = getFileSize(imgType);
			if(validate(uploadFile, fileSize, json)){
				uploadFile(uploadFile, filePath, json);
			}
		}catch (Exception e) {
			log.error("文件上传失败", e);
			json.put("success", false);
			json.put("message", "文件上传失败");
		}
		renderJson(json.toString());
	}
	
	
	//生成文件路径
	private String getUploadFilePath(Integer userId, String imgType){
		StringBuilder tempFileName = new StringBuilder("/"+imgType).append("/").append(ImageUtils.dateFormat.format(new Date())).append("/").append(userId);
		return tempFileName.toString();
	}

	//上传图片
	private void uploadFile(UploadFile file,String filePath, JSONObject json){
		if (file == null){
			json.put("success", false);
			json.put("message", "文件上传失败");
			return;
		}
		try {
			String sufffix = FileUtils.getSuffix(file.getOriginalFileName());
			String uuid = UUID.randomUUID().toString().replace("-", "");
			StringBuilder bu = new StringBuilder(filePath).append("/").append(uuid).append("_").append(sufffix);
			File newFile = new File(IMAGE_PATH + bu.toString());
			File oldFile = file.getFile();
			oldFile.renameTo(newFile);
			//上传小图
			ImageUtils.scaleSmall(newFile, newFile.getAbsolutePath(), sufffix, FileSize.S200X150.getWidth(), FileSize.S200X150.getHeight());
			json.put("success", true);
			json.put("src", "http://" + DOMAIN_PREFIX + bu.toString());
		} catch (Exception e) {
			log.error("文件上传失败", e);
			json.put("success", false);
			json.put("message", "文件上传失败");
		}
	}
	
	
	/**
	 * 上传文件验证
	 * @author liangxp
	 * Date:2017年7月6日下午3:15:11 
	 *
	 * @param uploadFile
	 * @param fileSize
	 * @param json
	 * @return
	 */
	private boolean validate(UploadFile uploadFile, FileSize fileSize, JSONObject json) {
		try {
			String sufffix = FileUtils.getSuffixNoSpot(uploadFile.getOriginalFileName());
			if(!fileSize.getFileType().contains(sufffix.toLowerCase())){
				log.error("请上传"+fileSize.getFileType()+"类型的文件");
				json.put("success", false);
				json.put("message", "请上传"+fileSize.getFileType()+"类型的文件");
				return false;
			}
			
			if(uploadFile.getFile().length() > fileSize.getSize()){
				log.error("上传的文件太大，不能超过"+fileSize.getSize());
				json.put("success", false);
				json.put("message", "上传的文件太大，不能超过"+fileSize.getSize());
				return false;
			}
			
			Image src = ImageIO.read(uploadFile.getFile());
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			if(fileSize.getWidth()!=0){
				if(width>fileSize.getWidth() || height> fileSize.getHeight()){
					log.error("请上传"+fileSize.getDesc()+"的尺寸的文件");
					json.put("success", false);
					json.put("message", "请上传"+fileSize.getDesc()+"的尺寸的文件");
					return false;
				}
			}
		} catch (IOException e) {
			log.error("文件上传失败", e);
			json.put("success", false);
			json.put("message", "文件上传失败");
		}
		return true;
	}
	
	/**
	 * 获取文件类型
	 * @author liangxp
	 * Date:2017年7月6日下午3:15:26 
	 *
	 * @param imgType
	 * @return
	 */
	private FileSize getFileSize(String imgType) {
		FileSize fileSize = FileSize.S000X000;
		if(StringUtils.isBlank(imgType))return fileSize;
		switch (imgType) {
			case "scrol": fileSize = FileSize.S000X000;break;	//5M
			case "cert": fileSize = FileSize.S000X000;break;
			case "company": fileSize = FileSize.S000X000;break;
			case "truck": fileSize = FileSize.S000X000;break;
			case "sign": fileSize = FileSize.S000X000;break;
		}
		return fileSize;
	}
	
	

}