package com.supyuan.kd.log;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

@ModelBind(table = "sas_dml_user")
public class AsaDmlUser extends KdBaseModel<AsaDmlUser> {

	private static final long serialVersionUID = 1L;

	public static final AsaDmlUser dao = new AsaDmlUser();
	
	public static final String MODEL_SAVE = "添加";
	public static final String MODEL_UPDATE = "更新";
	public static final String MODEL_DELETE = "删除";
	
	public static final int MODEL_SAVE_NUM = 1;
	public static final int MODEL_UPDATE_NUM = 2;
	public static final int MODEL_DELETE_NUM = 3;

}
