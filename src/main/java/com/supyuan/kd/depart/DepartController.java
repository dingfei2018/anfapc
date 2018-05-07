package com.supyuan.kd.depart;

import com.supyuan.component.base.BaseProjectController;
import com.supyuan.jfinal.component.annotation.ControllerBind;
@ControllerBind(controllerKey = "/kd/depart")
public class DepartController extends BaseProjectController {

	private static final String path = "/pages/kd/depart/";

	public void index() {
		
		
		
		renderJsp(path + "list.jsp");
	}
	

}
