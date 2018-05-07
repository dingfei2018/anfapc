package com.supyuan.mkd.user;


import org.apache.commons.lang.StringUtils;

import com.supyuan.component.base.MBaseProjectController;
import com.supyuan.component.base.MBaseResult;
import com.supyuan.component.base.MResultType;
import com.supyuan.component.util.JFlyFoxUtils;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.mkd.common.MSessionUser;
import com.supyuan.system.log.SysLog;
import com.supyuan.system.user.SysUser;
import com.supyuan.util.encrypt.Md5Utils;

/**
 * 
 * @author liangxp
 *
 * Date:2018年1月26日下午2:54:07 
 * 
 * @email liangxp@anfawuliu.com
 */
@ControllerBind(controllerKey = "/mkd/user")
public class UserController  extends MBaseProjectController  {
	
	
	public void userinfo(){
		MBaseResult res = new MBaseResult();
		MSessionUser mSessionUser = getMSessionUser();
		SysUser detailsUser = new UserSvc().findUserByName(mSessionUser.getMobile());
		if(detailsUser==null){
			res.setResultType(MResultType.USERNAME_NOT_EXIST);
		}
		res.putData("userinfo", detailsUser);
		renderJson(res);
	}
	
	
	/**
	 * 手机登录
	 * @author liangxp
	 */
	public void mlogin(){
		MBaseResult result = new MBaseResult();
		String username = getPara("username").trim();
		String password = getPara("password");
		if (StringUtils.isBlank(username)||StringUtils.isBlank(password)) {
			result.setResultType(MResultType.PARAM_ERROR);
			renderJson(result);
			return;
		} 
		
		String encryptPassword = password;
		SysUser user = new UserSvc().findUserByName(username);
		if (user == null) {
			result.setResultType(MResultType.USERNAME_NOT_EXIST);
			renderJson(result);
			return;
		}

		String md5Password = "";
		try {
			String userPassword = user.get("password");
			String decryptPassword = JFlyFoxUtils.passwordDecrypt(userPassword);
			md5Password = new Md5Utils().getMD5(decryptPassword);
		} catch (Exception e) {
			result.setResultType(MResultType.FAIL);
			renderJson(result);
			return;
		}
		if (!md5Password.equals(encryptPassword)) {
			result.setResultType(MResultType.PASSWORD_IS_WRONG);
			renderJson(result);
			return;
		}
		
		//保存登录信息
		MSessionUser sessionuser = setSessionInfo(user);
		// 添加日志
		user.put("update_id", user.getUserid());
		user.put("update_time", getNow());
		saveLog(user, SysLog.SYSTEM_LOGIN);
		log.info("用户登录后：userkey=" + sessionuser.getUserkey());
		result.putData("userkey", sessionuser.getUserkey());
		MUserResModel transferUser = transferUser(user);
		result.putData("userinfo", transferUser);
		renderJson(result);
	}
	
	
	private MUserResModel transferUser(SysUser user){
		MUserResModel model = new MUserResModel();
		model.setUsername(user.getUserName());
		model.setMobile(user.getStr("mobile"));
		model.setUserType(user.getInt("usertype"));
		//model.setHeadImg(user.getStr("title_img_uuid"));
		model.setHeadImg("http://img2.woyaogexing.com/2018/01/31/a32625a38ec9458b!400x400_big.jpg");
		return model;
	}
	

}
