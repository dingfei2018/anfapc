package com.supyuan.modules.express;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.supyuan.component.base.BaseProjectController;
import com.supyuan.component.base.BaseResult;
import com.supyuan.jfinal.component.annotation.ControllerBind;

/**
 * 快递单号查询
 * @author liangxp
 *
 * Date:2017年9月21日下午4:13:50 
 * 
 * @email liangxp@anfawuliu.com
 */
@ControllerBind(controllerKey = "/express")
public class KdnaioController extends BaseProjectController {
	
	private static final String path = "/pages/front/shipped/";

	public void index() {
		renderJsp(path + "expresst.jsp");
	}
	
	
	public void kd() {
		renderJsp(path + "express.jsp");
	}
	
	
	/**
	 * 快递鸟快递查询
	 * @author liangxp
	 * Date:2017年9月21日下午4:30:40 
	 *
	 */
	public void kdnaio(){
		String expCode = getPara("expCode");
		String expNo = getPara("expNo");
		String res = "{\"Success\": false}";
		if(StringUtils.isNotBlank(expCode)&&StringUtils.isNotBlank(expNo)){
			try {
				res = KdniaoQueryAPI.getOrderTracesByJson(expCode, expNo);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		renderJson(res);
	}
	
	
	/**
	 * 货运单号查询
	 * @author liangxp
	 * Date:2017年10月18日下午1:48:38 
	 *
	 */
	public void hydcx(){
		String expNo = getPara("expNo");
		BaseResult result = new BaseResult();
		if(StringUtils.isNotBlank(expNo)){
			List<Locations> list = new ArrayList<Locations>();
			if(expNo.equals("1")){
				String data = "[[113.332135,33.681472],[114.27696,30.650292],[115.067975,27.091503]]";
				list.add(new Locations("平顶山", new Date(), 0));
				list.add(new Locations("吉安", new Date(),1));
				result.putData("locations", data);
				result.putData("lineDesc", "平顶山->吉安");
			}else{
				String data = "[[108.96095,34.367786],[108.917005,30.96522],[110.147474,27.514944],[108.477552,22.904613]]";
				list.add(new Locations("西安", new Date(), 0));
				list.add(new Locations("南宁", new Date(),1));
				result.putData("locations", data);
				result.putData("lineDesc", "西安->南宁");
			}
			result.putData("list", list);
		}
		renderJson(result);
	}
	

}
