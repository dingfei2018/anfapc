package com.supyuan.kd.kucun;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.front.branch.LogisticsNetwork;
import com.supyuan.front.branch.NetWorkSvc;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.util.DateUtils;
import com.supyuan.util.excel.poi.POIUtils;



/**
 * 库存查询
 * @author liangxp
 *
 * Date:2017年12月15日上午11:27:41 
 * 
 * @email liangxp@anfawuliu.com
 */
@ControllerBind(controllerKey = "/kd/kucun")
public class KucunController extends BaseProjectController {

	private static final String path = "/pages/kd/kucun/";

	
	/**
	 * 库存查询
	 * @author liangxp
	 */
	public void index() {
		SessionUser user = getSessionSysUser();
		List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
		setAttr("networks", netWorkList);
		List<LogisticsNetwork> allnetWorks = new NetWorkSvc().getNetWorkList(user);
		setAttr("allnetWorks", allnetWorks);
		renderJsp(path + "list.jsp");
	}
	
	
	public void list() {
		SessionUser user = getSessionSysUser();
		List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
		setAttr("networks", netWorkList);
		KucunSearchModel model = KucunSearchModel.getBindModel(KucunSearchModel.class, getRequest());
		Paginator paginator = getPaginator();
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		Page<KdShip> queryList = new KucunSvc().queryList(paginator, user, model, false);
		renderJson(queryList);
	}
	
	
	/**
	 * 导出库存列表
	 * @author liangxp
	 */
	public void exportKucunlist() {
		SessionUser user = getSessionSysUser();
		KucunSearchModel model = KucunSearchModel.getBindModel(KucunSearchModel.class, getRequest());
		try {
			Page<KdShip> queryList = new KucunSvc().queryList(null, user, model, true);
			List<KucunModel> res = new ArrayList<KucunModel>();;
			for (KdShip record : queryList.getList()) {
				KucunModel mo = new KucunModel();
				mo.setShipSn(record.getStr("ship_sn"));
				mo.setSnetworkName(record.getStr("snetworkName"));
				mo.setCreatTime(DateUtils.format(record.getDate("create_time"), DateUtils.YMD_HMS));
				mo.setHashours(record.getLong("hashours")+"");
				mo.setProname(record.getStr("proname"));
				mo.setFromAddr(record.getStr("fromCity"));
				mo.setToAddr(record.getStr("toCity"));
				mo.setSendName(record.getStr("sendName"));
				mo.setReceiveName(record.getStr("receiverName"));
				mo.setEnetworkName(record.getStr("enetworkName"));
				mo.setShipVolume(record.getDouble("ship_volume"));
				mo.setShipWeight(record.getDouble("ship_weight"));
				mo.setShipAmount(record.getInt("ship_amount"));
				res.add(mo);
			}
			String filename = new String("库存列表.xlsx".getBytes("GB18030"), "ISO_8859_1");
			HttpServletResponse response = getResponse();
			response.setHeader("Content-Disposition", "attachment;filename="+filename);
			POIUtils.generateXlsxExcelStream(res, "库存列表.xlsx", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		renderNull();
	}
	
	
	
	/**
	 * 出库记录
	 * @author liangxp
	 */
	public void out() {
		SessionUser user = getSessionSysUser();
		List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
		setAttr("networks", netWorkList);
		List<LogisticsNetwork> allnetWorks = new NetWorkSvc().getNetWorkList(user);
		setAttr("allnetWorks", allnetWorks);
		renderJsp(path + "outlist.jsp");
	}
	
	
	public void outlist() {
		SessionUser user = getSessionSysUser();
		KucunSearchModel model = KucunSearchModel.getBindModel(KucunSearchModel.class, getRequest());
		Paginator paginator = getPaginator();
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		Page<KdShip> queryList = new KucunSvc().outList(paginator, user, model, false);
		renderJson(queryList);
	}
	
	/**
	 * 导出出库列表
	 * @author liangxp
	 */
	public void exportOutlist() {
		SessionUser user = getSessionSysUser();
		KucunSearchModel model = KucunSearchModel.getBindModel(KucunSearchModel.class, getRequest());
		try {
			Page<KdShip> queryList = new KucunSvc().outList(null, user, model, true);
			List<OutModel> res = new ArrayList<OutModel>();;
			for (KdShip record : queryList.getList()) {
				OutModel mo = new OutModel();
				mo.setShipSn(record.getStr("ship_sn"));
				mo.setSnetworkName(record.getStr("snetworkName"));
				mo.setCreatTime(DateUtils.format(record.getDate("create_time"), DateUtils.YMD_HMS));
				mo.setOuttime(DateUtils.format(record.getDate("outtime"), DateUtils.YMD_HMS));
				mo.setHashours(record.getLong("hashours")+"");
				mo.setProname(record.getStr("proname"));
				mo.setFromAddr(record.getStr("fromCity"));
				mo.setToAddr(record.getStr("toCity"));
				mo.setSendName(record.getStr("sendName"));
				mo.setReceiveName(record.getStr("receiverName"));
				mo.setEnetworkName(record.getStr("enetworkName"));
				mo.setShipVolume(record.getDouble("ship_volume"));
				mo.setShipWeight(record.getDouble("ship_weight"));
				mo.setShipAmount(record.getInt("ship_amount"));
				res.add(mo);
			}
			String filename = new String("出库列表.xlsx".getBytes("GB18030"), "ISO_8859_1");
			HttpServletResponse response = getResponse();
			response.setHeader("Content-Disposition", "attachment;filename="+filename);
			POIUtils.generateXlsxExcelStream(res, "出库列表.xlsx", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		renderNull();
	}
}
