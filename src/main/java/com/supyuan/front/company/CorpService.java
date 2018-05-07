package com.supyuan.front.company;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 公司服务
 * 
 * @author liangxp
 *
 * Date:2017年6月28日下午4:24:59 
 * 
 * @email liangxp@anfawuliu.com
 */
@ModelBind(table = "library_corp_service")
public class CorpService extends BaseProjectModel<CorpService> {

	private static final long serialVersionUID = 1L;
	
	public static final CorpService dao = new CorpService();

}
