package com.supyuan.kd.user;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;
/**
 * 客户资料model
 * @author chenan
 * Date:2017年12月5日下午5:41:08 
 */
@ModelBind(table = "sys_user", key = "userid")
public class User extends KdBaseModel<User> {
	private static final long serialVersionUID = 1L;
	public static final User dao = new User();


}
