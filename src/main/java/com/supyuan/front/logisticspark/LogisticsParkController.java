package com.supyuan.front.logisticspark;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.front.company.ShippingOrder;
import com.supyuan.jfinal.base.EasyUIPage;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.kd.customer.Customer;
import com.supyuan.kd.customer.CustomerSvc;
@ControllerBind(controllerKey = "/park")
public class LogisticsParkController extends BaseProjectController {

	private static final String path = "/pages/front/park/";

	/**
	 * 历史订单统计
	 * @author liangxp
	 * Date:2017年9月25日上午10:40:26 
	 *
	 */
	public void alltrade() {
		String  parkName = getPara("parkName");
		String  orderBy = getPara("orderBy");
		Paginator paginator = getPaginator();
		paginator.setPageSize(10);
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		if(paginator.getPageNo()<1){
			paginator.setPageNo(1);
		}
		Page<LogisticsPark> orders = new LogisticsParkSvc().statisticsOrders(paginator, parkName, orderBy, "amount");
		setAttr("orders", orders);
		setAttr("parkName", parkName);
		setAttr("orderBy", orderBy);
		renderJsp(path + "alltrade.jsp");
	}
	
	/**
	 * 按物流园发货页
	 */
	public void searchIndexPark() {

		try {
			String addcity = getPara("addcity");// 物流园城市code值
			String addCode = getPara("addCode"); // 物流园区县code值
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
			if (StringUtils.isNotBlank(parkName)) {
				parkName = java.net.URLDecoder.decode(parkName, "UTF-8");
			}
		
			Page<LogisticsPark> parks=new LogisticsParkSvc().searchLogisticsPark(paginator, parkName,addcity, addCode, orderby, columnName);
			setAttr("page", parks);
			setAttr("addcity", addcity);
			setAttr("addCode", addCode);
			setAttr("parkName", parkName);
			setAttr("orderby", orderby);
			setAttr("column", columnName);
			setAttr("tag", 2);
			renderJsp(path + "delivery.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 查看历史交易量详情
	 */
	public void searchParkOrders() {

		try {
			Page<ShippingOrder> orders = null;	
			String parkId = getPara("parkId");//物流园ID
			String fromCityCode=getPara("fromCityCode");
			String fromRegionCode = getPara("fromRegionCode"); // 出发地区code值
			String toCityCode = getPara("toCityCode");
			String toRegionCode = getPara("toRegionCode"); // 到达地区code值
			Paginator paginator = getPaginator();
			paginator.setPageSize(10);
			if (paginator.getPageSize() > 10) {
				paginator.setPageSize(10);
			}
			if (paginator.getPageNo() < 1) {
				paginator.setPageNo(1);
			}
			orders = new LogisticsParkSvc().historyOrders(paginator,fromCityCode, fromRegionCode,toCityCode, toRegionCode, parkId);
			LogisticsPark logisticsPark= new LogisticsParkSvc().getLineByParkId( parkId);
			setAttr("page", orders);
			setAttr("fromCityCode", fromCityCode);
			setAttr("fromRegionCode", fromRegionCode);
			setAttr("toCityCode", toCityCode);
			setAttr("toRegionCode", toRegionCode);
			setAttr("parkId", parkId);
			setAttr("LogisticsPark", logisticsPark);
			renderJsp(path + "quantity.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	
	/**
	 * 物流园输入联动
	 *
	 * 
	 *
	 */
	public void queryLogisticsParkList() {
		String parkName = getPara("parkName");
		List<LogisticsPark> parkList = new LogisticsParkSvc().ParkListByParkName(parkName);
		renderJson(parkList);
	}
	
	/**分页模糊查询物流园
	 * */
	public void queryLogisticsParkList1() {
		String pageNo = getPara("page");
		String pageSize = getPara("rows");
		Paginator paginator = getPaginator();
		if(StringUtils.isNotBlank(pageNo)){
			int size = Integer.parseInt(pageSize);
			paginator.setPageSize(size>50?50:size);
		}
		if(StringUtils.isNotBlank(pageNo)){
			int no = Integer.parseInt(pageNo);
			paginator.setPageNo(no>50?50:no);
		}
		Page<LogisticsPark> LogisticsParkList = new LogisticsParkSvc().ParkListByParkName1(paginator,getPara("queryName"));
		
		renderJson(new EasyUIPage<LogisticsPark>(LogisticsParkList));
	}

}
