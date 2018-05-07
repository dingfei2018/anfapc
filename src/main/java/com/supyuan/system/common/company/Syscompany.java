package com.supyuan.system.common.company;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

@ModelBind(table = "company")
public class Syscompany extends BaseProjectModel<Syscompany> {

	private static final long serialVersionUID = 1L;
	public static final Syscompany dao = new Syscompany();

}
