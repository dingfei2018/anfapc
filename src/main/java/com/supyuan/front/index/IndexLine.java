package com.supyuan.front.index;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 专线
 * 
 * @author TFC <2016-6-25>
 */
@ModelBind(table = "logistics_line")
public class IndexLine extends BaseProjectModel<IndexLine>{

	private static final long serialVersionUID = 1L;
	public static final IndexLine dao = new IndexLine();

}
