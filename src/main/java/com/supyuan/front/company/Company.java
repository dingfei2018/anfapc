package com.supyuan.front.company;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 公司信息
 * 
 * @author liangxp
 *
 * Date:2017年6月28日下午4:11:18 
 * 
 * @email liangxp@anfawuliu.com
 */
@ModelBind(table = "company")
public class Company extends BaseProjectModel<Company> {

	private static final long serialVersionUID = 1L;
	
	public static final Company dao = new Company();

}
