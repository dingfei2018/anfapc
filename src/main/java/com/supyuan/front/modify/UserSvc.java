package com.supyuan.front.modify;

import java.sql.SQLException;
import java.util.List;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.system.user.SysUser;

/**
 * 修改密码
 * 
 * @author dingfei <2017年6月30日>
 *
 */
public class UserSvc extends BaseService {

	private final static Log log = Log.getLog(UserSvc.class);

	/**
	 * 通过用户登录手机号码查询密码
	 * @param mobile 手机号码
	 * @return
	 */
	public List<SysUser> getUserPassWord(String mobile) {
	    try{  
            String sql="select password  from sys_user where mobile=? ";  
            return SysUser.dao.find(sql, mobile);
        }catch(Exception e){  
            e.printStackTrace();  
            return null;  
        } 
	}

	/**
	 *  更改密码
	 * @param mobile 手机号码
	 * @param password 新密码
	 * @return
	 */
	public int updateUserPassWord(String mobile, String password) {
		try {
			String updatesql = "update sys_user set password=? where mobile=? ";
			int result = Db.update(updatesql,password,mobile);
			if (result > 0) {
				return 1;
			} else {
				return 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 *  更改手机号码
	 * @param newmobile 新手机号码
	 * @param mobile 原手机号码
	 * @return
	 */
	public boolean updateUserMobile(String newmobile,SessionUser user) {
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				String updatesql = "update sys_user set mobile=? where mobile=? ";
				int result = Db.update(updatesql,newmobile, user.getMobile());
				if(result==0){
					return false;
				}
				
				updatesql = "update company set charge_mobile=? where user_id=? ";
				result = Db.update(updatesql,newmobile, user.getUserId());
				if(result==0){
					return false;
				}
				return true;
			}
		});
		return tx;
	}

}
