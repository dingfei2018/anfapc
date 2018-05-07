package com.supyuan.kd.home;




import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.plugin.activerecord.Record;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.component.base.BaseResult;
import com.supyuan.component.base.ResultType;
import com.supyuan.component.util.JFlyFoxUtils;
import com.supyuan.front.branch.LogisticsNetwork;
import com.supyuan.front.branch.NetWorkSvc;
import com.supyuan.front.modify.UserSvc;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.kd.loading.LoadingSvc;
import com.supyuan.system.user.SysUser;

/**
 * 首页控制器
 * @author chenan
 * Date:2017年12月14日 上午9:41:08 
 */
@ControllerBind(controllerKey = "/kd")
public class HomeController  extends BaseProjectController  {
	
	private static final String path = "/pages/kd/";
	
	public void index() {
		SessionUser user=getSessionSysUser();
	/*	List<LogisticsNetwork> networks= new NetWorkSvc().getNetWorkListBuUserId(user);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar.setTime(calendar.getTime());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date firstDayOfMonth = calendar.getTime();  
		String firstDay=format.format(firstDayOfMonth);
		String lastDay=format.format(calendar2.getTime());
		
		setAttr("firstDay", firstDay);
		setAttr("lastDay", lastDay);
		setAttr("networks", networks);*/
		renderJsp(path + "index.jsp");
	}
	/**
	 * 运营概况
	 */
	public void operation() {
		SessionUser user=getSessionSysUser();
		
		List<LogisticsNetwork> networks= new NetWorkSvc().getNetWorkListBuUserId(user);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar.setTime(calendar.getTime());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date firstDayOfMonth = calendar.getTime();  
		String firstDay=format.format(firstDayOfMonth);
		String lastDay=format.format(calendar2.getTime());
		
		setAttr("firstDay", firstDay);
		setAttr("lastDay", lastDay);
		setAttr("networks", networks);
		renderJsp(path + "operations.jsp");
		
	}
	
	
	
	
	public void getData(){
		SessionUser user=getSessionSysUser();
		int companyId=user.getCompanyId();
		String netWorkIds=getPara("netWorkId");
		String startTime=getPara("startTime");
		String endTime=getPara("endTime");
		
		if(netWorkIds.equals("")){
			netWorkIds=user.toNetWorkIdsStr();
		}
		
		HashMap<String, Record> dataTemp=null;
		HashMap<String, Object> countShipCity=null;
		HashMap<String, Object> stock=null;
		
			dataTemp=new HomeSvc().getData(companyId, netWorkIds, startTime, endTime);
			countShipCity=new HomeSvc().getCountShipCity(startTime, endTime, netWorkIds);
			stock=new HomeSvc().getStockData(startTime, endTime, netWorkIds);
		
		HashMap<String,Object> data=new HashMap<>();
		data.put("data", dataTemp);
		
		if(endTime.equals("")){
			endTime=getNow();
		}
		List<Record> countShip=new HomeSvc().getCountShipDataByEndTime(endTime, netWorkIds);
		
		data.put("countShipCity", countShipCity);
		data.put("countShip", countShip);
		data.put("stock", stock);
		renderJson(data);
	}
	
	
	public void password() {
		SessionUser user=getSessionSysUser();
		setAttr("mobile", user.getMobile());
		renderJsp(path + "password.jsp");
	}
	
	
	public void modifypwd(){
		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		SessionUser user = getSessionSysUser();
		try {
			String opassword = getPara("opassword");
			String npassword = getPara("npassword");
			String rnpassword = getPara("rnpassword");
			if(StringUtils.isNotBlank(opassword)&&StringUtils.isNotBlank(npassword)&&StringUtils.isNotBlank(rnpassword)){
				if(!npassword.equals(rnpassword)){
					baseResult.setResultType(ResultType.PASSWORD_NOT_SAME);
				}else{
					String decryptPassword = JFlyFoxUtils.passwordEncrypt(opassword);
					if(!user.getPassword().equals(decryptPassword)){
						baseResult.setResultType(ResultType.OLDPASSWORD_IS_WRONG);
						renderJson(baseResult);
						return;
					}
					String newpwd = JFlyFoxUtils.passwordEncrypt(npassword);
					int res = new UserSvc().updateUserPassWord(user.getMobile(), newpwd);
					if(res>0){
						user.setPassword(newpwd);
						baseResult.setResultType(ResultType.SUCCESS);
					}
				}
			}else{
				baseResult.setResultType(ResultType.PARAM_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(baseResult);
	}
	
	

}
