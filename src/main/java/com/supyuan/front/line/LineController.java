package com.supyuan.front.line;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.front.company.Company;
import com.supyuan.front.company.CompanySvc;
import com.supyuan.front.index.IndexLine;
import com.supyuan.front.logisticspark.LogisticsPark;
import com.supyuan.front.logisticspark.LogisticsParkSvc;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.modules.addressbook.Address;
import com.supyuan.util.UUIDUtil;

/**
 * 查询线路
 * 
 * @author dingfei <2017年7月5日>
 *
 */
@ControllerBind(controllerKey = "/front/line")
public class LineController extends BaseProjectController {
	private static final String path = "/pages/front/";

	/**
	 * 首页找专线和三方搜索
	 */
	public void searchIndexLine() {

		try {
			String fromCityCode = getPara("fromCityCode"); // 出发城市code值
			String fromRegionCode = getPara("fromRegionCode"); // 出发地区code值
			String toCityCode = getPara("toCityCode"); // 到达城市code值
			String toRegionCode = getPara("toRegionCode"); // 到达地区code值
			String parkName = getPara("parkName"); // 物流园名称
			String columnName = getPara("column");// 排序列
			String orderby = getPara("orderby"); // 升序/降序
			Paginator paginator = getPaginator();
			paginator.setPageSize(10);
			if (paginator.getPageSize() > 10) {
				paginator.setPageSize(10);
			}
			if (paginator.getPageNo() < 1) {
				paginator.setPageNo(1);
			}
			Page<IndexLine> lines = new LineSvc().getLinesByParam(paginator, fromCityCode, fromRegionCode, toCityCode,
					toRegionCode, columnName, orderby);
			setAttr("page", lines);
			setAttr("fromCityCode", fromCityCode);
			setAttr("fromRegionCode", fromRegionCode);
			setAttr("toCityCode", toCityCode);
			setAttr("toRegionCode", toRegionCode);
			setAttr("parkName", parkName);
			setAttr("orderby", orderby);
			setAttr("column", columnName);
			renderJsp(path + "search.jsp");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * 根据出发地、到达地、搜索并分页
	 */
	public void getLineParkList() {
		try {
			Page<IndexLine> lines = null;
			String tag=getPara("tag");
			String parkId = getPara("parkId");
			String fromCityCode = getPara("fromCityCode"); // 出发城市code值
			String fromRegionCode = getPara("fromRegionCode"); // 出发地区code值
			String toCityCode = getPara("toCityCode"); // 到达城市code值
			String toRegionCode = getPara("toRegionCode"); // 到达地区code值
			String columnName = getPara("column");// 排序列
			String orderby = getPara("orderby"); // 升序/降序
			Paginator paginator = getPaginator();
			paginator.setPageSize(10);
			if (paginator.getPageSize() > 10) {
				paginator.setPageSize(10);
			}
			if (paginator.getPageNo() < 1) {
				paginator.setPageNo(1);
			}
			LogisticsPark logisticsPark= new LogisticsParkSvc().getLineByParkId(parkId);
			lines = new LineSvc().getLineParkList(paginator, fromCityCode, fromRegionCode, toCityCode, toRegionCode,
					 columnName, orderby, parkId);
			setAttr("page", lines);
			setAttr("fromCityCode", fromCityCode);
			setAttr("fromRegionCode", fromRegionCode);
			setAttr("toCityCode", toCityCode);
			setAttr("toRegionCode", toRegionCode);
			setAttr("parkId", parkId);
			setAttr("orderby", orderby);
			setAttr("column", columnName);
			setAttr("tag", tag);
			setAttr("LogisticsPark", logisticsPark);
			renderJsp(path + "searchpark.jsp");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据出发地、到达地、搜索黄金专线并分页
	 */
	public void getGoltLineParkList() {
		try {
			Page<IndexLine> lines = null;
			String parkId = getPara("parkId");
			String fromCityCode = getPara("fromCityCode"); // 出发城市code值
			String fromRegionCode = getPara("fromRegionCode"); // 出发地区code值
			String toCityCode = getPara("toCityCode"); // 到达城市code值
			String toRegionCode = getPara("toRegionCode"); // 到达地区code值
			String columnName = getPara("column");// 排序列
			String orderby = getPara("orderby"); // 升序/降序
			Paginator paginator = getPaginator();
			paginator.setPageSize(10);
			if (paginator.getPageSize() > 10) {
				paginator.setPageSize(10);
			}
			if (paginator.getPageNo() < 1) {
				paginator.setPageNo(1);
			}
			lines = new LineSvc().getLineParkList(paginator, fromCityCode, fromRegionCode, toCityCode, toRegionCode,
					 columnName, orderby, parkId);
			setAttr("page", lines);
			setAttr("fromCityCode", fromCityCode);
			setAttr("fromRegionCode", fromRegionCode);
			setAttr("toCityCode", toCityCode);
			setAttr("toRegionCode", toRegionCode);
			setAttr("parkId", parkId);
			setAttr("orderby", orderby);
			setAttr("column", columnName);
			renderJsp(path + "examine.jsp");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 保存专线
	 */
	public void saveLine() {
		JSONObject json = new JSONObject();
		try {
			// 获取登录session用户
			SessionUser user = getSessionSysUser();
			Company company =new CompanySvc().getCompany(user.getUserId());
			final int networkId=getParaToInt("networkId"); //所属网点
			final String fromCityCode = getPara("fromCityCode");// 出发城市code
			final String fromRegionCode = getPara("fromRegionCode");// 出发地区code
			final String toCityCode = getPara("toCityCode");// 到达城市code
			//final String toRegionCode = getPara("toRegionCode");// 到达地区code
			final Integer survive_time = getParaToInt("survive_time",0);
			final String price_heavy = getPara("price_heavy");
			final String price_small = getPara("price_small");
			final Integer frequency = getParaToInt("frequency", 1);
			final String position = getPara("address");
			final String startingprice = getPara("startingprice","0");
			final int isSale = getParaToInt("isSale");
			final Integer parkId=getParaToInt("parkId"); //所属物流园ID
		   Address address = new Address();
		   address.set("uuid", UUIDUtil.UUID());
		   address.set("region_code", fromCityCode);
		   address.set("create_time", getNow());
		   address.set("user_id", user.getUserId());
			List<IndexLine> list=new ArrayList<>();
			// 保存专线信息
			String[] citySplit = toCityCode.split(",");
			for (int i = 0; i < citySplit.length; i++) {
				System.out.println(citySplit[i]);
				List<IndexLine> checkLine=new LineSvc().checkLine(fromCityCode, fromRegionCode, toCityCode, networkId);
				if (checkLine!=null && checkLine.size()>0) {
					json.put("state", "FAILED");
					json.put("message", "已经存在该条专线!请选择其他专线");
					renderJson(json.toString());
					return;
				}
				
				IndexLine indexLine = new IndexLine();
				indexLine.set("network_id", networkId);
				indexLine.set("from_city_code", fromCityCode);
				indexLine.set("from_region_code", fromRegionCode);
				indexLine.set("to_city_code", citySplit[i]);
				//indexLine.set("to_region_code", toRegionCode);
			    indexLine.set("survive_time", survive_time);
				indexLine.set("price_heavy", price_heavy);
				indexLine.set("price_small", price_small);
				indexLine.set("frequency", frequency);
				indexLine.set("logistics_park_id",parkId);
				indexLine.set("company_id", company.getInt("id"));
				indexLine.set("create_time", getNow());
				indexLine.set("address", position);
				indexLine.set("is_sale", isSale);
				indexLine.set("is_live", 1);
				indexLine.set("starting_price", startingprice);
				list.add(indexLine);
			}
			boolean savaLine=new LineSvc().saveLines(address,list);
			if (savaLine) {
				json.put("state", "SUCCESS");
				json.put("message", "保存成功");
				renderJson(json.toString());
			} 
		} catch (Exception e) {
			e.printStackTrace();
			json.put("state", "FAILED");
			json.put("message", "保存失败");
			renderJson(json.toString());
		}

	}
	/**
	 * 特价专线
	 */
	public void specialLine(){
		try{
		String fromCityCode = getPara("fromCityCode"); // 出发城市code值
		String fromRegionCode = getPara("fromRegionCode"); // 出发地区code值
		String toCityCode = getPara("toCityCode"); // 到达城市code值
		String toRegionCode = getPara("toRegionCode"); // 到达地区code值
		String parkName = getPara("parkName"); // 物流园名称
		String columnName = getPara("column");// 排序列
		String orderby = getPara("orderby"); // 升序/降序
		Paginator paginator = getPaginator();
		paginator.setPageSize(10);
		if (paginator.getPageSize() > 10) {
			paginator.setPageSize(10);
		}
		if (paginator.getPageNo() < 1) {
			paginator.setPageNo(1);
		}
		Page<IndexLine> lines = new LineSvc().getSpecialLines(paginator, fromCityCode, fromRegionCode, toCityCode,
				toRegionCode, columnName, orderby);
		setAttr("page", lines);
		setAttr("fromCityCode", fromCityCode);
		setAttr("fromRegionCode", fromRegionCode);
		setAttr("toCityCode", toCityCode);
		setAttr("toRegionCode", toRegionCode);
		setAttr("parkName", parkName);
		setAttr("orderby", orderby);
		setAttr("column", columnName);
		renderJsp(path+"special.jsp");

	} catch (Exception e) {
		e.printStackTrace();
	}
}

}
