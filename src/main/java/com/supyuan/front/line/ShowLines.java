package com.supyuan.front.line;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.front.branch.LogisticsNetwork;
import com.supyuan.front.branch.NetWorkSvc;
import com.supyuan.front.company.Company;
import com.supyuan.front.company.CompanySvc;
import com.supyuan.front.index.IndexLine;
import com.supyuan.front.logisticspark.LogisticsPark;
import com.supyuan.front.logisticspark.LogisticsParkSvc;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.modules.addressbook.Address;
import com.supyuan.util.UUIDUtil;

@ControllerBind(controllerKey = "/front/line/preview")

public class ShowLines extends BaseProjectController {

	private final String path = "/pages/front/personal/";
	/**
	 * 专线列表
	 */
	public void index() {
		SessionUser user = getSessionSysUser();
		Integer userid=user.getUserId();
		//SQLUtils sql = new SQLUtils("FROM logistics_line t where 1 = 1 and t.is_live=1");
		//sql.append(" and  company_id=getCorpIdByUid("+userid+") order by is_sale desc");
		//sql.setAlias("t");
		//Page<IndexLine> page = IndexLine.dao.paginate(getPaginator(), "SELECT " + "id,(SELECT getBookLongRegion (n.sub_addr_uuid) from logistics_network n left join logistics_line l  on l.network_id = n.id) as netadd, getLineRegion (from_city_code,from_region_code) AS from_addr,"+"getLineRegion (to_city_code,to_region_code) AS to_addr," + "getCompanyName (company_id) AS corpname," + "price_heavy," + "price_small," + "survive_time,is_sale," + "frequency", sql.toString());
		List<IndexLine> page=new LineSvc().getPageLine(userid);
		setAttr("page", page);
		System.out.println(getAttr("page").toString()+"**********获得属性");
		setAttr("curr", 9);
		renderJsp(path  + "logisticspreview.jsp");
	}
	
	/**
	 * 查询修改专线
	 */
	public void getLine(){
		SessionUser user = getSessionSysUser();
		try{
			CompanySvc svc = new CompanySvc();
			Company company=null;
			if(svc!=null){
			 company = svc.getCompany(user.getUserId());
		}
		String id = getPara("id");
		IndexLine line=new LineSvc().getLineById(Integer.parseInt(id));
		List<LogisticsNetwork> list=new NetWorkSvc().getNetWorkList(user);
		List<LogisticsPark> logisticsParks= new LogisticsParkSvc().findAllPark();
		setAttr("logisticsParks", logisticsParks);
		setAttr("list", list);
		setAttr("line", line);
		renderJsp(path  + "editline.jsp");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 修改专线
	 */
	public void updateLine(){
		JSONObject json = new JSONObject();
		try {
			// 获取登录session用户
			SessionUser user = getSessionSysUser();
			Company company = new CompanySvc().getCompany(user.getUserId());
			String lineId=getPara("lineid");
			final int networkId=getParaToInt("networkId"); //所属网点
			final String fromCityCode = getPara("fromCityCode");// 出发城市code
			final String fromRegionCode = getPara("fromRegionCode");// 出发地区code
			final String toCityCode = getPara("toCityCode");// 到达城市code
			final String toRegionCode = getPara("toRegionCode");// 到达地区code
			final Integer survive_time = getParaToInt("survive_time",0);
			final String price_heavy = getPara("price_heavy");
			final String price_small = getPara("price_small");
			final Integer frequency = getParaToInt("frequency", 1);
			final Integer parkId=getParaToInt("parkId"); //所属物流园ID
			final String position = getPara("address");
			final String startingprice = getPara("startingprice","0");
			int isSale = getParaToInt("isSale");
			Address address=new Address();
			address.set("uuid", UUIDUtil.UUID());
			address.set("region_code", fromCityCode);
			address.set("create_time", getNow());
			address.set("user_id", user.getUserId());
			// 保存专线信息
			IndexLine indexLine = new IndexLine();
			indexLine.set("id", lineId);
			indexLine.set("network_id", networkId);
			indexLine.set("from_city_code", fromCityCode);
			indexLine.set("from_region_code", fromRegionCode);
			indexLine.set("to_city_code", toCityCode);
			indexLine.set("to_region_code", toRegionCode);
			indexLine.set("survive_time", survive_time);
			indexLine.set("price_heavy", price_heavy);
			indexLine.set("price_small", price_small);
			indexLine.set("frequency", frequency);
			indexLine.set("logistics_park_id", parkId);
			indexLine.set("company_id", company.getInt("id"));
			indexLine.set("create_time", getNow());
			indexLine.set("address", position);
			indexLine.set("is_sale", isSale);
			indexLine.set("is_live", 1);
			indexLine.set("starting_price", startingprice);
			boolean updateLine=new LineSvc().updateLines(address, indexLine);
			if (updateLine) {
				json.put("state", "SUCCESS");
				json.put("message", "修改成功");
				renderJson(json.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
			json.put("state", "FAILED");
			json.put("message", "保存失败");
			renderJson(json.toString());
		}

	}
	
	
	public void delLine(){
		
		try{
			JSONObject json = new JSONObject();
			String lineId= getPara("lineid");
			if(lineId!=null){
				int update = Db.update("update logistics_line  set is_live=0  where id="+lineId);
				if(update>0){
					json.put("state", "SUCCESS");
					json.put("message", "删除成功");
					renderJson(json.toString());
				}else{
					json.put("state", "FAILED");
					json.put("message", "删除失败");
					renderJson(json.toString());
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
