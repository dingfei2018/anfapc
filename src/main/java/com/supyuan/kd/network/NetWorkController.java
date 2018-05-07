package com.supyuan.kd.network;




import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.kd.truck.Truck;
import com.supyuan.modules.addressbook.Address;
import com.supyuan.modules.addressbook.AddressSvc;
import com.supyuan.util.UUIDUtil;

/**
 * 网点控制器
 * @author chenan
 * Date:2018年1月11日上午9:41:08 
 */
@ControllerBind(controllerKey = "/kd/netWork")
public class NetWorkController  extends BaseProjectController  {
	
	private static final String path = "/pages/kd/network/";
	
	public void index() {
		SessionUser sessionUser=getSessionSysUser();
		
		String netWorkName=getPara("netWorkName")==null?"":getPara("netWorkName");
		String sub_leader_name=getPara("sub_leader_name")==null?"":getPara("sub_leader_name");
		String addr=getPara("addr")==null?"":getPara("addr");
	
		JSONObject json=getSessionAttr("msg");
		removeSessionAttr("msg");
		
		Paginator paginator = getPaginator();
		paginator.setPageSize(10);
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		if(paginator.getPageNo()<1){
			paginator.setPageNo(1);
		}
		Page<NetWork> page=new NetWorkSvc().getNetWorkList(paginator, netWorkName, sub_leader_name, addr,sessionUser);
	
		setAttr("page",page);
		setAttr("msg",json);
		setAttr("netWorkName",netWorkName);
		setAttr("sub_leader_name",sub_leader_name);
		setAttr("addr",addr);
		renderJsp(path + "index.jsp");
	}
	
	public void saveNetWork() {
		 JSONObject json = new JSONObject();
		try { 
		 SessionUser user = getSessionSysUser();
		 String sub_network_name = getPara("sub_network_name"); //add 网点名称 by chenan on 2017/12/12 14:53
		 String region = getPara("region");
		 String address = getPara("address");
		 String contact_man = getPara("contact_man");
		 String contact_information = getPara("contact_information");
		 String sub_logistic_telphone=getPara("sub_logistic_telphone");
		 
		 String sub_network_sn=getPara("sub_network_sn");
		 String sub_network_num=getsubnetworknum(user);
		 String park_id=getPara("park_id");
		 
		 boolean mobilecheck=getParaToBoolean("mobilecheck");
		 
			NetWork net= new NetWork();
			Address addressa=new Address();
			addressa.set("uuid",UUIDUtil.UUID());
			addressa.set("region_code",region);
			addressa.set("tail_address", address);
			addressa.set("create_time", getNow());
			addressa.set("user_id", user.getUserId());
			
			net.set("sub_network_name",sub_network_name); //add 网点名称 by chenan on 2017/12/12 14:53
			net.set("sub_leader_name", contact_man);
			net.set("sub_logistic_mobile", contact_information);
			net.set("sub_logistic_telphone", sub_logistic_telphone);
			net.set("company_id", user.getCompanyId());
			
			net.set("sub_network_sn", sub_network_sn);
			net.set("sub_network_num", sub_network_num);
			net.set("park_id", park_id);
			
			boolean flag=new NetWorkSvc().saveNetWork(net, addressa,mobilecheck, user);
			
			if(flag){
				json.put("state", "SUCCESS");
				json.put("msg", "新增成功");
			}else{
				json.put("state", "FAILED");
				json.put("msg", "新增失败");
			}   
		}catch(Exception e){
			e.printStackTrace();
		}
		renderJson(json.toString());
	}
	
	public void add(){
		SessionUser user = getSessionSysUser();
		String id=getPara("id");
		String flag=getPara("flag")==null?"":getPara("flag");
		NetWork netWork=new NetWork();
		if(flag.equals("update")){
			netWork=new NetWorkSvc().getNetWork(id, user);
		}
		
		setAttr("type",flag);
		setAttr("netWork",netWork);
		renderJsp(path + "addNet.jsp");
	}
	
	 public void updateNetWork(){
		 
		 SessionUser user = getSessionSysUser();
		 
		 String sub_network_name = getPara("sub_network_name");
		 String region = getPara("region");
		 String address = getPara("address");
		 String contact_man = getPara("contact_man");
		 String contact_information = getPara("contact_information");
		 String sub_logistic_telphone=getPara("sub_logistic_telphone");
		 String netWorkId=getPara("netWorkId");
		 String addId=getPara("addId");
		 
		 String sub_network_sn=getPara("sub_network_sn");
		 String sub_network_num=getPara("sub_network_num");
		 int park_id=getParaToInt("park_id");
		 
		 	NetWork net= new NetWork().findById(netWorkId);
			Address addressa=new Address().findById(addId);
		    addressa.set("uuid",addId);
			addressa.set("region_code",region);
			addressa.set("tail_address", address);
			addressa.set("create_time", getNow());
			addressa.set("user_id", user.getUserId());
			net.set("sub_network_name",sub_network_name); 
			net.set("sub_leader_name", contact_man);
			net.set("sub_logistic_mobile", contact_information);
			net.set("company_id", user.getCompanyId());
			net.set("sub_logistic_telphone", sub_logistic_telphone);
			
			net.set("sub_network_sn", sub_network_sn);
			net.set("sub_network_num", sub_network_num);
			net.set("park_id", park_id);
			
		 JSONObject json = new JSONObject();
			try{
				boolean flag=new NetWorkSvc().update(net, addressa);
					if(flag==true){
						json.put("state", "SUCCESS");
						json.put("msg", "更新成功");
					}else{
						json.put("state", "FAILED");
						json.put("msg", "更新失败");
						
					}
				renderJson(json.toString());
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	
	 public void delNetWork(){
			
		 JSONObject json = new JSONObject();
			try{
				String netWorkId=getPara("netWorkId");
				boolean flag=new NetWork().deleteById(netWorkId);
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
	 
	 public void checkNetWorkName(){
			String netWorkName=getPara("netWorkName");
			Truck truck=new Truck().findFirst("SELECT sub_network_name from logistics_network where sub_network_name=? and company_id=?",netWorkName,getSessionUser().getCompanyId());
			boolean flag=false;
			if(truck==null){
				flag=true;
			}
			renderJson(flag);
		}
	
	 /**
	  * 校验网点代码是否公司唯一
	  */
	 public void checknetworksn(){
		 String netWorkName=getPara("sub_network_sn");
		 Truck truck=new Truck().findFirst("SELECT sub_network_sn from logistics_network where sub_network_sn=? and company_id=?",netWorkName,getSessionUser().getCompanyId());
		 boolean flag=false;
		 if(truck==null){
			 flag=true;
		 }
		 renderJson(flag);
	 }
	 
	
	 /**
	  * 获取序号的方法
	  */
	 public String getsubnetworknum(SessionUser user){
			Record record =Db.findFirst("SELECT count(1) as countId from logistics_network where company_id="+user.getCompanyId());
			long id=1;
			if(record.getLong("countId")==0){
				
			}else{
				id=record.getLong("countId")+1;
			}
			
			String maxId=id+"";
			
			if(maxId.length()>=3){
				new NetWorkSvc().updateNetNum(user.getCompanyId());
			}
			
			switch (maxId.length()) {
			case 1:maxId="0"+maxId;
				break;
			default:
				break;
			}
			return maxId;
			
		}
	
	 public void getNetWork(){
		 SessionUser user = getSessionSysUser();
		String netWorkId=getPara("networkId");
		NetWork netWork = new NetWorkSvc().getNetWork(netWorkId,user);
		renderJson(new AddressSvc().getRegiondownName(netWork.getInt("region_code")));
	}

}
