package com.supyuan.kd.line;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.front.branch.LogisticsNetwork;
import com.supyuan.front.branch.NetWorkSvc;
import com.supyuan.front.company.Company;
import com.supyuan.front.company.CompanySvc;
import com.supyuan.front.index.IndexLine;
import com.supyuan.front.line.LineSvc;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.kd.network.NetWork;
import com.supyuan.modules.addressbook.Address;
import com.supyuan.system.line.SysLine;
import com.supyuan.util.UUIDUtil;

/**
 * 专线控制器
 *
 */
@ControllerBind(controllerKey = "/kd/line")
public class LineController  extends BaseProjectController{
	private static final String path = "/pages/kd/line/";
	com.supyuan.system.line.LineSvc svc = new com.supyuan.system.line.LineSvc();
	public void index() {
		SessionUser user = getSessionSysUser();
		List<LogisticsNetwork> networks= new NetWorkSvc().getNetWorkListBuUserId(user);
		setAttr("networks", networks);
		renderJsp(path + "index.jsp");
	}
	
	public void search() {
		SessionUser user = getSessionSysUser();
		Paginator paginator = getPaginator();
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		KdLineSearchModel model=KdLineSearchModel.getBindModel(KdLineSearchModel.class,getRequest());
		Page<SysLine> Lines=new KdLineSvc().getKdLinePage(paginator,model,user);
		renderJson(Lines);
	}
	
	
	/**
	 * 跳转到新增页面
	 */
	public void add() {
		setAttr("citySelect", svc.selectcity(0));
		renderJsp(path + "add.jsp");
	}
	
	/**
	 * 创建专线的方法
	 */
	public void save() {
		JSONObject json = new JSONObject();
		try {
			// 获取登录session用户
			SessionUser user = getSessionSysUser();
			//Company company =new CompanySvc().getCompany(user.getUserId());
			final String networkIds=getPara("networkId"); //出发网点，多选
			final Integer arriveNetworkId=getParaToInt("arriveNetworkId"); //到达网点
			final String fromCityCode = getPara("fromCityCode");// 出发城市code
			//final String fromRegionCode = getPara("fromRegionCode");// 出发地区code
			final String toCityCode = getPara("toCityCode");// 到达城市code
			//final String toRegionCode = getPara("toRegionCode");// 到达地区code
			//final Integer survive_time = getParaToInt("survive_time",0);
			final String price_heavy = getPara("price_heavy","0");
			final String price_small = getPara("price_small","0");
			final Integer frequency = getParaToInt("frequency", 1);
			//final String position = getPara("address");
			final String startingprice = getPara("startingprice","0");
			//final Integer isSale = getParaToInt("isSale");
			//final String parkId=getPara("parkId"); //所属物流园ID
		   Address address = new Address();
		   address.set("uuid", UUIDUtil.UUID());
		   address.set("region_code", fromCityCode);
		   address.set("create_time", getNow());
		   address.set("user_id", user.getUserId());
			List<IndexLine> list=new ArrayList<>();
			// 保存专线信息
			String[] citySplit = toCityCode.split(",");
			String[] networkIdSplit = networkIds.split(",");
			//String[] parkIdSplit = parkId.split(",");
			for (int j = 0; j < networkIdSplit.length; j++) {
				System.out.println(networkIdSplit[j]);
				for (int i = 0; i < citySplit.length; i++) {
					System.out.println(citySplit[i]);
					//List<IndexLine> checkLine=new LineSvc().checkLine(fromCityCode, null, toCityCode, Integer.parseInt(networkIdSplit[j]));
					IndexLine checkLine = new KdLineSvc().checkLine(fromCityCode, citySplit[i], networkIdSplit[j]);
					//if (checkLine!=null && checkLine.size()>0) {
					if (checkLine!=null) {
						json.put("state", "FAILED");
						json.put("message",checkLine.getStr("network")+ "已经存在"+checkLine.getStr("formcity")+"到"+checkLine.getStr("tocity")+"的专线!请选择其他专线");
						renderJson(json.toString());
						return;
					}
					Integer parkId = NetWork.dao.findById(networkIdSplit[j]).getInt("park_id");
					IndexLine indexLine = new IndexLine();
					indexLine.set("network_id", networkIdSplit[j]);
					indexLine.set("arrive_network_id", arriveNetworkId);
					indexLine.set("from_city_code", fromCityCode);
					//indexLine.set("from_region_code", fromRegionCode);
					indexLine.set("to_city_code", citySplit[i]);
					//indexLine.set("to_region_code", toRegionCode);
				    //indexLine.set("survive_time", survive_time);
					indexLine.set("price_heavy", price_heavy);
					indexLine.set("price_small", price_small);
					indexLine.set("frequency", frequency);
					indexLine.set("logistics_park_id",parkId);
					indexLine.set("company_id", user.getCompanyId());
					indexLine.set("create_time", getNow());
					//indexLine.set("address", position);
					//indexLine.set("is_sale", isSale);
					indexLine.set("is_live", 1);
					indexLine.set("starting_price", startingprice);
					list.add(indexLine);
				}
				
			}
			
			
			boolean savaLine=new LineSvc().saveLines(address,list);
			if (savaLine) {
				json.put("state", "SUCCESS");
				json.put("message", "保存成功");
				renderJson(json);
			} 
		} catch (Exception e) {
			e.printStackTrace();
			json.put("state", "FAILED");
			json.put("message", "保存失败");
			renderJson(json);
		}

	}
	
	
	
