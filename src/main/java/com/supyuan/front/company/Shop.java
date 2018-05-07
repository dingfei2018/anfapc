package com.supyuan.front.company;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 店铺
 * 
 * @author liangxp
 *
 * Date:2017年6月28日下午4:07:28 
 * 
 * @email liangxp@anfawuliu.com
 */
@ModelBind(table = "shop")
public class Shop extends BaseProjectModel<Shop> {

	private static final long serialVersionUID = 1L;
	
	public static final Shop dao = new Shop();

}
