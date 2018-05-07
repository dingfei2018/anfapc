package com.supyuan.front.infotransactions;
import com.jfinal.log.Log;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.jfinal.component.annotation.ControllerBind;
/**
 * 订单
 * 
 * @author chendekang
 *
 */
@ControllerBind(controllerKey = "/front/infotransactions")
public class InfotransactionsController extends BaseProjectController {
	private static final Log log = Log.getLog(InfotransactionsController.class);
	private static final String path = "/pages/front/";
	//零担跳转
	public void index() {

		renderJsp(path + "xianshigengduo.jsp");
	}
}
