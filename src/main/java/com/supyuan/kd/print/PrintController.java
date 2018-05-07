package com.supyuan.kd.print;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.front.branch.LogisticsNetwork;
import com.supyuan.front.branch.NetWorkSvc;
import com.supyuan.front.company.Company;
import com.supyuan.front.company.CompanySvc;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.kd.customer.Customer;
import com.supyuan.kd.goods.KdProduct;
import com.supyuan.kd.loading.KdTrunkLoad;
import com.supyuan.kd.loading.LoadingSvc;
import com.supyuan.kd.setting.KdSettingSvc;
import com.supyuan.kd.user.User;
import com.supyuan.kd.user.UserSvc;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.kd.waybill.KdShipSvc;
import com.supyuan.modules.addressbook.Address;
import com.supyuan.util.Config;



/**
 * 打印相关
 * @author liangxp
 *
 * Date:2017年12月20日下午4:29:16 
 * 
 * @email liangxp@anfawuliu.com
 */
@ControllerBind(controllerKey = "/kd/print")
public class PrintController extends BaseProjectController {

	private static final String path = "/pages/kd/print/";
	
	private static final String IMAGE_PATH = Config.getStr("FILE.UPLOAD_PATH");//图片上传目录
	
	private static final String DOMAIN_PREFIX = Config.getStr("IMAGE.DOMAIN_PREFIX");
	
	public void loadprintIndex() {
		SessionUser user = getSessionSysUser();
    	String loadId = getPara("loadId");
    	if(StringUtils.isNotBlank(loadId)){
    		KdTrunkLoad load = new LoadingSvc().queryKdTrunkLoad(Integer.parseInt(loadId), user);
    		if(load==null){
    			renderError(404);
        		return;
    		}
    		List<KdShip> shipList = new LoadingSvc().queryLoadShipList(Integer.parseInt(loadId), user);
    		User loaduser = User.dao.findFirst("select realname, userid from sys_user where userid=?", user.getUserId());
    		NetWorkSvc svc = new NetWorkSvc();
    		LogisticsNetwork snet = svc.getNetWorkNetworkId(load.getInt("network_id"));
    		LogisticsNetwork enet = svc.getNetWorkNetworkId(load.getInt("load_next_network_id"));
    		
    		Company company = new CompanySvc().getCompany(loaduser.getInt("userid"));
    		setAttr("load", load);
    		setAttr("ships", shipList);
    		setAttr("snet", snet);
    		setAttr("enet", enet);
    		setAttr("loaduser", loaduser);
    		setAttr("company", company);
    	}else{
    		renderError(404);
    		return;
    	}
    	renderJsp(path+"loadprint.jsp");
	}
/*	public void wayBillprintIndex() {
		SessionUser user = getSessionUser();
		String shipId=getPara("shipId");
		KdShip kdShip =new KdShipSvc().findShipList(shipId);
		List<KdProduct> kdProducts=new KdShipSvc().findShipProductsToPrint(shipId);
		KdSetting set =KdSetting.dao.findFirst("SELECT id,value,name,company_id from kd_setting where company_id=?",user.getCompanyId());
		if(set==null){
			set=KdSetting.dao.findFirst("SELECT id,value,name,company_id from kd_setting where company_id=?",0);
		}
		User userCompany =new UserSvc().getUserAndCompanyByUserId(user.getUserId());
		Customer senderCustomer=new KdShipSvc().getCustomer(shipId,1);
		Customer receiverCustomer=new KdShipSvc().getCustomer(shipId,2);
		setAttr("kdShip", kdShip);
		setAttr("kdProducts", kdProducts);
		setAttr("set", set);
		setAttr("senderCustomer", senderCustomer);
		setAttr("receiverCustomer", receiverCustomer);
		setAttr("userCompany", userCompany);
		renderJsp(path+"waybillprint.jsp");
	}*/
	/*public void wayBillprintIndex() {
		SessionUser user = getSessionUser();
		String shipId=getPara("shipId");
		KdShip kdShip =new KdShipSvc().findShipList(shipId);
		List<KdProduct> kdProducts=new KdShipSvc().findShipProductsToPrint(shipId);
		KdSetting set =KdSetting.dao.findFirst("SELECT id,value,name,company_id from kd_setting where company_id=?",user.getCompanyId());
		if(set==null){
			set=KdSetting.dao.findFirst("SELECT id,value,name,company_id from kd_setting where company_id=?",0);
		}
		User userCompany =new UserSvc().getUserAndCompanyByUserId(user.getUserId());
		Customer senderCustomer=new KdShipSvc().getCustomer(shipId,1);
		Customer receiverCustomer=new KdShipSvc().getCustomer(shipId,2);
		setAttr("kdShip", kdShip);
		setAttr("kdProducts", kdProducts);
		setAttr("set", set);
		setAttr("senderCustomer", senderCustomer);
		setAttr("receiverCustomer", receiverCustomer);
		setAttr("userCompany", userCompany);
		renderJsp(path+"waybillprint.jsp");
	}*/
	
