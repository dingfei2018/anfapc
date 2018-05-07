package com.supyuan.front.goods;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.component.base.BaseResult;
import com.supyuan.component.base.ResultType;
import com.supyuan.front.company.Company;
import com.supyuan.front.company.CompanySvc;
import com.supyuan.front.index.IndexLine;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.modules.addressbook.Address;
import com.supyuan.modules.addressbook.AddressSvc;
import com.supyuan.modules.addressbook.Region;
import com.supyuan.system.user.SysUser;
import com.supyuan.util.DateUtils;
import com.supyuan.util.UUIDUtil;
/**
 * 订单
 * 
 * @author chendekang
 *
 */
@ControllerBind(controllerKey = "/front/goods")
public class GoodsController extends BaseProjectController {
	private static final Log log = Log.getLog(GoodsController.class);
	private static final String path = "/pages/front/goods/";
	private static final String personalpath = "/pages/front/personal/";
	//零担跳转
	public void index() {
		SessionUser user = getSessionSysUser();
		Integer isCert = user.getIsCert();
		if(isCert==0){
			redirect("/front/userconter/getCompanyInfo");
		}else{
			homelist();
		}
	}
	
	
	public void homelist(){
		Region fromaddr =null;
		Region toaddr=null;
		Integer order =getParaToInt("orderid");
		Integer fromcitycode =getParaToInt("fromcitycode");
		Integer fromregioncode =getParaToInt("fromregioncode");
		Integer tocitycode =getParaToInt("tocitycode");
		Integer toregioncode =getParaToInt("toregioncode");
		if(null != order){
			if(fromregioncode==null ){
				fromaddr = new AddressSvc().getRegiondownName(fromcitycode);
			}else{
				fromaddr = new AddressSvc().getRegiondownName(fromregioncode);
			}
			if(toregioncode==null){
				toaddr = new AddressSvc().getRegiondownName(tocitycode);
			}else{
				 toaddr = new AddressSvc().getRegiondownName(toregioncode);
			}
			if(fromaddr != null){
				setAttr("fromaddr", fromaddr);
			}
			if(toaddr != null){
				setAttr("toaddr", toaddr);
			}
			CompanySvc companySvc = new CompanySvc();
		    IndexLine line = companySvc.getLine(order);
		    boolean isSame = false;
			if(line!=null){
				Company company = companySvc.getCompanyById(line.getInt("company_id"));
				SessionUser user = getSessionSysUser();
				if(user!=null&&user.getUserId()==company.getInt("user_id")){
					isSame = true;
				}
			}
			setAttr("isSame", isSame);
			setAttr("orderid", order);
			setAttr("fromregioncode", fromregioncode);
			setAttr("toregioncode", toregioncode);
			setAttr("Linesdata", line);
		}
		renderJsp(path + "zeroorder.jsp");
	}
	
