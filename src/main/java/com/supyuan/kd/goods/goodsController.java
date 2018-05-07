package com.supyuan.kd.goods;

import com.supyuan.component.base.BaseProjectController;
import com.supyuan.jfinal.component.annotation.ControllerBind;

/**
 * 货物
 * 
 * @author dingfei
 *
 * @date 2017年11月9日 下午12:04:36
 */
@ControllerBind(controllerKey = "/kd/goods")
public class goodsController extends BaseProjectController {

	private static final String path = "/pages/kd/goods/";

	public void index() {
		renderJsp(path + "index.jsp");
	}

	public void add() {

		renderJsp(path + "add.jsp");

	}
	
	public void saveProduct() {
		KdProduct kdProduct=getModel(KdProduct.class,true);
		kdProduct.set("create_time", getNow());
		kdProduct.set("company_id", 1);
		kdProduct.save();
		
	}

}