	public void getwayBillprintData(){
		SessionUser user = getSessionUser();
		String shipId=getPara("shipId");
		KdShip kdShip =new KdShipSvc().findShipList(shipId);
		List<KdProduct> kdProducts=new KdShipSvc().findShipProductsToPrint(shipId);
		User userCompany =new UserSvc().getUserAndCompanyByUserId(user.getUserId());
		Customer senderCustomer=new KdShipSvc().getCustomer(shipId,1);
		Customer receiverCustomer=new KdShipSvc().getCustomer(shipId,2);
		
		HashMap<String,Object> data=new HashMap<>();
		data.put("kdShip", kdShip);
		data.put("kdProducts", kdProducts);
		data.put("userCompany", userCompany);
		data.put("senderCustomer", senderCustomer);
		data.put("receiverCustomer", receiverCustomer);
		
		renderJson(data);
		
	}
	
	public void loadprint() {
		SessionUser user = getSessionSysUser();
		String loadId = getPara("loadId");
    	if(StringUtils.isNotBlank(loadId)){
    		KdTrunkLoad load = new LoadingSvc().queryKdTrunkLoad(Integer.parseInt(loadId), user);
    		List<KdShip> shipList = new LoadingSvc().queryLoadShipList(Integer.parseInt(loadId), user);
    		setAttr("load", load);
    		setAttr("ships", shipList);
    		LoadPrintModel model = new LoadPrintModel();
    		model.setLoad(load);
    		model.setShipList(shipList);
    		renderJson(model);
    	}else{
    		renderError(500);
    		return;
    	}
	}
	
	
	public void wayBarPrintIndex() {
    	String shipId = getPara("shipId");
    	SessionUser user = getSessionUser();
    	if(StringUtils.isNotBlank(shipId)){
    		StringBuilder sql=new StringBuilder(" select ship_amount,goods_sn,getCustomerName(s.ship_receiver_id)as receiverName, getRegion(s.ship_to_city_code) as toAdd,s.create_time,"
    				+ "getNetworkNameById(s.load_network_id) AS tonetWorkName,s.network_id,getNetworkNameById(s.network_id) AS fromnetWorkName");
    		sql.append(" from  kd_ship s where s.ship_id=? ");
    		KdShip ship = KdShip.dao.findFirst(sql.toString(), shipId);
    		if(ship == null){
    			renderError(404);
        		return;
    		}
    		
    		JSONObject res = new KdSettingSvc().getSetJsonByComIdAndType(user.getCompanyId(), 0);
    		if(res.getIntValue("topLabel")==0){
    			Company company = new CompanySvc().getCompany(user.getCompanyId());
    			setAttr("companyName", company.get("corpname"));
    		}
    		
    		if(res.getIntValue("bottomLabel")==0){
    			Address address = Address.dao.findFirst("select getUUIDFullAddress(sub_addr_uuid) detailAddress,sub_logistic_telphone from logistics_network where id=?", ship.getInt("network_id"));
    			if(address == null){
    				renderError(404);
    				return;
    			}
    			setAttr("detailAddress", address.get("detailAddress"));
    			setAttr("telphone", address.get("sub_logistic_telphone"));
    		}else if(res.getIntValue("bottomLabel")==2){
    			Address address = Address.dao.findFirst("select getUUIDFullAddress(corp_addr_uuid) detailAddress, corp_telphone from company where id=?", user.getCompanyId());
    			if(address == null){
    				renderError(404);
    				return;
    			}
    			setAttr("detailAddress", address.get("detailAddress"));
    			setAttr("telphone", address.get("sub_logistic_telphone"));
    		}else if(res.getIntValue("bottomLabel")==3){
    			setAttr("detailAddress", res.getString("labelValue"));
    		}
    		
    		String tonetWorkName = ship.get("tonetWorkName");
    		if(StringUtils.isEmpty(tonetWorkName)){
    			tonetWorkName = ship.get("toAdd");
    		}
    		
    		setAttr("fromnetWorkName", ship.get("fromnetWorkName"));
    		setAttr("tonetWorkName", tonetWorkName);
    		setAttr("size", ship.getInt("ship_amount"));
    		setAttr("goodsSn", ship.get("goods_sn"));
    		setAttr("receiverName", ship.get("receiverName"));
    		setAttr("time", ship.getDate("create_time"));
    		
    	}else{
    		renderError(404);
    		return;
    	}
    	renderJsp(path+"waybar.jsp");
	}
	
}
