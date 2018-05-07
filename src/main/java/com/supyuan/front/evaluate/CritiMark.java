package com.supyuan.front.evaluate;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 评价表
 * @author dingfei
 *
 * @date 2017年8月21日 上午10:19:21
 */
@ModelBind(table = "criti_mark")
public class CritiMark extends BaseProjectModel<CritiMark> {
	private static final long serialVersionUID = 1L;
	public static final CritiMark dao = new CritiMark();

}
