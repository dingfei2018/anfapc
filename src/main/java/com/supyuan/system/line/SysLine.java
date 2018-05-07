package com.supyuan.system.line;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

@ModelBind(table = "logistics_line")
public class SysLine extends BaseProjectModel<SysLine> {

	private static final long serialVersionUID = 1L;
	public static final SysLine dao = new SysLine();

}