	//整车跳转
	public void vehiclelist(){
		Integer order =getParaToInt("orderid");
		Integer fromcitycode =getParaToInt("fromcitycode");
		Integer fromregioncode =getParaToInt("fromregioncode");
		Integer tocitycode =getParaToInt("tocitycode");
		Integer toregioncode =getParaToInt("toregioncode");
		try {
			Region fromaddr =null;
			Region toaddr=null;
			if(null != order){
				if(fromregioncode==null ){
					fromaddr = new AddressSvc().getRegiondownName(fromcitycode);
				}else{
					fromaddr = new AddressSvc().getRegiondownName(fromregioncode);
				}
				if(toregioncode==null){
					toaddr = new AddressSvc().getRegiondownName(tocitycode);
				}else{
					 toaddr = new AddressSvc().getRegiondownName(toregioncode);
				}
				if(fromaddr != null){
					setAttr("fromaddr", fromaddr);
				}
				if(toaddr != null){
					setAttr("toaddr", toaddr);
				}
				CompanySvc companySvc = new CompanySvc();
			    IndexLine line = companySvc.getLine(order);
			    boolean isSame = false;
				if(line!=null){
					Company company = companySvc.getCompanyById(line.getInt("company_id"));
					SessionUser user = getSessionSysUser();
					if(user!=null&&user.getUserId()==company.getInt("user_id")){
						isSame = true;
					}
				}
				setAttr("isSame", isSame);
				setAttr("orderid", order);
				setAttr("fromregioncode", fromregioncode);
				setAttr("toregioncode", toregioncode);
				setAttr("Linesdata", line);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		renderJsp(path + "vehicleorder.jsp");
	}
	

	public void ordersave(){
		//发货地此
		String hairarea= getPara("hairarea");
		String fulladdress= getPara("hairfulladdress");
		//收货地此
		String closedarea= getPara("closedarea");
		String closedfulladdress= getPara("closedfulladdress");
		BaseResult result = new BaseResult();
		try {
			SessionUser user = getSessionSysUser();
			//区分订单
			if(user == null){
				result.setResultType(ResultType.NOTLOGIN);
				renderJson(result);
				return;
			}
			String lineId = getPara("orderid");
			if(StringUtils.isEmpty(lineId)){
				result.setResultType(ResultType.ORDEROFF);
				renderJson(result);
				return;
			}else{
				IndexLine line = new CompanySvc().getLine(Integer.parseInt(lineId));
				if(line==null){
					result.setResultType(ResultType.ORDEROFF);
					renderJson(result);
					return;
				}
			}
			
			String orderdate=new SimpleDateFormat("yyyyMMddHHmmss").format(DateUtils.getNowDate());
			String now = getNow();
			if(StringUtils.isBlank(hairarea)){
				hairarea = getPara("scity");
			}
			if(StringUtils.isBlank(closedarea)){
				closedarea = getPara("ecity");
			}
			
			Address hairaddress = new Address();
			hairaddress.set("uuid", UUIDUtil.UUID());
			hairaddress.set("region_code", hairarea);
			hairaddress.set("tail_address", fulladdress);
			hairaddress.set("create_time", now);
			hairaddress.set("user_id", user.getUserId());
			
			Address closedress = new Address();
			closedress.set("uuid", UUIDUtil.UUID());
			closedress.set("region_code", closedarea);
			closedress.set("tail_address", closedfulladdress);
			closedress.set("create_time", now);
			closedress.set("user_id", user.getUserId());
			
			//订单
			Order order = getModel(Order.class);
			order.set("ship_status", 2);//运单状态（0：默认状态，1：交易进行中，2：交易确认，3：交易完成）
			order.set("order_number", "YD"+orderdate);//运单号
			order.set("line_id", lineId);//专线id
			if(user != null){
				order.set("user_id", user.getUserId());
			}
			
			order.set("create_time", now);
			List<Goods> ordergoods =getModels(Goods.class,"Ordergoods", order, hairarea, closedarea);
			boolean saveOrder = new ShipOrderSvc().saveOrder(order, ordergoods, hairaddress, closedress);
			if(!saveOrder){
				result.setResultType(ResultType.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultType(ResultType.FAIL);
		}
		renderJson(result);
	}


	
	//常发货页面跳转
	public void oftenlist(){
		SessionUser user = getSessionSysUser();
		Paginator paginator = getPaginator();
		paginator.setPageSize(10);
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		
		if(paginator.getPageNo()<1){
			paginator.setPageNo(1);
		}
		Page<Order> ordergoods = new GoodsSvc().getOrdergoods(paginator, user.getUserId());
		setAttr("page", ordergoods);
		
		
		renderJsp(path + "commonlyorder.jsp");
	}
	
	//设置常发货物
	public void setoftenOrder(){
		String orderid= getPara("orderid");
		BaseResult result = new BaseResult();
		try {
			if(null != orderid){
				int update = Db.update("update shipping_order set send_status=1 where id=" + orderid);
				if(update==0){
					result.setResultType(ResultType.FAIL);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultType(ResultType.FAIL);
		}
		renderJson(result);
	}
	

	//已发的货
	public void personaloftenlist(){
		SessionUser user = getSessionSysUser();
		Paginator paginator = getPaginator();
		paginator.setPageSize(10);
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		
		if(paginator.getPageNo()<1){
			paginator.setPageNo(1);
		}
		String para = getPara("type");
		if(StringUtils.isEmpty(para)||!NumberUtils.isNumber(para)){
			para = "1";
		}
		Page<Order> orders = new GoodsSvc().getOrders(paginator, user.getUserId(), Integer.parseInt(para), "order by create_time desc");
		setAttr("page", orders);
		setAttr("curr", 3);
		setAttr("type", para);
		renderJsp(personalpath + "delivery.jsp");
	}
	
	
	//常发货查看详情
	public void oftendetails(){
		String fromregioncode =getPara("fromregioncode");
		String toregioncode =getPara("toregioncode");
		Integer orderid =getParaToInt("orderid");
		if(null != orderid){
			setAttr("fromregioncode", fromregioncode);
			setAttr("toregioncode", toregioncode);
			Order order = new GoodsSvc().getorderid(orderid);
			if(order != null ){

				setAttr("order", order);
				List<Goods> ordergoods = new GoodsSvc().getoftendetails(orderid);
				if(ordergoods != null && ordergoods.size() > 0){
					setAttr("ordergoods", ordergoods);
				}
			}
		}
		renderJsp(path + "orderdetails.jsp");
	}
	
	//删除已发订单
	public void deleteorder(){
		String orderid= getPara("orderid");
		BaseResult result = new BaseResult(ResultType.FAIL);
		try {
			if(StringUtils.isNotBlank(orderid)){
				SessionUser user = getSessionSysUser();
				int update = Db.update("update shipping_order set is_live_from=(CASE WHEN user_id="+user.getUserId()+" THEN 0  ELSE is_live_from END), is_live_to=(CASE WHEN line_user_id="+user.getUserId()+" THEN 0  ELSE is_live_to END) where id in (" + orderid + ")");
				if(update>0){
					result.setResultType(ResultType.SUCCESS);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultType(ResultType.FAIL);
		}
		renderJson(result);
	}
	
	//修改常发货物状态
	public void updateSendStatus(){
		String orderid= getPara("orderid");
		BaseResult result = new BaseResult(ResultType.FAIL);
		try {
			if(null != orderid){
				int update = Db.update("update shipping_order  set send_status=0  where id in (" + orderid + ")");
				if(update>0){
					result.setResultType(ResultType.SUCCESS);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultType(ResultType.FAIL);
		}
		renderJson(result);
	}
	
	

	//再次发下单
	public void onceagainorder() {
		String orderid = getPara("orderid");
		try {
			if(StringUtils.isNotBlank(orderid)){
				Order order = new Order().findFirst("select o.* from shipping_order o where o.id=? and send_status=1 limit 1", Integer.parseInt(orderid));
				if(order==null){
					redirect("/index");
					return;
				}
				IndexLine line = new CompanySvc().getLine(order.getInt("line_id"));
				if(line != null){
					SessionUser sessionSysUser = getSessionSysUser();
					if(order.getInt("user_id")!=sessionSysUser.getUserId()){
						line = null;
						setAttr("Linesdata", line);
						if(order.getInt("is_truckload") == 0){
							renderJsp(path + "editorzeroorder.jsp");
						}else{
							renderJsp(path + "editorvehicleorder.jsp");
						}
						return;
					}else{
						setAttr("Linesdata", line);
						String fromcitycode =line.getStr("from_city_code");
						if(StringUtils.isEmpty(fromcitycode)){
							fromcitycode =line.getStr("from_region_code");
						}
						String tocitycode =line.getStr("to_city_code");
						if(StringUtils.isEmpty(fromcitycode)){
							tocitycode =line.getStr("to_region_code");
						}
						Region fromaddr = new AddressSvc().getRegiondownName(Integer.parseInt(fromcitycode));
						Region toaddr = new AddressSvc().getRegiondownName(Integer.parseInt(tocitycode));
						if(fromaddr != null){
							setAttr("fromaddr", fromaddr);
						}
						if(toaddr != null){
							setAttr("toaddr", toaddr);
						}
					}
				}
				setAttr("order", order);
				// 发货地此
				Address hair = Address.dao.findFirst("SELECT tail_address AS place FROM address_book b WHERE b.uuid='"+ order.getStr("from_addr_uuid")+"'");
				if(null !=hair ){
					setAttr("hair", hair);
				}
				// 收货地此
				Address closed = Address.dao.findFirst("SELECT tail_address AS place FROM address_book b WHERE b.uuid='"+ order.getStr("to_addr_uuid")+"'");
				if(null !=closed ){
					setAttr("closed", closed);
				}
				List<Goods> ordergoods = new GoodsSvc().getoftendetails(Integer.parseInt(orderid));
				if(ordergoods != null && ordergoods.size() > 0){
					setAttr("ordergoods", ordergoods);
				}
				if(order.getInt("is_truckload") == 0){
					renderJsp(path + "editorzeroorder.jsp");
				}else{
					renderJsp(path + "editorvehicleorder.jsp");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("再次发布失败");
		}
	}

	
	public <T> List<Goods> getModels(Class<T> modelClass,String modelName, Order order,String hairarea,String closedarea ){
		List<Goods>  list= new ArrayList<Goods>();
		int allweight = 0;//总重量
		int allvolume = 0;//总体积
		int allamount = 0;//总数量
		for (int i=0;true;i++) {
			Goods m =(Goods) getModel(modelClass,modelName+"["+i+"]");
			if(m==null||m.toString().equals("{}")){
				break;
			}else{
				m.set("from_addr", Integer.parseInt(hairarea));
				m.set("to_addr", Integer.parseInt(closedarea));
				if(m.getStr("weight_unit").equals("k")){
					allweight+=m.getInt("weight");//重量
				}else{
					allweight+=m.getInt("weight")*1000;//重量
				}
				allvolume+=m.getInt("volume");//立方
				Integer amount = m.getInt("amount");
				if(amount!=null)allamount+=amount;//件
				list.add(m);
			}
		}
		order.set("all_weight", allweight);
		order.set("all_volume", allvolume);
		order.set("all_amount", allamount);
		return list;
	}

}
