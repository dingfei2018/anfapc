package com.supyuan.system.userrole;

import java.util.List;

import com.supyuan.jfinal.base.BaseService;


/**
 * 用户角色
 * @author liangxp
 *
 * Date:2017年12月6日下午3:24:09 
 * 
 * @email liangxp@anfawuliu.com
 */
public class SysUserRoleSvc  extends BaseService {
	
	
	/**
	 * 查询用户角色列表并拼接成字符串
	 * @author liangxp
	 * Date:2017年12月6日下午3:30:40 
	 *
	 * @param userId
	 * @return
	 */
	public String findUserRoleIds(int userId){
		SysUserRole role = SysUserRole.dao.findFirst("select group_concat(role_id) roleIds from sys_user_role where user_id=?", userId);
		return role==null?"":role.get("roleIds");
	}
	
	/**
	 * 查询用户角色列表
	 * @author liangxp
	 * Date:2017年12月6日下午3:33:32 
	 *
	 * @param userId
	 * @return
	 */
	public List<SysUserRole> findUserRoles(int userId){
		List<SysUserRole> roles = SysUserRole.dao.find("select id, user_id, role_id from sys_user_role where user_id=?", userId);
		return roles;
	}
	
}
