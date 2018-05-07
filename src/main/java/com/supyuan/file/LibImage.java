package com.supyuan.file;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 
 * 图片
 * 
 * @author liangxp
 *
 * Date:2017年6月30日上午10:41:53 
 * 
 * @email liangxp@anfawuliu.com
 */
@ModelBind(table = "library_image")
public class LibImage extends BaseProjectModel<LibImage> {

	private static final long serialVersionUID = 1L;
	
	public static final LibImage dao = new LibImage();

}
