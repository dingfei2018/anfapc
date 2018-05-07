package com.supyuan.kd.role;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


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
public class RoleSvc extends BaseService {

	private final static Log log = Log.getLog(RoleSvc.class);
	
	/**
	 * 获取角色列表
	 * @param companyId
	 * @return
	 */
	public List<Role> getRoleList(int companyId, SessionUser sessionUser) {
		List<Role> roleComList;
		StringBuilder sql = new StringBuilder("select s.id as roleId,s.name as roleName,m.id as menuId,getMenuName(s.id) as menuName,srm.id as srmId");
		sql.append(" FROM sys_role s,sys_role_menu srm,sys_menu m");
		sql.append(" where 1=1");
		sql.append(" and s.id=srm.role_id");
		sql.append(" and m.id=srm.menu_id");
		sql.append(" and s.status=1");
		sql.append(" and s.company_id=?");
		sql.append(" GROUP BY s.id");
		sql.append(" ORDER BY s.create_time desc");
		
		roleComList = Role.dao.find(sql.toString(),companyId);
		
		List<Role> roleList;
		StringBuilder sql2 = new StringBuilder("select s.id as roleId,s.name as roleName,m.id as menuId,getMenuName(s.id) as menuName,srm.id as srmId");
		sql2.append(" FROM sys_role s,sys_role_menu srm,sys_menu m");
		sql2.append(" where 1=1");
		sql2.append(" and s.id=srm.role_id");
		sql2.append(" and m.id=srm.menu_id");
		sql2.append(" and s.status=1");
		sql2.append(" and s.company_id=0 and s.name not in (SELECT s.name FROM sys_role s WHERE s. STATUS = 1 AND s.company_id = ?)");
		sql2.append(" GROUP BY s.id");
		sql2.append(" ORDER BY s.create_time desc");
		
		roleList = Role.dao.find(sql2.toString(),companyId);

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
		
		for (Role role : roleList) {
			if(flag){
				if(role.get("roleName").equals("系统管理员")||role.get("roleName").equals("总经理")) break;
			}
			roleComList.add(role);
		}
		
		return roleComList;
	}
	
	/**
	 * 获取公司下角色列表
	 * @param companyId
	 * @return
	 */
	public List<Role> getRoleListByComId(int companyId) {
		List<Role> roleList=Role.dao.find("SELECT id,name from sys_role where status=1 and (company_id=? or company_id=0)",companyId);
		return roleList;
	}
	
	/**
	 * 删除角色、角色菜单关联表数据、保留菜单表数据
	 * @param id
	 * @return
	 */
	public boolean  delete(String id) {
		
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				if(!Role.dao.deleteById(id)) return false;
				if(!(Db.update("delete from sys_role_menu where role_id="+id)>=0)) return false;
				return true;
			}
		});
		return tx;
	}
	
	/**
	 * 同时插入角色表、角色菜单表数据
	 * @param role
	 * @param ids
	 * @return
	 */
	public boolean  saveRole(Role role,String []ids){
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				
				if(!role.save()) return false;
				for (String id : ids) {
					RoleMenu roleMenu=new RoleMenu();
					roleMenu.set("role_id", role.get("id"));
					roleMenu.set("menu_id", id);
					if(!roleMenu.save()) return false;
				}
				return true;
			}
		});
		return tx;
	}
	
	/**
	 * 同时更新角色表、角色菜单关联表，如关联表未有该条关联则插入该条信息
	 * @param role
	 * @param ids
	 * @return
	 */
	public boolean  updateRole(Role role,String []ids){
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				
				if(!role.update()) return false;
				int roleId=role.getInt("id");
				if(!(Db.update("Delete from sys_role_menu where role_id=?",roleId)>=0)) return false;
				for (String id : ids) {
					if (!new RoleMenu().set("role_id", roleId).set("menu_id", id).save()) return false;
				}
				
				return true;
			}
		});
		return tx;
	}
	
	/*
	 * 菜单tree
	 */
	public List<Tree> getMenuTree(){
		List<Record> record=Db.find("SELECT id,name from sys_menu where parent_id=0");
		List<Tree> treeList1=new ArrayList();
		
		for (Record r : record) {
			Tree tree=new Tree();
			tree.setId(r.getInt("id"));
			tree.setText(r.getStr("name"));
			treeList1.add(tree);
		}
		for (Tree tree : treeList1) {
			List<Record> record2=Db.find("SELECT id,name from sys_menu where parent_id="+tree.getId());
			List<Tree> treeList2=new ArrayList();
			for (Record r2 : record2) {
				Tree tree2=new Tree();
				tree2.setId(r2.getInt("id"));
				tree2.setText(r2.getStr("name"));
				treeList2.add(tree2);
			}
				tree.setChildren(treeList2);
		}
		
		return treeList1;
	}
	
	/*
	 * 获取选中菜单tree
	 */
	public List<Tree> getCheckMenuTree(List<Integer> ids){
		List<Record> record=Db.find("SELECT id,name from sys_menu where parent_id=0");
		List<Tree> treeList1=new ArrayList();
		
		for (Record r : record) {
			Tree tree=new Tree();
			int id=r.getInt("id");
			tree.setId(id);
			tree.setText(r.getStr("name"));
			for (int i : ids) {
				if(i==id){
					tree.setChecked(true);
				}
			}
			treeList1.add(tree);
		}
		for (Tree tree : treeList1) {
			List<Record> record2=Db.find("SELECT id,name from sys_menu where parent_id="+tree.getId());
			List<Tree> treeList2=new ArrayList();
			for (Record r2 : record2) {
				Tree tree2=new Tree();
				int id=r2.getInt("id");
				tree2.setId(id);
				tree2.setText(r2.getStr("name"));
				for (int i : ids) {
					if(i==id){
						tree2.setChecked(true);
					}
				}
				treeList2.add(tree2);
			}
				tree.setChildren(treeList2);
		}
		
		return treeList1;
	}
	
	
	
}
