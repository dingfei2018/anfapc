package com.supyuan.kd.user;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.util.tree.Tree;
/**
 * 角色管理svc
 * @author chenan
 * Date:2017年11月14日上午10:30:08 
 * 
 */
public class UserSvc extends BaseService {

	private final static Log log = Log.getLog(UserSvc.class);
	
	/**
	 *  获取分页角色列表
	 * @param paginator
	 * @param mobile
	 * @param realname
	 * @param tel
	 * @param sessionUser
	 * @return
	 */
	public Page<User> getUserList(Paginator paginator,String mobile, String realname,String tel,SessionUser sessionUser) {
		Page<User> userList;
		StringBuilder select = new StringBuilder("select u.userid as userId,u.realname,u.mobile,u.email,telephone,getRoleName(u.userid) as roleName,getNetWorkName(u.userid) as netWork");
		StringBuilder sql = new StringBuilder();
		StringBuilder parm = new StringBuilder();
		sql.append("From sys_user u LEFT JOIN sys_user_role sur ON u.userid = sur.user_id LEFT JOIN sys_role r ON r.id = sur.role_id LEFT JOIN sys_user_network sun ON sun.user_id=u.userid LEFT JOIN logistics_network l ON l.id=sun.network_id");
		parm.append(" where 1=1");
		
		if  (StringUtils.isNotBlank(mobile)) {
			parm.append(" and u.mobile =" + mobile);
		}
		if  (StringUtils.isNotBlank(realname)) {
			parm.append(" and u.realname like ('%" + realname + "%')");
		}
		if  (StringUtils.isNotBlank(tel)) {
			parm.append(" and u.telephone =" + tel);
		}
			parm.append(" and (u.company_id=" + sessionUser.getCompanyId());
			parm.append(" OR u.userid in (" +"SELECT c.id from company c,sys_user s where c.user_id=s.userid and s.userid=" +sessionUser.getUserId()+"))");
			parm.append(" and u.status=" + 1);
		boolean flag=true;
		List<Record> list=Db.find("select r.name from sys_user_role su,sys_role r where su.role_id=r.id and user_id=?",sessionUser.getUserId());
		for (Record record:list) {
			if(record.get("name")==null){
				break;
			}
			if(record.get("name").equals("系统管理员")||record.get("name").equals("总经理")){
				flag=false;
			}
		}

		if(flag){
				parm.append(" and u.userid in (");
				parm.append(" SELECT u.userid FROM sys_user u,logistics_network l,sys_user_network sun where u.userid=sun.user_id AND l.id=sun.network_id AND l.id in("+sessionUser.toNetWorkIdsStr()+") )");
				parm.append(" and u.userid not in (select user_id from sys_user_role su,sys_role r where su.role_id=r.id and (r.company_id=0 or r.company_id="+sessionUser.getCompanyId()+") and (r.name='系统管理员' or r.name='总经理'))");
		}

		parm.append(" GROUP BY u.userid");
		parm.append(" order BY u.create_time desc");

		sql.append(parm.toString());
		
		userList = User.dao.paginate(paginator,select.toString(),sql.toString());
		return userList;
	}
	
	public User getUserAndCompanyByUserId(int userId) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT u.realname,c.corpname,c.corp_telphone,getBookLongRegion(c.corp_addr_uuid) as addr");
		sql.append(" FROM sys_user u");
		sql.append(" LEFT JOIN company c ON u.company_id=c.id");
		sql.append(" where u.`status`=1 and u.userid=?");
		
		User user = User.dao.findFirst(sql.toString(),userId);
		return user;
	}
	
	
	/**
	 * 删除用户表、用户角色关联表、用户网点关联表数据
	 * @param id
	 * @return
	 */
	public boolean  delete(String id) {
		
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				User user=new User();
				if(!(Db.update("UPDATE sys_user SET status=0 where userid=?",id)>=0)) return false;
				if(!(Db.update("delete from sys_user_network where user_id=?",id)>=0)) return false;
				if(!(Db.update("delete from sys_user_role where user_id=?",id)>=0)) return false;
				return true;
			}
		});
		return tx;
	}
	
	/**
	 * 同时插入用户表、用户角色关联表、用户网点关联表
	 * @param user
	 * @param netWorkIds
	 * @param roleIds
	 * @return
	 */
	public boolean  saveuser(User user,String []netWorkIds,String []roleIds){
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				
				if(!user.save()) return false;
				
				for (String netWorkId : netWorkIds) {
					if(netWorkId!=null&&!netWorkId.equals("")){
						UserNetWork userNetWork=new UserNetWork();
						if(!userNetWork.set("network_id", netWorkId).set("user_id", user.getInt("userid")).save()) return false;
					}
				}
				
				for (String roleId : roleIds) {
					if(roleId!=null&&!roleId.equals("")){
						UserRole userRole=new UserRole();
						if(!userRole.set("role_id", roleId).set("user_id", user.getInt("userid")).save()) return false;
					}
				}
				
				return true;
			}
		});
		return tx;
	}
	
	/**
	 * 同时更新用户表、用户角色关联表、用户网点关联表，如关联表未有该条关联则插入该条信息
	 * @param user
	 * @param netIds
	 * @param roleIds
	 * @return
	 */
	public boolean  updateUser(User user,String [] netIds,String [] roleIds){
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				
				if(!user.update()) return false;
				int userId=user.getInt("userid");
				
				if(netIds==null) return false;
				if(roleIds==null) return false;
				if(!(Db.update("Delete from sys_user_network where user_id=?",userId)>=0)) return false;
				for (String id : netIds) {
					if(!new UserNetWork().set("user_id", userId).set("network_id", id).save()) return false;
				}
				
				if(!(Db.update("Delete from sys_user_role where user_id=?",userId)>=0)) return false;
				for (String id : roleIds) {
					if(!new UserRole().set("user_id", userId).set("role_id", id).save()) return false;
				}
				
				return true;
			}
		});
		return tx;
	}
	
	/**
	 * 顶部导航user信息
	 * @param userId
	 * @return
	 */
	public User getDetailsUser(int userId){
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT getRoleName(u.userid) as roleName,getNetWorkName(u.userid) as netWorkName,u.mobile,u.realname,u.email,u.telephone");
		sql.append(" FROM sys_user u");
		sql.append(" where u.status=1 and u.userid=?");

		User user=new User().findFirst(sql.toString(),userId);
		
		return user;
	}
	
	

	
	
}
