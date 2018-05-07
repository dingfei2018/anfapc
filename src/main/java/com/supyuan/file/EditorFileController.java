package com.supyuan.file;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.log.Log;
import com.jfinal.upload.UploadFile;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.system.user.SysUser;
import com.supyuan.util.Config;
import com.supyuan.util.FileUtils;

@ControllerBind(controllerKey = "/file/kedit")
public class EditorFileController extends BaseProjectController {
	
	private static final Log log = Log.getLog(EditorFileController.class);
	
	private static final String FILENAME = "fileName";
	
	private static final String DOMAIN_PREFIX = Config.getStr("IMAGE.DOMAIN_PREFIX");//图片文件访问域名前缀
	
	public void upload() {
		JSONObject json = new JSONObject();
		SessionUser sessionSysUser = getSessionSysUser();
		if(sessionSysUser==null){
			json.put("error", 1);
			json.put("message", "未登陆文件上传失败");
			renderJson(json.toString());
			return;
		}
		String filePath = getUploadFilePath(sessionSysUser.getUserId());
		UploadFile uploadFile = null;
		try {
			uploadFile = getFile(FILENAME, filePath);
			if(validate(uploadFile, FileSize.S000X000, json)){
				json.put("error", 0);
				json.put("url", "http://" + DOMAIN_PREFIX + filePath + "/" + uploadFile.getFileName());
			}
		}catch (Exception e) {
			log.error("文件上传失败", e);
			json.put("error", 1);
			json.put("message", "文件上传失败");
		}
		renderJson(json.toString());
	}
	
	
	//生成文件路径
	private String getUploadFilePath(Integer userId){
		StringBuilder tempFileName = new StringBuilder().append("/kfile").append(userId);
		return tempFileName.toString();
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
				json.put("error", 1);
				json.put("message", "请上传"+fileSize.getFileType()+"类型的文件");
				return false;
			}
			
			if(uploadFile.getFile().length() > fileSize.getSize()){
				log.error("上传的文件太大，不能超过"+fileSize.getSize());
				json.put("error", 1);
				json.put("message", "上传的文件太大，不能超过"+fileSize.getSize());
				return false;
			}
			
			Image src = ImageIO.read(uploadFile.getFile());
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			if(fileSize.getWidth()!=0){
				if(width>fileSize.getWidth() || height> fileSize.getHeight()){
					log.error("请上传"+fileSize.getDesc()+"的尺寸的文件");
					json.put("error", 1);
					json.put("message", "请上传"+fileSize.getDesc()+"的尺寸的文件");
					return false;
				}
			}
		} catch (IOException e) {
			log.error("文件上传失败", e);
			json.put("error", 1);
			json.put("message", "文件上传失败");
		}
		return true;
	}

}