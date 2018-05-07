package com.supyuan.front.logisticsservice;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.jfinal.component.annotation.ControllerBind;
/**
 * 物流公司服务项
 */
@ControllerBind(controllerKey = "/front/logistics")
public class LogisticsController extends BaseProjectController {
	private static final String path = "/pages/front/logisticsservice/";
	
	
	public void aboutus() {
		renderJsp(path + "aboutus.jsp");
	}
	
	
	
	public void contactus() {
		renderJsp(path + "contactus.jsp");
	}
	
	
	
	public void commonproblems() {
		renderJsp(path + "commonproblems.jsp");
	}
	
	public void joinus() {
		renderJsp(path + "joinus.jsp");
	}
}
