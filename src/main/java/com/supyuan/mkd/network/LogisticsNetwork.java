package com.supyuan.mkd.network;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 
 * @author liangxp
 *
 * Date:2017年6月28日下午3:07:29 
 * 
 * @email liangxp@anfawuliu.com
 */
@ModelBind(table = "logistics_network")
public class LogisticsNetwork extends BaseProjectModel<LogisticsNetwork> {

	private static final long serialVersionUID = 1L;
	
	public static final LogisticsNetwork dao = new LogisticsNetwork();

}
