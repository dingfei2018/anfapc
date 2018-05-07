package com.supyuan.front.company;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 
 * 友情链接
 * 
 * @author liangxp
 *
 * Date:2017年6月28日下午4:18:59 
 * 
 * @email liangxp@anfawuliu.com
 */
@ModelBind(table = "library_friendly_link")
public class FriendlyLink extends BaseProjectModel<FriendlyLink> {

	private static final long serialVersionUID = 1L;
	
	public static final FriendlyLink dao = new FriendlyLink();

}
