package com.supyuan.kd.user;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 用户网点
 * @author liangxp
 *
 * Date:2017年12月12日下午2:29:24 
 * 
 * @email liangxp@anfawuliu.com
 */
@ModelBind(table = "sys_user_network", key = "id")
public class UserNetWork extends KdBaseModel<UserNetWork> {
	private static final long serialVersionUID = 1L;
	public static final UserNetWork dao = new UserNetWork();


}
