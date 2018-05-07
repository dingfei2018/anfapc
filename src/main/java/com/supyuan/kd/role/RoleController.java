package com.supyuan.kd.role;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;

/**
 * 角色管理控制器
 * @author chenan
 * Date:2017年12月5日下午5:41:08 
 */
@ControllerBind(controllerKey = "/kd/role")
public class RoleController  extends BaseProjectController  {
	
	private static final String path = "/pages/kd/role/";
	
	public void index() {
		int companyId=getSessionSysUser().getCompanyId();
		RoleSvc roleSvc=new RoleSvc();
	
		JSONObject json=getSessionAttr("msg");
		removeSessionAttr("msg");
		
		List<Role> roleList=roleSvc.getRoleList(companyId,getSessionUser());
		
		setAttr("roleList",roleList);
		setAttr("msg",json);
		renderJsp(path + "index.jsp");
	}
	
	public void add() {
		String type=getPara("type")==null?"":getPara("type");
		if(type.equals("update")){
			int roleId=getParaToInt("roleId");
			Role role=Role.dao.findById(roleId);
			setSessionAttr("roleId",roleId);
			setAttr("role",role);
			setAttr("type","update");
		}
		renderJsp(path + "add.jsp");
	}
	
	public void updateRole() {
		SessionUser user=getSessionUser();
		RoleSvc roleSvc=new RoleSvc();
		String roleId=getPara("roleId");
		String menuIds=getPara("menuIds");
		String roleName=getPara("roleName");
		JSONObject json = new JSONObject();
		try{
		String ids[]=menuIds.split(",");
		Role role=Role.dao.findById(roleId);
		role.set("create_time", getNow());
		role.set("name", roleName);
		boolean flag=false;
		if(role.getInt("company_id")==0){
			role.set("company_id", user.getCompanyId());
			role.set("id", null);
			flag=roleSvc.saveRole(role,ids);
		}else{
			flag= roleSvc.updateRole(role, ids);
		}
		if(flag==true){
			json.put("state", "SUCCESS");
			json.put("msg", "修改成功");
		}else{
			json.put("state", "FAILED");
			json.put("msg", "修改失败");
		}
		setSessionAttr("msg", json);
		}catch (Exception e) {
			e.printStackTrace();
	}
		
		 renderJson(json);
	}
	
	public void saveRole() {
		int companyId=getSessionSysUser().getCompanyId();
		RoleSvc roleSvc=new RoleSvc();
		
		String menuIds=getPara("menuIds");
		String roleName=getPara("roleName");
		Role role=new Role();
		String ids[]=menuIds.split(",");
		JSONObject json = new JSONObject();
		try{
			role.set("name", roleName);
			role.set("status", 1);
			role.set("sort", 1);
			role.set("company_id", companyId);
			role.set("create_id", getSessionSysUser().getUserId());
			role.set("create_time", getNow());
			

		boolean flag=roleSvc.saveRole(role,ids);
		
		if(flag==true){
			json.put("state", "SUCCESS");
			json.put("msg", "新增成功");
		}else{
			json.put("state", "FAILED");
			json.put("msg", "新增失败");
		}
		
		setSessionAttr("msg", json);
		
		}catch (Exception e) {
			e.printStackTrace();
	}
		
		 renderJson(json);
	}
	
	 public void delete(){
			
			try{
				RoleSvc roleSvc=new RoleSvc();
				JSONObject json = new JSONObject();
				String roleId= getPara("roleId");
				if(roleId!=null&&!roleId.equals("")){
					boolean flag=false;
					String ids[]=roleId.split(",");
					for (String id : ids) {
						flag=roleSvc.delete(id);
					}
					
					if(flag==true){
						json.put("state", "SUCCESS");
						json.put("msg", "删除成功");
						renderJson(json.toString());
					}else{
						json.put("state", "FAILED");
						json.put("msg", "删除失败");
						renderJson(json.toString());
					}
				}
				setSessionAttr("msg", json);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	 
	 public void getMenuTree(){
		 RoleSvc roleSvc=new RoleSvc();
		 renderJson( roleSvc.getMenuTree());
	 }
	 
	 public void getCheckMenuTree(){
		 RoleSvc roleSvc=new RoleSvc();
		 List<Integer> ids=new ArrayList<>();
		 int id=getSessionAttr("roleId");
		 removeSessionAttr("roleId");
		 List<Record> record=Db.find("SELECT menu_id from sys_role s,sys_role_menu srm where s.id=srm.role_id and s.id="+id);
		 for (Record r : record) {
			 ids.add(r.getInt("menu_id"));
		}
		 renderJson( roleSvc.getCheckMenuTree(ids));
	 }
	 
	 /**
	  * 判断同个公司下角色名是否已存在
	  */
	 public void checkRoleName(){
		 String roleName=getPara("roleName");
		 boolean flag=false;
		 List<Role> roleList= new RoleSvc().getRoleListByComId(getSessionSysUser().getCompanyId());
		 for (Role role : roleList) {
			if(role.get("name").equals(roleName)){
				flag=true;
			}
		}
		 renderJson( flag);
	 }
	

}
