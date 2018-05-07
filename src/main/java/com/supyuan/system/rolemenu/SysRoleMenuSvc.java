package com.supyuan.system.rolemenu;

import java.util.List;

import com.supyuan.jfinal.base.BaseService;


/**
 * 角色菜单权限
 * @author liangxp
 *
 * Date:2017年12月6日下午4:20:17 
 * 
 * @email liangxp@anfawuliu.com
 */
public class SysRoleMenuSvc  extends BaseService {
	
	
	/**
	 * 查询角色权限url列表
	 * @author liangxp
	 * Date:2017年12月6日下午3:33:32 
	 *
	 * @param userId
	 * @return
	 */
	public List<SysRoleMenu> findUserRoles(String roleIds){
		List<SysRoleMenu> menus = SysRoleMenu.dao.find("select href from sys_role_menu r left join sys_menu m on r.menu_id=m.id where r.role_id in (?)", roleIds);
		return menus;
	}
	
	
}
