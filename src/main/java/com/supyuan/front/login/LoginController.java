package com.supyuan.front.login;
import java.net.URLDecoder;

import org.apache.commons.lang.StringUtils;

import com.jfinal.log.Log;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.component.base.BaseResult;
import com.supyuan.component.base.ResultType;
import com.supyuan.component.util.JFlyFoxUtils;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.modules.message.SMSType;
import com.supyuan.modules.message.SMSUtils;
import com.supyuan.system.log.SysLog;
import com.supyuan.system.user.SysUser;
import com.supyuan.util.encrypt.Md5Utils;
/**
 * 用户登录
 * 
 * @author TFC <2017-06-25>
 */
@ControllerBind(controllerKey = "/front/user")
public class LoginController extends BaseProjectController {
	private static final Log log = Log.getLog(LoginController.class);
	private static final String path = "/pages/front/";
	public static final String homePage = "/front/user/home";
	public static String[] urls = {"/front/register/success","/front/forget/success"
			,"/front/register/wl","/front/register/uwl", "/front/forget", "/front/userconter/getMobile"};
	
	
	
	public void index() {
		if (getSessionSysUser() != null) {
			renderJsp(path + "index.jsp");
		} else {
			String retUrl = getHeader("Referer"); 
			if(StringUtils.isNotBlank(retUrl)){
				retUrl = retUrl.substring(retUrl.indexOf("//")+2);
				retUrl = retUrl.substring(retUrl.indexOf("/"));
				if(!containUrl(retUrl))setAttr("url", retUrl);
			}
			renderJsp(path + "login.jsp");
		}
	}
	public void home(){
		redirect(path + "redirect");
	}
	
	/**
	 * 是否包含过滤条件
	 * @author liangxp
	 * Date:2017年8月18日下午4:09:48 
	 *
	 * @param u
	 * @return
	 */
	public boolean containUrl(String u){
		for (String url : urls) {
			if(u.startsWith(url))return true;
		}
		return false;
	}
	
	
	public void login() {
		String username = getPara("username").trim();
		String password = getPara("password");
		Integer isAuto = getParaToInt("isAuto");
		BaseResult result = new BaseResult();
		if (StringUtils.isBlank(username)) {
			result.setResultType(ResultType.USERNAME_NOT_NULL);
			renderJson(result);
			return;
		} else if (StringUtils.isBlank(password)) {
			result.setResultType(ResultType.PASSWORD_NOT_NULL);
			renderJson(result);
			return;
		}
		String encryptPassword = password;
		SysUser user = SysUser.dao.findFirstByWhere(" where status=1 and mobile = ?", username);
		if (user == null || user.getInt("userid") <= 0) {
			result.setResultType(ResultType.USERNAME_NOT_EXIST);
			renderJson(result);
			return;
		}

		String md5Password = "";
		try {
			String userPassword = user.get("password");
			String decryptPassword = JFlyFoxUtils.passwordDecrypt(userPassword);
			md5Password = new Md5Utils().getMD5(decryptPassword).toLowerCase();
		} catch (Exception e) {
			result.setResultType(ResultType.FAIL);
			renderJson(result);
			return;
		}
		if (!md5Password.equals(encryptPassword)) {
			result.setResultType(ResultType.PASSWORD_IS_WRONG);
			renderJson(result);
			return;
		}
		
		SessionUser sessionUser = setSessionInfo(user);
		setSessionSysUser(sessionUser, isAuto==1);
		// 添加日志
		user.put("update_id", user.getUserid());
		user.put("update_time", getNow());
		saveLog(user, SysLog.SYSTEM_LOGIN);
		String url = getPara("url");
		if(StringUtils.isNotBlank(url)){
			result.putData("url", URLDecoder.decode(url));
		}
		renderJson(result);
	}
	
	
	
	/**
	 * 用户注册实时验证手机验证码输入是否正确
	 * @author liangxp
	 * Date:2017年10月20日下午4:52:44 
	 *
	 */
	public void checkCode(){
		BaseResult result = new BaseResult();
		String mobile = getPara("phoneNumber");
		//验证码
		String smsCode = getPara("smsCode");
		ResultType res = SMSUtils.checkSMS(SMSType.REGISTER_LIMIT_NUM, mobile, smsCode, false);
		if(res!=null)result.setResultType(res);
		renderJson(result);
	}
	
	
	/**
	 * 退出
	 */
	public void logout() {
		SessionUser sessionUser = getSessionSysUser();
		if (sessionUser != null) {
			// 添加日志
			SysUser user = SysUser.dao.findById(sessionUser.getUserId());
			user.put("update_id", sessionUser.getUserId());
			user.put("update_time", getNow());
			saveLog(user, SysLog.SYSTEM_LOGOUT);
			// 删除session
			removeSessionSysUser();
		}
		String redirect_url = getPara("url")!=null? "?url="+getPara("url"):"";
		redirect("/front/user"+redirect_url);
	}
}
	

