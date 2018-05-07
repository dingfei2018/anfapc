package com.supyuan.system.role;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.system.rolemenu.SysRoleMenu;
import com.supyuan.util.DateUtils;
import com.supyuan.util.NumberUtils;
import com.supyuan.util.StrUtils;

public class RoleSvc extends BaseService {

	/**
	 * 获取角色授权的菜单
	 * 
	 * 2015年4月28日 下午5:01:54 flyfox 369191470@qq.com
	 * 
	 * @param role_id
	 * @return
	 */
	public String getMemus(int role_id) {
		String menus = null;
		List<SysRoleMenu> roleMenuList = new SysRoleMenu().findByWhere("where role_id = ?", role_id);
		for (SysRoleMenu role : roleMenuList) {
			menus += role.get("menu_id")+",";
		}
		if (menus != null) {
			menus = menus.substring(0,menus.length()-1);
		}
		return menus;
	}

	/**
	 * 保存授权信息
	 * 
	 * 2015年4月28日 下午5:00:30 flyfox 369191470@qq.com
	 * 
	 * @param role_id
	 * @param menus
	 */
	public void saveAuth(int role_id, String menus, int update_id) {
		// 删除原有数据库
		Db.update("delete from sys_role_menu where role_id = ? ", role_id);

		if (StrUtils.isNotEmpty(menus)) {
			String[] arr = menus.split(",");
			for (String menu_id : arr) {
				SysRoleMenu roleMenu = new SysRoleMenu();
				roleMenu.set("role_id", role_id);
				roleMenu.set("menu_id", NumberUtils.parseInt(menu_id));

				// 日志添加
				roleMenu.put("update_id", update_id);
				roleMenu.put("update_time", DateUtils.getNow(DateUtils.DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS));
				roleMenu.save();
			}
		}
	}

}
