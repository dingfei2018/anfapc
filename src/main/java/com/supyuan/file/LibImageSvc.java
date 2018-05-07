package com.supyuan.file;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.supyuan.jfinal.base.BaseService;

/**
 * 图片保存
 * 
 * @author liangxp
 *
 * Date:2017年6月30日下午4:20:49 
 * 
 * @email liangxp@anfawuliu.com
 */
public class LibImageSvc extends BaseService {

	private final static Log log = Log.getLog(LibImageSvc.class);
	
	public boolean addLibImage(LibImage libImage) {
		return libImage.save();
	}
	
	public boolean addLibImages(List<LibImage> libImages) {
		int[] batchSave = Db.batchSave(libImages, libImages.size());
		return batchSave.length==libImages.size();
	}
	
	public List<LibImage> getLibImages(String gid) {
		if(StringUtils.isBlank(gid)){
			return new ArrayList<LibImage>();
		}
		List<LibImage> images = LibImage.dao
				.find("SELECT CONCAT('http://', host, image) img , tag, `order` FROM library_image where gid=? order by `order` asc", gid);
		return images;
	}
}
