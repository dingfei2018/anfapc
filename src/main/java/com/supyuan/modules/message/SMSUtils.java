package com.supyuan.modules.message;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jfinal.log.Log;
import com.jfinal.plugin.ehcache.CacheKit;
import com.supyuan.component.base.BaseResult;
import com.supyuan.component.base.ResultType;
import com.supyuan.component.util.Cache;
import com.supyuan.modules.result.WResult;
import com.supyuan.util.DateUtils;
import com.supyuan.util.UUIDUtil;
import com.supyuan.util.encrypt.Md5Utils;

/**
 * 短信发送控制
 * @author liangxp
 *
 * Date:2017年10月30日上午10:17:59 
 * 
 * @email liangxp@anfawuliu.com
 */
public class SMSUtils {
	
	private final static Log log = Log.getLog(SMSUtils.class);
	
	//短信发送频率限制
	private static Map<String, SendInfo> sends = new HashMap<String, SendInfo>();
	
	private final static String salt = "$injrlo94&@04$(985}wdmf{em)";
	
	
	/**
	 * 查询短信发送的数量
	 * @author liangxp
	 * Date:2017年10月30日上午10:30:25 
	 *
	 * @param mobile
	 * @param info
	 * @return 
	 */
	private static int getSendCurrNum(SMSType type, String mobile, SendInfo info){
		if(info!=null){
			String time = DateUtils.getNow();
			if(time.equals(info.getTime())){
				return info.getNum();
			}
			remove(type.getName()+mobile);
			return 0;
		}
		return 0;
	}
	
	
	public static synchronized void put(String key, SendInfo info){
		sends.put(key, info);
	}
	
	
	public static synchronized void remove(String key){
		sends.remove(key);
	}
	
	public static String getToken(){
		String random = UUIDUtil.UUID();
		String md5 = new Md5Utils().getMD5(random+salt);
		long randomTime = (long)(Math.random()*300+120);
		if(randomTime>300)randomTime = 300;
		Cache cache = new Cache();
		cache.setKey(md5);
		cache.setValue(random);
		cache.setTimeOut(randomTime*1000 + System.currentTimeMillis());
		CacheKit.put("smsNum", md5, cache);
		return md5;
	}
	
	
	private static boolean checkToken(String token){
		Cache cache = CacheKit.get("smsNum", token);
		if(cache==null||cache.getTimeOut()<System.currentTimeMillis()){
			return false;
		}
		return true;
	}
	
	
	/**
	 * 忘记密码发送短信
	 * @author liangxp
	 * Date:2017年10月30日上午10:32:53 
	 *
	 * @param mobile
	 * @return
	 */
	public static BaseResult sendPassSMS(String mobile, String token){
		BaseResult res = new BaseResult(ResultType.FAIL);
		if(!checkToken(token)){
			res.setResultType(ResultType.ILLEGAL);
			return res;
		}
		SendInfo info = sends.get(SMSType.PASSWORD_LIMIT_NUM.getName()+mobile);
		int num = getSendCurrNum(SMSType.PASSWORD_LIMIT_NUM, mobile, info);
		if((num+1)>SMSType.PASSWORD_LIMIT_NUM.getNum()){
			res.setResultType(ResultType.PHONE_LIMIT_ERROR);
			return res;
		}
		
		String value = CacheKit.get("sms", SMSType.PASSWORD_LIMIT_NUM.getName()+mobile);
		if(StringUtils.isNotEmpty(value)){//判断是否有缓存
			log.info("手机号:"+mobile+">>>后台验证码【"+value+"】");
			info.setNum(num+1);
			log.info("手机号:"+mobile+">>>短信发送次数【"+(num+1)+"】");
			res.putData("code", value);
			res.setResultType(ResultType.SUCCESS);
			return res;
		}
		
		String code = System.currentTimeMillis() + "";
		code = code.substring(code.length()-4);
		log.info("手机号:"+mobile+">>>后台验证码【"+code+"】");
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("code", code);
		//WResult re = SMSServiceImp.sendSms("ANFA_RESTORE_PASS", mobile, param);
		//if(re.isIstrue()){
		if(true){
			log.info("手机号:"+mobile+">>>短信发送次数【"+(num+1)+"】");
			if(num==0){
				put(SMSType.PASSWORD_LIMIT_NUM.getName()+mobile, new SendInfo(DateUtils.getNow(), 1));
			}else{
				info.setNum(num+1);
			}
			//本地缓存验证码
			CacheKit.put("sms", SMSType.PASSWORD_LIMIT_NUM.getName()+ mobile, code);
			
			res.putData("code", code);
			res.setResultType(ResultType.SUCCESS);
		}
		return res;
	}
	
	
	
	/**
	 * 公司注册发送短信
	 * @author liangxp
	 * Date:2017年10月30日上午10:50:34 
	 *
	 * @param mobile
	 * @return
	 */
	public static BaseResult sendRegSMS(String mobile, String token){
		BaseResult res = new BaseResult(ResultType.FAIL);
		if(!checkToken(token)){
			res.setResultType(ResultType.ILLEGAL);
			return res;
		}
		
		SendInfo info = sends.get(SMSType.REGISTER_LIMIT_NUM.getName()+mobile);
		int num = getSendCurrNum(SMSType.REGISTER_LIMIT_NUM, mobile, info);
		if((num+1)>SMSType.REGISTER_LIMIT_NUM.getNum()){
			res.setResultType(ResultType.PHONE_LIMIT_ERROR);
			return res;
		}
		
		String value = CacheKit.get("sms", SMSType.REGISTER_LIMIT_NUM.getName()+mobile);
		if(StringUtils.isNotEmpty(value)){//判断是否有缓存
			log.info("手机号:"+mobile+">>>后台验证码【"+value+"】");
			info.setNum(num+1);
			log.info("手机号:"+mobile+">>>短信发送次数【"+(num+1)+"】");
			res.putData("code", value);
			res.setResultType(ResultType.SUCCESS);
			return res;
		}
		
		String code = System.currentTimeMillis() + "";
		code = code.substring(code.length()-4);
		log.info("手机号:"+mobile+">>>后台验证码【"+code+"】");
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("code", code);
		//WResult re = SMSServiceImp.sendSms("ANFA_SIGN_IN", mobile, param);
		//if(re.isIstrue()){
		if(true){
			log.info("手机号:"+mobile+">>>短信发送次数【"+(num+1)+"】");
			if(num==0){
				put(SMSType.REGISTER_LIMIT_NUM.getName()+mobile, new SendInfo(DateUtils.getNow(), 1));
			}else{
				info.setNum(num+1);
			}
			
			//本地缓存验证码
			CacheKit.put("sms", SMSType.REGISTER_LIMIT_NUM.getName()+ mobile, code);
			
			res.putData("code", code);
			res.setResultType(ResultType.SUCCESS);
		}
		return res;
	}
	
	
	/**
	 * 验证短信
	 * @author liangxp
	 * Date:2017年10月30日上午10:52:58 
	 *
	 * @param type
	 * @param mobile
	 * @param code
	 * @return  验证通过返回null
	 */
	public static ResultType checkSMS(SMSType type, String mobile, String code, boolean delete){
		String value = CacheKit.get("sms", type.getName()+mobile);
		if(StringUtils.isEmpty(value)||!StringUtils.equals(code, value)){
			return ResultType.PHONE_CODE_WRONG;
		}
		if(delete)CacheKit.remove("sms", type.getName()+mobile);
		return null;
	}

}
