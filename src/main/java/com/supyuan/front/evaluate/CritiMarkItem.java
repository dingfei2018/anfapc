package com.supyuan.front.evaluate;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 评价关系表
 * @author dingfei
 *
 * @date 2017年8月21日 上午10:20:40
 */
@ModelBind(table = "criti_mark_item")
public class CritiMarkItem extends BaseProjectModel<CritiMarkItem> {
	private static final long serialVersionUID = 1L;
	public static final CritiMarkItem dao = new CritiMarkItem();

}