	/**
	 * 進入修改頁面
	 */
	public void update() {
		SessionUser user = getSessionSysUser();
		String id=getPara("id");
		SysLine sysLine = new KdLineSvc().getSysLine(id,user);
		setAttr("sysLine", sysLine);
		List<LogisticsNetwork> networks= new NetWorkSvc().getNetWorkListByCityCode(user, sysLine.getStr("from_city_code"));
		List<LogisticsNetwork> arriveNetworks= new NetWorkSvc().getNetWorkListByCityCode(user, sysLine.getStr("to_city_code"));
		setAttr("networks", networks);
		setAttr("arriveNetworks", arriveNetworks);
		renderJsp(path + "changeline.jsp");
	}
	
	
	/**
	 * 修改
	 */
	public void updateLine(){
		JSONObject json = new JSONObject();
		try {
			int id = Integer.parseInt(getPara("id"));
			String networkId = getPara("networkId");
			String arriveNetworkId = getPara("arriveNetWorkId");
			String priceHeavy = getPara("price_heavy");
			String priceSmall = getPara("price_small");
			String startingPrice = getPara("startingprice");
			LogisticsNetwork netWork = new NetWorkSvc().getNetWorkNetworkId(Integer.parseInt(networkId));
			int logisticsParkId = netWork.get("park_id");
			
			SysLine sysLine = new SysLine().findById(id);
			boolean flag = sysLine
					.set("network_id", networkId)
					.set("arrive_network_id", arriveNetworkId==""?0:arriveNetworkId)
					.set("price_heavy", priceHeavy==""?0:priceHeavy)
					.set("price_small", priceSmall==""?0:priceSmall)
					.set("starting_price", startingPrice==""?0:startingPrice)
					.set("logistics_park_id", logisticsParkId)
					.update();
			
			if(flag==true){
				json.put("state", "SUCCESS");
				json.put("msg", "修改成功");
			}else{
				json.put("state", "FAILED");
				json.put("msg", "修改失败");
				
			}
			renderJson(json.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据出发地获取网点信息
	 */
	public void getNetWork() {
		SessionUser user = getSessionSysUser();
		String fromCityCode = getPara("fromCityCode");// 出发城市code
		List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListByCityCode(user, fromCityCode);
		renderJson(netWorkList);
		
	}
	
	
	/**
	 * 根据出发地获取网点信息
	 */
	public void getArriveNetWork() {
		SessionUser user = getSessionSysUser();
		String toCityCodeArr = getPara("toCityCode");// 出发城市code
		String[] toCityCodes = toCityCodeArr.split(",");
		List<LogisticsNetwork> netWorkList = new ArrayList<>();
		for (String cityCode : toCityCodes) {
			List<LogisticsNetwork> netWorkListByCityCode = new NetWorkSvc().getNetWorkListByCityCode(user, cityCode);
			for (LogisticsNetwork logisticsNetwork : netWorkListByCityCode) {
				netWorkList.add(logisticsNetwork);
			}
		}
		renderJson(netWorkList);
		
	}
	

	/**
	 * 删除
	 */
	public void delLine(){
		
		 JSONObject json = new JSONObject();
			try{
				String lineId=getPara("lineId");
				boolean flag=new KdLineSvc().deleteLineById(lineId);
					if(flag==true){
						json.put("state", "SUCCESS");
						json.put("msg", "删除成功");
					}else{
						json.put("state", "FAILED");
						json.put("msg", "删除失败");
						
					}
				renderJson(json.toString());
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	

	 /**
	  * 校验专线是否网点唯一
	  */
	 public void checkLine(){
		 String networkId=getPara("networkId");
		 String from_city_code=getPara("from_city_code");
		 String to_city_code=getPara("to_city_code");
		IndexLine checkLine = new KdLineSvc().checkLine(from_city_code, to_city_code, networkId);
		 boolean flag=false;
		 if(checkLine == null ){
			 flag=true;
		 }
		 renderJson(flag);
	 }
	
	public void getLineJsonByNetWorkId(){
		String netWorkId=getPara("netWorkId");
		renderJson(new KdLineSvc().getLineListByNetWorkId(netWorkId));
	}
	
	public void getCityByNetWorkId(){
		String netWorkId=getPara("netWorkId");
		List<SysLine> list = new ArrayList<SysLine>();
		if(StringUtils.isBlank(netWorkId)){
			renderJson(list);
			return;
		}
		String[] split = netWorkId.split(",");
		NetWork findWork = new NetWorkSvc().findWorkAddByWorkId(Integer.parseInt(split[0]));
		SysLine line = new SysLine();
		String addrName = findWork.get("addrName");
		String code = findWork.get("region_code");
		//String city = code.substring(0,4)+"00";
		line.set("from_city_code", code);
		line.put("fromAdd", addrName);
		list.add(line);
		List<SysLine> netWork = new KdLineSvc().getCityByNetWorkId(netWorkId);
		for (SysLine sysLine : netWork) {
			if(!addrName.equals(sysLine.get("fromAdd")))list.add(sysLine);
		}
		renderJson(list);
	}
	
	public void lineview(){
		SessionUser user = getSessionSysUser();
		String id = getPara("id");
		SysLine sysLine = new KdLineSvc().getSysLine(id, user);
		setAttr("sysLine", sysLine);
		renderJsp(path + "lineview.jsp");
	}
}
