package com.supyuan.kd.user;


import java.util.List;

import javax.servlet.jsp.tagext.TryCatchFinally;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.component.base.BaseResult;
import com.supyuan.component.base.ResultType;
import com.supyuan.component.util.JFlyFoxUtils;
import com.supyuan.front.branch.LogisticsNetwork;
import com.supyuan.front.branch.NetWorkSvc;
import com.supyuan.front.register.RegisterSvc;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.kd.bankcard.BankCard;
import com.supyuan.kd.role.Role;
import com.supyuan.kd.role.RoleSvc;

/**
 * 用户控制器
 * @author chenan
 * Date:2017年12月11日下午10:41:08 
 */
@ControllerBind(controllerKey = "/kd/user")
public class UserController  extends BaseProjectController  {
	
	private static final String path = "/pages/kd/user/";
	
	public void index() {
		SessionUser sessionUser=getSessionSysUser();
		UserSvc userSvc=new UserSvc();
		
		String mobile=getPara("mobile")==null?"":getPara("mobile");
		String realname=getPara("realname")==null?"":getPara("realname");
		String tel=getPara("tel")==null?"":getPara("tel");
	
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
		Page<User> page=userSvc.getUserList(paginator, mobile, realname, tel,sessionUser);
	
		setAttr("page",page);
		setAttr("userId",sessionUser.getUserId());
		setAttr("msg",json);
		setAttr("mobile",mobile);
		setAttr("realname",realname);
		setAttr("tel",tel);
		renderJsp(path + "index.jsp");
	}
	
	public void add() {
		String type=getPara("type")==null?"":getPara("type");
		SessionUser sessionUser=getSessionSysUser();
		int companyId=sessionUser.getCompanyId();
		boolean AllNetFlag=false;
		//根据userid获取可分配网点列表
		List<LogisticsNetwork> netWorkList=new NetWorkSvc().getNetWorkListBuUserId(sessionUser);
		List<LogisticsNetwork> comNet=new NetWorkSvc().getNetWorkList(sessionUser);
		//根据公司id获取可分配角色列表
		List<Role> roleList=new RoleSvc().getRoleList(companyId,sessionUser);
		
		if(sessionUser.getUsertype()==4 || sessionUser.getUsertype()==3){
			AllNetFlag=true;
		}
		if(netWorkList.size()==comNet.size()){
			AllNetFlag=true;
		}
		
		setAttr("AllNetFlag",AllNetFlag);
		setAttr("netWorkList",netWorkList);
		setAttr("roleList",roleList);
		renderJsp(path + "add.jsp");
	}
	
