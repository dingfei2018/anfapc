package com.supyuan.front.forget;
import org.apache.commons.lang.StringUtils;

import com.supyuan.component.base.BaseProjectController;
import com.supyuan.component.base.BaseResult;
import com.supyuan.component.base.ResultType;
import com.supyuan.component.util.JFlyFoxUtils;
import com.supyuan.component.util.ValidateTools;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.modules.message.SMSType;
import com.supyuan.modules.message.SMSUtils;
import com.supyuan.system.user.SysUser;
import com.supyuan.util.encrypt.Md5Utils;
/**
 * 忘记密码
 * @author TFC <2017-06-25>
 */
@ControllerBind(controllerKey = "/front/forget")
public class ForgetController extends BaseProjectController {
	private static final String path = "/pages/front/";
	
	private static final int timeout = 5*60*1000;
	
	public void index() {
		setAttr("token", SMSUtils.getToken());
		renderJsp(path + "forget.jsp");
	}
	//忘记密码获取验证码
	public void forgetMsg(){
		String phone = getPara("phone");
		String token = getPara("token");
		BaseResult result = new BaseResult(ResultType.FAIL);
		try {
			if(null != phone){
				if(StringUtils.isBlank(phone)){
					result.setResultType(ResultType.PHONE_NOT_NULL);
					renderJson(result);
					return;
				}else if (!ValidateTools.isMobile(phone)) {
					result.setResultType(ResultType.PHONE_IS_WRONG);
					renderJson(result);
					return;
				}else{
					SysUser member = SysUser.dao.findFirstByWhereAndColumns(" where mobile="+phone,"*");
					if(member ==null){
						result.setResultType(ResultType.PHONE_NOT_EXIST);
						renderJson(result);
						return ;
					}
					result = SMSUtils.sendPassSMS(phone, token);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(result);
	}
	
	//忘记密码手机验证码验证
	public void forgetsave(){
		BaseResult result = new BaseResult(ResultType.FAIL);
		final String phone = getPara("phoneNumber"); 
		final String smsCode = getPara("smsCode"); 
		try {
			if (StringUtils.isBlank(phone)) {
				result.setResultType(ResultType.PHONE_NOT_NULL);
				renderJson(result);
				return;
			}else if (!ValidateTools.isMobile(phone)) {
				result.setResultType(ResultType.PHONE_IS_WRONG);
				renderJson(result);
				return;
			}else{
				SysUser member = SysUser.dao.findFirstByWhereAndColumns(" where mobile="+phone,"*");
				if(member ==null){
					result.setResultType(ResultType.PHONE_NOT_EXIST);
					renderJson(result);
					return ;
				}
				ResultType cres = SMSUtils.checkSMS(SMSType.PASSWORD_LIMIT_NUM, phone, smsCode, true);
				if(cres!=null){
					result.setResultType(cres);
					renderJson(result);
					return ;
				}
				long time = System.currentTimeMillis();
				result.putData("phone", phone);
				result.putData("uid", member.getUserid());
				result.putData("time", time);
				String md5 = new Md5Utils().getMD5(member.getDate("create_time").getTime()+phone+member.getUserid()+time);
				result.putData("sign", md5);
				result.setResultType(ResultType.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(result);
	}
	
	//跳转修改密码页面
	public void passwordindex() {
		try {
			String phone = getPara("phone");
			Integer userid = getParaToInt("uid");
			SysUser member = SysUser.dao.findFirstByWhereAndColumns(" where mobile="+phone,"*");
			if(member==null||member.getUserid().intValue()!=userid){
				redirect("/front/user");
			}else{
				Long time = getParaToLong("time");
				String sign = getPara("sign");
				setAttr("time", time);
				setAttr("sign", sign);
				setAttr("userid", userid);
				setAttr("phone", phone);
				renderJsp(path + "password.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirect("/front/user");
		}
	}
	
	
	public void updatepassword(){
		BaseResult result = new BaseResult(ResultType.FAIL);
		try {
			Integer id = getParaToInt("userid");
			String phone = getPara("phone");
			Long time = getParaToLong("time");
			String sign = getPara("sign");
			SysUser member = SysUser.dao.findFirstByWhereAndColumns(" where mobile="+phone,"*");
			if(member==null||time==null||member.getUserid().intValue()!=id){
				result.setResultType(ResultType.INVIDATE);
				renderJson(result);
				return;
			}
			if(System.currentTimeMillis()-time>timeout){//五分钟未修改则失效
				result.setResultType(ResultType.INVIDATE);
				renderJson(result);
				return;
			}
			
			String md5 = new Md5Utils().getMD5(member.getDate("create_time").getTime()+phone+member.getUserid()+time);
			if(!md5.equals(sign)){
				result.setResultType(ResultType.INVIDATE);
				renderJson(result);
				return;
			}
			//密码
			String password = getPara("password");
			String newPassword = getPara("newPassword");
			if (StringUtils.isBlank(password)||StringUtils.isBlank(newPassword)) {
				result.setResultType(ResultType.PASSWORD_NOT_NULL);
				renderJson(result);
				return;
			}else if (!password.equals(newPassword)) {
				result.setResultType(ResultType.PASSWORD_NOT_SAME);
				renderJson(result);
				return;
			}else{
				String decryptPassword = JFlyFoxUtils.passwordEncrypt(password);
				SysUser sysuser = new SysUser();
				sysuser.set("userid", id);
				sysuser.set("password", decryptPassword);
				if(sysuser.update()){
					result.setResultType(ResultType.SUCCESS);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(result);
		
	}
	
	public void success() {
		renderJsp(path + "passwordsuc.jsp");
	}
	
}
