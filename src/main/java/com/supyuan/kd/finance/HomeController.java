package com.supyuan.kd.finance;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.front.branch.LogisticsNetwork;
import com.supyuan.front.branch.NetWorkSvc;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.util.DateUtils;

/**
 * 财务首页
 * @author liangxp
 *
 * Date:2018年1月6日下午4:53:09 
 * 
 * @email liangxp@anfawuliu.com
 */

@ControllerBind(controllerKey = "/kd/finance")
public class HomeController extends BaseProjectController {
	
	
	private static final String path = "/pages/kd/finance/";
	
	public void index(){
		SessionUser user = getSessionSysUser();
		List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
		setAttr("networks", netWorkList);
		setAttr("firstDay", DateUtils.getCurrMonthFirstDay());
		setAttr("currDay", DateUtils.format(new Date()));
		renderJsp(path+"financehome.jsp");
	}
	
	
	/**
     * 财务首页搜索
     * @author liangxp
     */
    public void search(){
    	SessionUser user = getSessionSysUser();
		Paginator paginator = getPaginator();
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		HomeSearchModel model = HomeSearchModel.getBindModel(HomeSearchModel.class, getRequest());
		HomeFinanceSvc homeFinanceSvc = new HomeFinanceSvc();
		Page<HomeFinanceModel> datas = homeFinanceSvc.queryHomeFinanceList(paginator, user, model);
		HomeFinanceAllModel findTotal = homeFinanceSvc.findTotal(user, model);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", datas);
		data.put("head", findTotal);
		renderJson(data);
    }
	

}
