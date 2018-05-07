package com.supyuan.front.forget;
import com.jfinal.log.Log;
import com.supyuan.component.util.Cache;
import com.supyuan.component.util.CacheManager;
import com.supyuan.jfinal.base.BaseService;
/**
 * 注册业务层
 * @author TFC <2016-6-25>
 */
public class ForgetSvc extends BaseService {
	private final static Log log = Log.getLog(ForgetSvc.class);
	public static String sendMobileCode(String mobile) throws Exception {
		String code = System.currentTimeMillis() + "";
		code = code.substring(code.length()-4);
/*		  Map<String, Object> param=new HashMap<String, Object>();
		  param.put("code", code);
		  WResult re = SMSServiceImp.sendSms("ANFA_RESTORE_PASS",mobile,param);
		  log.info("忘记密码短信通知>>>:"+re.toString());*/
		
		//本地缓存验证码
		Cache cache = new Cache();
		cache.setKey(mobile);
		cache.setValue(code);
		CacheManager.putCache(mobile, cache);
		log.info("手机号:"+mobile+">>>后台验证码【"+code+"】");
		return code;
	}
}
