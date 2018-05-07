package com.supyuan.modules.addressbook;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 专线
 * 
 * @author TFC <2016-6-25>
 */
@ModelBind(table = "library_region")
public class Region extends BaseProjectModel<Region> {

	private static final long serialVersionUID = 1L;
	public static final Region dao = new Region();
}
