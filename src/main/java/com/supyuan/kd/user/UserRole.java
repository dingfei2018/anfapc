package com.supyuan.kd.user;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;
/**
 *	用户角色关联表model
 * @author chenan
 * Date:2017年12月12日上午午9:41:08 
 */
@ModelBind(table = "sys_user_role", key = "id")
public class UserRole extends KdBaseModel<UserRole> {
	private static final long serialVersionUID = 1L;
	public static final UserRole dao = new UserRole();


}
