package com.supyuan.front.branch;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.front.company.Company;
import com.supyuan.front.company.CompanySvc;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;

@ControllerBind(controllerKey = "/front/branch/preview")
public class ShowBranch extends BaseProjectController {
	private final String path = "/pages/front/branch/";
	public void index() {
		SessionUser user = getSessionSysUser();
		CompanySvc svc = new CompanySvc();
		Company company=null;
		if(svc!=null){
		 company = svc.getCompany(user.getUserId());
		}
		Paginator paginator = getPaginator();
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		if(paginator.getPageNo()<1){
			paginator.setPageNo(1);
		}
		Page<LogisticsNetwork> page = LogisticsNetwork.dao.paginate(getPaginator(),"select id, getBookRegion(sub_addr_uuid) region,(select tail_address as address from address_book where uuid=sub_addr_uuid) address,sub_leader_name, sub_logistic_mobile,sub_logistic_telphone, company_id  ",  "FROM logistics_network where company_id=? ", company.getInt("id"));
		setAttr("page", page);
		setAttr("curr", 10);
		renderJsp(path  + "preview.jsp");
	}
	
    public void delBranch(){
		
		try{
			JSONObject json = new JSONObject();
			String branchId= getPara("branchid");
			if(branchId!=null){
				int update = Db.update("delete from logistics_network where id="+branchId);
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
