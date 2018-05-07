package com.supyuan.front.evaluate; 

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 评价标签表
 * @author dingfei
 *
 * @date 2017年8月21日 上午10:14:09
 */
@ModelBind(table = "criti_item")
public class CritiItem extends BaseProjectModel<CritiItem> {
	private static final long serialVersionUID = 1L;
	public static final CritiItem dao = new CritiItem();

}