	public void update(){
		
		String netWorkIdsPara=getPara("netWorkIds");
		String roleIdsPara=getPara("roleIds");
		String netWorkIds[]=null;
		String roleIds[]=null;
		
		if(netWorkIdsPara!=null&&!netWorkIdsPara.equals("")){
			netWorkIds=netWorkIdsPara.split(",");
		}
		
		if(roleIdsPara!=null&&!roleIdsPara.equals("")){
			roleIds=roleIdsPara.split(",");
		}
		
		int userId=getParaToInt("userId");
		String mobile=getPara("mobile");
		String realname=getPara("realname");
		String telephone=getPara("telephone");
		String email=getPara("email");
		
		JSONObject json = new JSONObject();
		
		try {
			User user=User.dao.findById(userId);
			user.set("mobile", mobile).set("realname", realname).set("telephone", telephone).set("email", email);
			boolean flag=new UserSvc().updateUser(user, netWorkIds, roleIds);
			if(flag==true){
				json.put("state", "SUCCESS");
				json.put("msg", "修改成功");
			}else{
				json.put("state", "FAILED");
				json.put("msg", "修改失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setSessionAttr("msg", json);
		renderJson(json);
		
	}
	
	public void goUpdate(){
		int userId=getParaToInt("userId");
		SessionUser sessionUser=getSessionUser();
		User user=User.dao.findFirst("Select userid,mobile,realname,email,telephone from sys_user where userid=?",userId);
		boolean AllNetFlag=false;
		//根据userid获取可分配网点列表
		List<LogisticsNetwork> netWorkList=new NetWorkSvc().getNetWorkListBuUserId(sessionUser);
		List<Record> netIds=Db.find("SELECT l.id  from logistics_network l,sys_user_network s where l.id=s.network_id AND s.user_id=?",userId);

		boolean flag=false;
		List<Record> list=Db.find("select r.name from sys_user_role su,sys_role r where su.role_id=r.id and user_id=?",userId);
		for (Record record:list) {
			if(record.get("name")==null){
				break;
			}
			if(record.get("name").equals("系统管理员")||record.get("name").equals("总经理")){
				flag=true;
			}
		}

		if(flag){
			netIds=Db.find("SELECT l.id  from logistics_network l where l.company_id=?",sessionUser.getCompanyId());
		}

		List<LogisticsNetwork> comNet=new NetWorkSvc().getNetWorkList(sessionUser);
		//根据公司id获取可分配角色列表
		List<Role> roleList=new RoleSvc().getRoleList(sessionUser.getCompanyId(),sessionUser);
		List<Record> roleIds=Db.find("SELECT s.id from sys_role s,sys_user_role u where s.id=u.role_id AND u.user_id=?",userId);
		
		if(sessionUser.getUsertype()!=1){
			AllNetFlag=true;
		}
		if(netWorkList.size()==comNet.size()){
			AllNetFlag=true;
		}
		
		setAttr("AllNetFlag",AllNetFlag);
		setAttr("user",user);
		setAttr("netWorkList",netWorkList);
		setAttr("netIds",netIds);
		setAttr("roleList",roleList);
		setAttr("roleIds",roleIds);
		
		renderJsp(path + "update.jsp");
	}
	
	public void saveUser() {
		SessionUser sessionUser=getSessionUser();
		int companyId=sessionUser.getCompanyId();
		int userId=sessionUser.getUserId();
		UserSvc userSvc=new UserSvc();
		
		String netWorkIdsPara=getPara("netWorkIds");
		String roleIdsPara=getPara("roleIds");
		String netWorkIds[]=null;
		String roleIds[]=null;
		
		String mobile=getPara("mobile");
		String realname=getPara("realname");
		String telephone=getPara("telephone");
		String email=getPara("email");
		String password=getPara("password");
		
		User user=new User();
		user.set("mobile", mobile).set("username", mobile).set("realname", realname).set("telephone", telephone).set("email", email).set("password", JFlyFoxUtils.passwordEncrypt(password));
		user.set("company_id", companyId).set("create_time", getNow()).set("update_time", getNow()).set("usertype", 1).set("status", 1).set("create_id", userId);
		
		if(netWorkIdsPara!=null&&!netWorkIdsPara.equals("")){
			netWorkIds=netWorkIdsPara.split(",");
		}
		if(roleIdsPara!=null&&!roleIdsPara.equals("")){
			 roleIds=roleIdsPara.split(",");
		}
		
		JSONObject json = new JSONObject();
		try{

		boolean flag=userSvc.saveuser(user, netWorkIds, roleIds);
		
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
	
	/**
	 * 管理员修改用户密码
	 */
	public void updatePassWord(){
		int userId=getParaToInt("userId");
		String password=getPara("password");
		User user=User.dao.findById(userId);
		JSONObject json = new JSONObject();
		boolean flag=user.set("update_time", getNow()).set("password", JFlyFoxUtils.passwordEncrypt(password)).update();
		if(flag==true){
			json.put("state", "SUCCESS");
			json.put("msg", "修改成功");
		}else{
			json.put("state", "FAILED");
			json.put("msg", "修改失败");
		}
		setSessionAttr("msg", json);
		renderJson(json);
		
	}
	
	 public void delete(){
			
		 	String userId= getPara("userId");
			try{
				UserSvc userSvc=new UserSvc();
				JSONObject json = new JSONObject();
				if(userId!=null&&!userId.equals("")){
					boolean flag=false;
					String ids[]=userId.split(",");
					for (String id : ids) {
						flag=userSvc.delete(id);
					}
					
					if(flag==true){
						json.put("state", "SUCCESS");
						json.put("msg", "删除成功");
					}else{
						json.put("state", "FAILED");
						json.put("msg", "删除失败");
					}
				}
				setSessionAttr("msg", json);
				renderJson(json.toString());
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	 
	/**
	 * 根据公司id获取该公司下网点
	 */
	 public void getNetWork(){
		 NetWorkSvc netWorkSvc=new NetWorkSvc();
		 renderJson( netWorkSvc.getNetWorkList(getSessionSysUser()));
	 }
	 
	 public void checkMobile(){
		 String mobile=getPara("mobile");
		 renderJson( new RegisterSvc().checkMobile(mobile));
	 }
	 
	/**
	 * 获取用户属于的网点
	 */
	 public void getNetWorkByUserId(){
		 renderJson(new NetWorkSvc().getNetWorkListBuUserId(getSessionSysUser()));
	 }
	 
	 
	
	

}
