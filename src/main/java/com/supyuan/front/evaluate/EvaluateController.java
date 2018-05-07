package com.supyuan.front.evaluate;

import java.util.List;

import com.supyuan.component.base.BaseProjectController;
import com.supyuan.front.goods.GoodsSvc;
import com.supyuan.front.goods.Order;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;

/**
 * 订单评价
 * 
 * @author dingfei
 *
 * @date 2017年8月17日 上午10:58:41
 */
@ControllerBind(controllerKey = "/front/evaluate")
public class EvaluateController extends BaseProjectController {

	private static final String path = "/pages/front/evaluate/";

	public void index() {
		String fromregioncode = getPara("fromregioncode");
		String toregioncode = getPara("toregioncode");
		Integer orderid = getParaToInt("orderid");
		if (null != orderid) {
			setAttr("fromregioncode", fromregioncode);
			setAttr("toregioncode", toregioncode);
			Order order = new GoodsSvc().getorderid(orderid);
			if (order != null) {
				setAttr("order", order);
			}
		}
		List<CritiItem> goodItems = new CritiItemSvc().getList(1);
		List<CritiItem> disparityItems=new CritiItemSvc().getList(2);
		setAttr("goodItems", goodItems);
		setAttr("disparityItems", disparityItems);
		renderJsp(path + "evaluate.jsp");

	}

	/**
	 * 保存评价信息
	 */
	public void saveCriti() {
		// 登录用户
		SessionUser user = getSessionSysUser();
		//订单ID
		String orderId=getPara("orderid");
		// 五星指数
		String critiStars = getPara("critiStars");
		// 评价内容
		String critiRemark = getPara("critiRemark");
		//评价标签Id
		String itemid = getPara("itemid");
		String[] arr = itemid.split(",");
		List<String> list = java.util.Arrays.asList(arr);
		CritiMark cMark = getModel(CritiMark.class);
		cMark.set("shipping_order_id", orderId);
		cMark.set("mark_user_id", user.getUserId());
		cMark.set("criti_stars", critiStars);
		cMark.set("criti_remark", critiRemark);
		cMark.set("from_yw", 1);
		cMark.set("create_time", getNow());
		if (cMark.save()) {
			new CritiItemSvc().saveCritiMarkItem(list, cMark.getInt("id"));
			setAttr("state", "SUCCESS");
			setAttr("msg", "评价成功！");
			renderJson();
			return;
		}else{
			setAttr("state", "FAILED");
			setAttr("msg", "评价失败！");
			renderJson();
			return;
			
		}

	}

}
