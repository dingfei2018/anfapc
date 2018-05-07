package com.supyuan.front.register;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.supyuan.component.util.Cache;
import com.supyuan.component.util.CacheManager;
import com.supyuan.front.company.Company;
import com.supyuan.front.company.Shop;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.kd.user.User;
import com.supyuan.kd.user.UserRole;
import com.supyuan.modules.message.InMessage;
import com.supyuan.modules.message.MessageUser;
import com.supyuan.system.user.SysUser;
/**
 * 注册业务层
 * @author TFC <2016-6-25>
 */
public class RegisterSvc extends BaseService {
	private final static Log log = Log.getLog(RegisterSvc.class);
	public static String sendMobileCode(String mobile) throws Exception {
		String code = System.currentTimeMillis() + "";
		code = code.substring(code.length()-4);
	/*	  Map<String, Object> param=new HashMap<String, Object>();
		  param.put("code", code);
		  WResult re = SMSServiceImp.sendSms("ANFA_SIGN_IN",mobile,param);
		  log.info("注册短信通知>>>:"+re.toString());*/
		
		//本地缓存验证码
		Cache cache = new Cache();
		cache.setKey(mobile);
		cache.setValue(code);
		CacheManager.putCache(mobile, cache);
		log.info("手机号:"+mobile+">>>后台验证码【"+code+"】");
		return code;
	}
	
	public static String validateMobileCode(String mobile, String code) {
		log.info("进入validateMobileCode方法验证");
		String result = "";
		Cache cache = CacheManager.getCacheInfo(mobile);
		if(null !=  cache){
			String mobileCode = (String)cache.getValue();
			if(StringUtils.isNotBlank(mobileCode)){
				if(!StringUtils.equals(code, mobileCode)){
					result = "验证码不正确";
				}
			}else{
				result = "验证码已过期";
			}
		}else{
			result = "验证码或手机号码不存在";
		}
		return result;
	}

	
	
	public boolean register(SysUser user, int type) {
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				if(type==4){//物流公司注册
					user.set("is_onshop", 1);
					if(!user.save()) return false;
					Company cmp = new Company();
					cmp.set("id", user.get("userid"));
					cmp.set("user_id", user.get("userid"));
					cmp.set("charge_mobile", user.get("mobile"));
					if(!cmp.save()) return false;
					Shop shop = new Shop();
					shop.set("id", user.get("userid"));
					shop.set("user_id", user.get("userid"));
					shop.set("create_time", user.get("create_time"));
					if(!shop.save()) return false;
					user.set("create_id", user.get("userid"));
					if(!user.update()) return false;
					
					
					/*List<UserRole> roles = new ArrayList<UserRole>();
					List<Role> res = Role.dao.find("select id from sys_role where company_id=0");
					for (Role role : res) {
						UserRole role1 = new UserRole();
						role1.set("user_id", user.get("userid"));
						role1.set("role_id", role.get("id"));
						roles.add(role1);
					}
					int[] tSave = Db.batchSave(roles, roles.size());
					if(tSave.length!=roles.size())return false;*/
					
					//复制管理员
					UserRole role = new UserRole();
					role.set("user_id", user.get("userid"));
					role.set("role_id", 1);
					if(!role.save())return false;
				}else if(type==3){//非物流公司注册
					if(!user.save()) return false;
					Company cmp = new Company();
					cmp.set("id", user.get("userid"));
					cmp.set("user_id", user.get("userid"));
					cmp.set("charge_mobile", user.get("mobile"));
					if(!cmp.save()) return false;
					user.set("create_id", user.get("userid"));
					if(!user.update()) return false;
				}
				
				String message = String.format("尊敬的%s用户，恭喜你成为网上物流VIP会员，为了交易的可靠性，请尽快去完善资质和基本信息", user.getStr("mobile"));
				//交易通知消息
				InMessage comMessage = new InMessage();
				comMessage.set("label", "系统提示");
				comMessage.set("content", message);
				comMessage.set("created", user.get("create_time"));
				comMessage.set("sender", user.getUserid());
				comMessage.set("type", 1);
				if(!comMessage.save())return false;
				MessageUser cmessageUser = new MessageUser();
				cmessageUser.set("message_id", comMessage.getInt("id"));
				cmessageUser.set("toer", user.getUserid());
				if(!cmessageUser.save())return false;
				return true;
			}
		});
		return tx;
	}
	
	/**
	 * 验证手机号是否已注册
	 * @param mobile
	 * @return
	 */
	public boolean checkMobile(String mobile){
		return User.dao.findFirst("SELECT mobile from sys_user where mobile=?",mobile)==null?true:false;
	}
	
}
