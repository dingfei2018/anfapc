package com.supyuan.front.photo;

import com.supyuan.component.base.BaseProjectController;
import com.supyuan.jfinal.component.annotation.ControllerBind;


/**
 * 合照
 * 
 * @author liangxp
 *
 * Date:2017年7月3日下午2:28:17 
 * 
 * @email liangxp@anfawuliu.com
 */
@ControllerBind(controllerKey = "/photo")
public class PhotoController extends BaseProjectController {

	private static final String path = "/pages/front/photo/";

	
	/**
	 * 司机合照
	 * @author liangxp
	 *
	 * Date:2017年7月3日下午3:26:19
	 */
	public void driver() {
		setAttr("photo", "http://192.168.1.9/img/20170703/10/3bb503924a074f2d9ffc82d9d81433cc.png");
		renderJsp(path + "driver-info.jsp");
	}
	
	
	/**
	 * 车队合照
	 * @author liangxp
	 *
	 * Date:2017年7月3日下午3:26:30
	 */
	public void fleet() {
		setAttr("photo", "http://192.168.1.9/img/20170703/10/3bb503924a074f2d9ffc82d9d81433cc.png");
		renderJsp(path + "driver-info.jsp");
	}
	
}
