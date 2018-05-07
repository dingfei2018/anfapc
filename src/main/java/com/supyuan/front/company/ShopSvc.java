package com.supyuan.front.company;

import com.jfinal.plugin.activerecord.Db;
import com.supyuan.jfinal.base.BaseService;


/**
 * 店铺
 * @author liangxp
 *
 * Date:2017年10月26日下午2:13:09 
 * 
 * @email liangxp@anfawuliu.com
 */
public class ShopSvc  extends BaseService {
	
	/**
	 * 更新设置
	 * @author liangxp
	 * Date:2017年10月26日下午2:18:06 
	 *
	 * @param zhizhao
	 * @param cert
	 * @param telphone
	 * @param networkphone
	 * @param userId
	 * @return
	 */
	public boolean  updateSetings(String zhizhao, String cert, String telphone, String networkphone, int userId){
		String sql = "update shop set show_yyzz=?, show_sfz=?, show_mobile=?, show_network_mobile=? where user_id=?";
		return Db.update(sql, zhizhao,cert,telphone,networkphone,userId)>0;
	}
	
	
}
