package com.supyuan.front.branch;
/**
 * 网点发布
 * 
 */
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.front.company.Company;
import com.supyuan.front.company.CompanySvc;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.modules.addressbook.Address;
import com.supyuan.util.UUIDUtil;

@ControllerBind(controllerKey = "/front/branch")     //网店发布路径
public class BranchController extends BaseProjectController {

	private final String path = "/pages/front/branch/";

	public void index() {
	    setAttr("curr", 5);
		renderJsp(path  + "supervise.jsp");
	}
	
	public void submitBranch(){
		StringBuilder sb = new StringBuilder();
		try { 
		SessionUser user = getSessionSysUser();
		final String sub_network_name = getPara("sub_network_name"); //add 网点名称 by chenan on 2017/12/12 14:53
		final String region = getPara("region");
		final String address = getPara("address");
		final String contact_man = getPara("contact_man");
		final String contact_information = getPara("contact_information");
		final String sub_logistic_telphone=getPara("sub_logistic_telphone");
//			String uuid = UuidUtils.getUUID();
			LogisticsNetwork net= new LogisticsNetwork();
			Address addressa=new Address();
			addressa.set("uuid",UUIDUtil.UUID());
			addressa.set("region_code",region);
			addressa.set("tail_address", address);
			addressa.set("create_time", getNow());
			addressa.set("user_id", user.getUserId());
			addressa.save();
			
			CompanySvc svc = new CompanySvc();
			Company company = svc.getCompany(user.getUserId());
			
			net.set("sub_network_name",sub_network_name); //add 网点名称 by chenan on 2017/12/12 14:53
			net.set("sub_addr_uuid",addressa.get("uuid"));
			net.set("sub_leader_name", contact_man);
			net.set("sub_logistic_mobile", contact_information);
			net.set("company_id", company.getInt("id"));
			net.set("sub_logistic_telphone", sub_logistic_telphone);
			if(net.save()){
				sb.append("{'num':01,'msg':'");
				sb.append("提交成功" + "'}");

			}else{
				sb.append("{'num':01,'msg':'");
				sb.append("提交失败" + "'}");
			}   
		}catch(Exception e){
			e.printStackTrace();
			sb.append("{'msg':'");
			sb.append("提交失败" + "'}");
		}
		renderJson(sb.toString());
		
	}
	
	public void changeBranch(){
		StringBuilder sb = new StringBuilder();
		try { 
		SessionUser user = getSessionSysUser();
		final String region = getPara("region");
		final String address = getPara("address");
		final String contact_man = getPara("contact_man");
		final String contact_information = getPara("contact_information");
		final String sub_logistic_telphone=getPara("sub_logistic_telphone");
		final String branchid = getPara("branchid");
//			String uuid = UuidUtils.getUUID();
			LogisticsNetwork net= new LogisticsNetwork();
			Address addressa=new Address();
			addressa.set("uuid",UUIDUtil.UUID());
			addressa.set("region_code",region);
			addressa.set("tail_address", address);
			addressa.set("create_time", getNow());
			addressa.set("user_id", user.getUserId());
			addressa.set("uuid", UUIDUtil.UUID());
			addressa.save();
			
			CompanySvc svc = new CompanySvc();
			Company company = svc.getCompany(user.getUserId());
			
			net.set("id", branchid);
			net.set("sub_addr_uuid",addressa.get("uuid"));
			net.set("sub_leader_name", contact_man);
			net.set("sub_logistic_mobile", contact_information);
			net.set("company_id", company.getInt("id"));
			net.set("sub_logistic_telphone", sub_logistic_telphone);
			if(net.update()){
				sb.append("{'num':01,'msg':'");
				sb.append("提交成功" + "'}");

			}else{
				sb.append("{'num':01,'msg':'");
				sb.append("提交失败" + "'}");
			}   
		}catch(Exception e){
			e.printStackTrace();
			sb.append("{'msg':'");
			sb.append("提交失败" + "'}");
		}
		renderJson(sb.toString());
		
	}
	
	public void getBranch(){
    	try{
    		String id = getPara("id");
    		LogisticsNetwork branch = null;
    		StringBuilder sql = new StringBuilder(
    				"select id, getBookRegion(sub_addr_uuid) region,(select region_code as region  from address_book where uuid=sub_addr_uuid) as region,(select tail_address as address from address_book where uuid=sub_addr_uuid) as address ,sub_leader_name, sub_logistic_mobile,sub_logistic_telphone, company_id  " + "FROM logistics_network where id=? ");
    		branch = LogisticsNetwork.dao.findFirst(sql.toString(), id);
    		setAttr("branch", branch);
    		renderJsp(path  + "editbranch.jsp");
    		}catch (Exception e) {
    			e.printStackTrace();
    		}
    }
}
