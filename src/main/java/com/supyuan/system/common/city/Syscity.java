package com.supyuan.system.common.city;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

@ModelBind(table = "library_region")
public class Syscity extends BaseProjectModel<Syscity> {

	private static final long serialVersionUID = 1L;
	public static final Syscity dao = new Syscity();

}
