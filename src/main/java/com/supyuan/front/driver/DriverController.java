package com.supyuan.front.driver;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.jfinal.component.annotation.ControllerBind;
/**
 * 订单
 * 
 * @author chendekang
 *
 */
@ControllerBind(controllerKey = "/driver")
public class DriverController extends BaseProjectController {
	private static final String path = "/pages/front/driver/";
	
	public void index() {
		renderJsp(path + "driver.jsp");
	}
	
	public void info() {
		renderJsp(path + "driverinfo.jsp");
	}

}
