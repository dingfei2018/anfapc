package com.supyuan.mkd.user;


import com.supyuan.jfinal.base.BaseService;
import com.supyuan.system.user.SysUser;

/**
 * user
 * @author liangxp
 *
 * Date:2018年1月30日上午10:27:28 
 * 
 * @email liangxp@anfawuliu.com
 */
public class UserSvc extends BaseService {

	/**
	 * 手机号查找用户
	 * @author liangxp
	 * @param username
	 * @return
	 */
	public SysUser findUserByName(String username){
		return  SysUser.dao.findFirstByWhere(" where status=1 and mobile = ?", username);
	}
	
}
