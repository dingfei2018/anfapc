package com.supyuan.kd.role;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;
/**
 * 客户资料model
 * @author chenan
 * Date:2017年12月5日下午5:41:08 
 */
@ModelBind(table = "sys_role", key = "id")
public class Role extends KdBaseModel<Role> {
	private static final long serialVersionUID = 1L;
	public static final Role dao = new Role();


}
