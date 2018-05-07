package com.supyuan.mkd.truck;


import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.MBaseProjectController;
import com.supyuan.component.base.MBaseResult;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.kd.truck.Truck;
import com.supyuan.mkd.common.MSessionUser;

/**
 * 
 * @author liangxp
 *
 * Date:2018年1月26日下午2:54:07 
 * 
 * @email liangxp@anfawuliu.com
 */
@ControllerBind(controllerKey = "/mkd/truck")
public class TruckController  extends MBaseProjectController  {
	
	
	public void list(){
		MBaseResult res = new MBaseResult();
		MSessionUser mSessionUser = getMSessionUser();
		Paginator paginator = getPaginator();
		if(paginator.getPageSize()>20){
			paginator.setPageSize(20);
		}
		Page<Truck> trucks = new TruckSvc().findTruckList(paginator, mSessionUser.getCompanyId());
		res.putData("trucks", transforMpage(trucks));
		renderJson(res);
	}

}
