package com.supyuan.front.register;
import org.apache.commons.lang.StringUtils;

import com.supyuan.component.base.BaseProjectController;
import com.supyuan.component.base.BaseResult;
import com.supyuan.component.base.ResultType;
import com.supyuan.component.util.JFlyFoxUtils;
import com.supyuan.component.util.ValidateTools;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.modules.message.SMSType;
import com.supyuan.modules.message.SMSUtils;
import com.supyuan.system.user.SysUser;
/**
 * 注册
 * @author TFC <2017-06-25>
 */
@ControllerBind(controllerKey = "/front/register")
public class RegisterController extends BaseProjectController {
	
	private static final String path = "/pages/front/";
	
	public void wl() {
		setAttr("token", SMSUtils.getToken());
		renderJsp(path + "register.jsp");
	}
	
	public void uwl() {
		setAttr("token", SMSUtils.getToken());
		renderJsp(path + "registers.jsp");
	}
	
	
	public void success() {
		renderJsp(path + "registersuc.jsp");
	}
	
	/**
	 * 已有账号发送验证码
	 * @author liangxp
	 * Date:2017年11月6日上午10:16:45 
	 *
	 */
	public void sendVerifiycode(){
		String phone = getPara("phone");
		String token = getPara("token");
		BaseResult result = new BaseResult();
		try {
			if(StringUtils.isBlank(phone)){
				result.setResultType(ResultType.PHONE_NOT_NULL);
				renderJson(result);
				return;
			}else if (!ValidateTools.isMobile(phone)) {
				result.setResultType(ResultType.PHONE_IS_WRONG);
				renderJson(result);
				return;
			}
			else{
				SysUser model =SysUser.dao.findFirstByWhereAndColumns(" where mobile="+phone,"*");
				if(model ==null){
					result.setResultType(ResultType.PHONE_NOT_EXIST);
					renderJson(result);
					return;
				}
				result = SMSUtils.sendRegSMS(phone, token);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultType(ResultType.FAIL);
		}
		renderJson(result);
	}
	
	
	/**
	 * 没有账号发送验证码
	 * @author liangxp
	 * Date:2017年11月6日上午10:16:59 
	 *
	 */
	public void sendVerificationcode(){
		String phone = getPara("phone");
		String token = getPara("token");
		BaseResult result = new BaseResult();
		try {
			if(StringUtils.isBlank(phone)){
				result.setResultType(ResultType.PHONE_NOT_NULL);
				renderJson(result);
				return;
			}else if (!ValidateTools.isMobile(phone)) {
				result.setResultType(ResultType.PHONE_IS_WRONG);
				renderJson(result);
				return;
			}
			else{
				SysUser model =SysUser.dao.findFirstByWhereAndColumns(" where mobile="+phone,"*");
				if(model !=null){
					result.setResultType(ResultType.PHONE_IS_EXIST);
					renderJson(result);
					return;
				}
				result = SMSUtils.sendRegSMS(phone, token);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultType(ResultType.FAIL);
		}
		renderJson(result);
	}
	
	
	/**
	 * 更改账号发送验证码
	 * @author dingfei
	 * 
	 *
	 */
	public void sendCheckPhoneVerificationcode(){
		SessionUser user = getSessionSysUser();
		String token = getPara("token");
		BaseResult result = new BaseResult();
		try {
			result = SMSUtils.sendRegSMS(user.getMobile(), token);
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultType(ResultType.FAIL);
		}
		renderJson(result);
	}
	

	public void registersave(){
		BaseResult result = new BaseResult();
		try {
			//手机号
			String mobile = getPara("phoneNumber").trim();
			//验证码
			String smsCode = getPara("smsCode").trim();
			//密码
			String password = getPara("password");
			//密码
			String newPassword = getPara("newPassword");
			//物流3与非物流4
			Integer type = getParaToInt("type");
			
			if (StringUtils.isBlank(mobile)) {
				result.setResultType(ResultType.PHONE_NOT_NULL);
				renderJson(result);
				return;
			}else if (StringUtils.isBlank(password)) {
				result.setResultType(ResultType.PASSWORD_NOT_NULL);
				renderJson(result);
				return;
			}else if( SysUser.dao.findFirstByWhere(" where mobile = ?", mobile)!= null){
				result.setResultType(ResultType.PHONE_IS_EXIST);
				renderJson(result);
				return;
			}else if (!password.equals(newPassword)) {
				result.setResultType(ResultType.PASSWORD_NOT_SAME);
				renderJson(result);
				return;
			}else{
				ResultType cres = SMSUtils.checkSMS(SMSType.REGISTER_LIMIT_NUM, mobile, smsCode, true);
				if(cres!=null){
					result.setResultType(cres);
					renderJson(result);
					return ;
				}
				
				SysUser user = new SysUser();
				String decryptPassword = JFlyFoxUtils.passwordEncrypt(password);
				String now = getNow();
				user.set("username", mobile);
				user.set("mobile", mobile);
				user.set("password", decryptPassword);
				user.set("usertype", type);
				user.set("create_time", now);
				boolean res = new RegisterSvc().register(user, type);
				if(!res){
					result.setResultType(ResultType.FAIL);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultType(ResultType.FAIL);
		}
		renderJson(result);
	}
	/**
	 * 物流公司注册协议
	 */
	public void regGreement(){
		
		renderJsp(path + "greement.jsp");
		
	}
	
	/**
	 * 非物流公司协议
	 * @author liangxp
	 * Date:2017年10月17日下午5:14:42 
	 *
	 */
	public void regGreement2(){
		
		renderJsp(path + "greement2.jsp");
		
	}

}
